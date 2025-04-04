package services.user;

import dtos.user.BlacklistUserRequest;
import dtos.user.PromoteUserRequest;
import dtos.user.UpdatePasswordRequest;
import dtos.user.ViewUsers;
import model.entities.User;
import model.exceptions.ServerException;
import model.exceptions.NotFoundException;
import persistence.daos.user.UserDao;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService
{
    private final UserDao userRepo;

    public UserServiceImpl(UserDao userDao)
    {
        userRepo = userDao;
    }


    // Following transaction script pattern:
    // * retrieve
    // * validate
    // * update
    // * save changes
    @Override
    public void promoteToAdmin(PromoteUserRequest request)
    {
        // retrieve
        User user = userRepo.getSingle(request.email());
        if (user == null)
        {
            throw new NotFoundException("User with email '" + request.email() + "' not found.");
        }

        // validate
        if (user.isBlacklisted())
        {
            throw new ServerException("The user with email '" + request.email() + "' is blacklisted, and cannot be promoted to admin.");
        }

        if (user.isAdmin())
        {
            throw new ServerException("The user with email '" + request.email() + "' is already an admin.");
        }

        // update
        user.setAdmin(true);

        // save changes
        userRepo.save(user);
    }

    @Override
    public void blacklistUser(BlacklistUserRequest request)
    {
        User user = userRepo.getSingle(request.email());
        if (user == null)
        {
            throw new NotFoundException("User with email '" + request.email() + "' not found.");
        }
        if (user.isBlacklisted())
        {
            throw new ServerException("The user with email '" + request.email() + "' is already blacklisted.");
        }
        user.blacklist(request.reason()); // Alternatively I could have a setBlackList(true, request.reason()) method. I prefer this clearer approach.
        user.setAdmin(false); // admins are automatically demoted when blacklisted.
        userRepo.save(user);
    }

    @Override
    public List<ViewUsers.UserDisplayDto> getUsersOverview(ViewUsers.Request filterParameters)
    {
        List<User> users = userRepo.getMany(filterParameters.pageIndex(), filterParameters.pageSize(), filterParameters.firstNameContains());
        List<ViewUsers.UserDisplayDto> result = new ArrayList<>();

        // convert User to UserDto. This way we only send the data, the client needs. We don't include the password, for example.
        // I could add admin status or black list status, if needed.

        for (User user : users)
        {
            ViewUsers.UserDisplayDto dto = new ViewUsers.UserDisplayDto(user.getEmail(), user.getFirstName(), user.getLastName(), user.isBlacklisted());
            result.add(dto);
        }

        return result;
    }

    @Override
    public void updatePassword(UpdatePasswordRequest request)
    {
        // retrieve
        User user = userRepo.getSingle(request.email());
        if (user == null)
        {
            throw new NotFoundException("User with email '" + request.email() + "' not found.");
        }

        // validate
        if (!user.getPassword().equals(request.oldPassword()))
        {
            throw new ServerException("Incorrect password");
        }

        // update
        user.setPassword(request.newPassword());

        // save changes
        userRepo.save(user);
    }
}

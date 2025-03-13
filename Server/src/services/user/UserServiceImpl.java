package services.user;

import dtos.user.BlacklistUserRequest;
import dtos.user.PromoteUserRequest;
import dtos.user.UpdatePasswordRequest;
import dtos.user.ViewUsers;
import model.entities.User;
import model.exceptions.BusinessLogicException;
import model.exceptions.NotFoundException;
import persistence.repositories.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;

    public UserServiceImpl(UserRepository userRepository) {
        userRepo = userRepository;
    }

    @Override
    public void promoteToAdmin(PromoteUserRequest request) {
        User user = userRepo.getSingle(request.email());
        if (user == null) {
            throw new NotFoundException("User with email '" + request.email() + "' not found.");
        }
        if (user.isBlacklisted()) {
            throw new BusinessLogicException("The user with email '" + request.email() + "' is blacklisted, and cannot be promoted to admin.");
        }

        if (user.isAdmin()) {
            throw new BusinessLogicException("The user with email '" + request.email() + "' is already an admin.");
        }

        user.setAdmin(true);
        userRepo.save(user);
    }

    @Override
    public void blacklistUser(BlacklistUserRequest request) {
        User user = userRepo.getSingle(request.email());
        if (user == null) {
            throw new NotFoundException("User with email '" + request.email() + "' not found.");
        }
        if (user.isBlacklisted()) {
            throw new BusinessLogicException("The user with email '" + request.email() + "' is already blacklisted.");
        }
        user.setBlacklisted(true, request.reason());
        user.setAdmin(false);
        userRepo.save(user);
    }

    @Override
    public List<ViewUsers.UserDto> getUsersOverview(ViewUsers.Request filterParameters) {
        List<User> users = userRepo.getMany(filterParameters.pageIndex(), filterParameters.pageSize(), filterParameters.firstNameContains());
        List<ViewUsers.UserDto> result = new ArrayList<>();

        for (User user : users) {
            ViewUsers.UserDto dto = new ViewUsers.UserDto(user.getEmail(), user.getFirstName(), user.getLastName());
            result.add(dto);
        }

        return result;
    }

    @Override
    public void updatePassword(UpdatePasswordRequest request) {
        User user = userRepo.getSingle(request.email());
        if (user == null) {
            throw new NotFoundException("User with email '" + request.email() + "' not found.");
        }
        if(!user.getPassword().equals(request.oldPassword())){
            throw new BusinessLogicException("Incorrect password");
        }
        user.setPassword(request.newPassword());
        userRepo.save(user);
    }
}

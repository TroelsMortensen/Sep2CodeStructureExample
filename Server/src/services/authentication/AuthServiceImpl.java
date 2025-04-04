package services.authentication;

import dtos.auth.LoginRequest;
import dtos.auth.RegisterUserRequest;
import dtos.user.UserDataDto;
import model.entities.User;
import model.exceptions.ServerException;
import persistence.daos.user.UserDao;

public class AuthServiceImpl implements AuthenticationService
{

    private final UserDao userDao;

    public AuthServiceImpl(UserDao userDao)
    {
        this.userDao = userDao;
    }


    @Override
    public void registerUser(RegisterUserRequest request)
    {
        User existingUser = userDao.getSingle(request.email());
        if (existingUser != null)
        {
            throw new ServerException("Email is already in use.");
        }

        validateEmailIsCorrectFormat(request.email());
        validatePasswordIsCorrectFormat(request.password());
        validateName(request.firstName());
        validateName(request.lastName());

        User newUser = new User(
                request.email(),
                request.password(),
                request.firstName(),
                request.lastName()
        );

        userDao.add(newUser);
    }

    private static void validatePasswordIsCorrectFormat(String password)
    {
        // validate password has correct format, e.g. upper case/lower case, symbols, numbers, etc
        if (password.length() < 8)
        {
            throw new ServerException("Password must be 8 or more characters");
        }
        if (password.length() > 24)
        {
            throw new ServerException("Password must be 24 or fewer characters");
        }
        // etc..
    }

    private static void validateName(String name)
    {
        // here you should validate the name is not null or empty, maybe has min/max length
    }

    private static void validateEmailIsCorrectFormat(String email)
    {
        // validate email has correct format

    }

    @Override
    public UserDataDto login(LoginRequest request)
    {
        User existingUser = userDao.getSingle(request.email());
        if (existingUser == null)
        {
            throw new ServerException("User not found.");
        }

        if (!existingUser.getPassword().equals(request.password()))
        {
            throw new ServerException("Incorrect password.");
        }

        if (existingUser.isBlacklisted())
        {
            throw new ServerException("This user is blacklisted: " + existingUser.getBlacklistReason());
        }

        UserDataDto userData = new UserDataDto
                (
                        existingUser.getEmail(),
                        existingUser.getFirstName(),
                        existingUser.getLastName(),
                        existingUser.isBlacklisted(),
                        existingUser.isAdmin()
                );
        return userData;
    }
}

package services.authentication;

import dtos.auth.LoginRequest;
import dtos.auth.RegisterUserRequest;
import model.entities.User;
import model.exceptions.BusinessLogicException;
import model.exceptions.ValidationException;
import persistence.repositories.user.UserRepository;

public class AuthServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void registerUser(RegisterUserRequest request) {
        User existingUser = userRepository.getSingle(request.email());
        if(existingUser != null){
            throw new BusinessLogicException("Email is already in use.");
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

        userRepository.add(newUser);
    }

    private static void validatePasswordIsCorrectFormat(String password) {
        // validate password has correct format, e.g. upper case/lower case, symbols, numbers, etc
        if (password.length() < 8) {
            throw new ValidationException("Password must be 8 or more characters");
        }
        if (password.length() > 24) {
            throw new ValidationException("Password must be 24 or fewer characters");
        }
        // etc..
    }

    private static void validateName(String name) {
        // here you should validate the name is not null or empty, maybe has min/max length
    }

    private static void validateEmailIsCorrectFormat(String email) {
        // validate email has correct format

    }

    @Override
    public String login(LoginRequest request) {
        User existingUser = userRepository.getSingle(request.email());
        if(existingUser == null){
            throw new BusinessLogicException("User not found.");
        }
        if(!existingUser.getPassword().equals(request.password())){
            throw new BusinessLogicException("Incorrect password.");
        }
        return "OK";
    }
}

package services.authentication;

import dtos.auth.LoginRequest;
import dtos.auth.RegisterUserRequest;

public interface AuthenticationService {
    void registerUser(RegisterUserRequest request);
    String login(LoginRequest request);
}

package networking.authentication;

import dtos.auth.RegisterUserRequest;

public interface AuthenticationService
{
    void registerUser(RegisterUserRequest user);
}

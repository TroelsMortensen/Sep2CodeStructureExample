package networking.authentication;

import dtos.Request;
import dtos.auth.LoginRequest;
import dtos.auth.RegisterUserRequest;
import dtos.user.UserDataDto;

public class SocketAuthenticationService implements AuthenticationService
{
    @Override
    public void registerUser(RegisterUserRequest user)
    {
        Request request = new Request("auth", "register", user);
        SocketService.sendRequest(request);
        // ignore response, don't need it. If the above method succeeds, then the request is a success.
    }

    @Override
    public UserDataDto login(LoginRequest loginRequest)
    {
        Request request = new Request("auth", "login", loginRequest);
        UserDataDto userData = (UserDataDto) SocketService.sendRequest(request);
        return userData;
    }
}

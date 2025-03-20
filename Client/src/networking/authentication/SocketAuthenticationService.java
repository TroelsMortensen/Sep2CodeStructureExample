package networking.authentication;

import dtos.Request;
import dtos.auth.RegisterUserRequest;

public class SocketAuthenticationService implements AuthenticationService
{
    @Override
    public void registerUser(RegisterUserRequest user)
    {
        Request request = new Request("auth", "register", user);
        SocketService.sendRequest(request);
        // ignore response, don't need it. If the above method succeeds, then the request is a success.
    }
}

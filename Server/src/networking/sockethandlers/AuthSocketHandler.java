package networking.sockethandlers;

import dtos.auth.LoginRequest;
import dtos.auth.RegisterUserRequest;
import networking.exceptions.InvalidActionException;
import services.authentication.AuthenticationService;

public class AuthSocketHandler implements SocketHandler {
    private final AuthenticationService authenticationService;

    public AuthSocketHandler(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public Object handle(String action, Object payload) {
        // Some methods can return something, some do not. Logging in? If no exceptions are thrown, success could be assumed.
        // Here, though, I explicitly return "OK", just as an example. I don't really use it.
        switch (action) {
            case "register" -> authenticationService.registerUser((RegisterUserRequest) payload);
            case "login" -> {
                return authenticationService.login((LoginRequest) payload);
            }
            default -> throw new InvalidActionException("auth", action);
        };
        return null; // just a default return value. Some actions above may return stuff.
    }
}

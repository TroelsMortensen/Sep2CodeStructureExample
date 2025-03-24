package startup;

import networking.requesthandlers.AuthRequestHandler;
import networking.requesthandlers.RequestHandler;
import networking.requesthandlers.UserRequestHandler;
import persistence.repositories.user.UserListRepository;
import services.authentication.AuthServiceImpl;
import services.authentication.AuthenticationService;
import services.user.UserService;
import services.user.UserServiceImpl;

public class ServiceLocator {

    // The service locator is responsible for providing services, where needed.
    // It ensures various services receives the correct arguments.
    // If an implementation needs to be swapped out, e.g. repository implementations,
    // we can do that a single place, and it will take effect across the entire application.

    public RequestHandler getAuthenticationRequestHandler() {
        return new AuthRequestHandler(getAuthenticationService());
    }

    public RequestHandler getUserRequestHandler(){
        return new UserRequestHandler(getUserService());
    }

    private static AuthenticationService getAuthenticationService() {
        return new AuthServiceImpl(getUserRepository());
    }

    private static UserService getUserService() {
        return new UserServiceImpl(getUserRepository());
    }

    private static UserListRepository getUserRepository() {
        return new UserListRepository();
    }
}

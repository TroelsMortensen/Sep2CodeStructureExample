package startup;

import networking.sockethandlers.AuthSocketHandler;
import networking.sockethandlers.SocketHandler;
import networking.sockethandlers.UserSocketHandler;
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

    public SocketHandler getAuthenticationSocketHandler() {
        return new AuthSocketHandler(getAuthenticationService());
    }

    public SocketHandler getUserSocketHandler(){
        return new UserSocketHandler(getUserService());
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

package startup;

import networking.requesthandlers.AuthRequestHandler;
import networking.requesthandlers.RequestHandler;
import networking.requesthandlers.UserRequestHandler;
import persistence.daos.user.UserListDao;
import services.authentication.AuthServiceImpl;
import services.authentication.AuthenticationService;
import services.user.UserService;
import services.user.UserServiceImpl;

public class ServiceProvider
{

    // The service provider is responsible for providing services, where needed.
    // It ensures various services receives the correct arguments.
    // If an implementation needs to be swapped out, e.g. DAO implementations,
    // we can do that a single place, and it will take effect across the entire application.

    // This is a fairly crude and basic implementation of the Service Locator pattern.

    public RequestHandler getAuthenticationRequestHandler() {
        return new AuthRequestHandler(getAuthenticationService());
    }

    public RequestHandler getUserRequestHandler(){
        return new UserRequestHandler(getUserService());
    }

    private static AuthenticationService getAuthenticationService() {
        return new AuthServiceImpl(getUserDao());
    }

    private static UserService getUserService() {
        return new UserServiceImpl(getUserDao());
    }

    private static UserListDao getUserDao() {
        return new UserListDao();
    }
}

package networking.sockethandlers;

import dtos.user.BlacklistUserRequest;
import dtos.user.PromoteUserToAdminRequest;
import dtos.user.ViewUsers;
import services.user.UserService;

import java.util.List;

public class UserSocketHandler implements SocketHandler {
    private final UserService userService;

    public UserSocketHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Object handle(String action, Object payload) {
        switch (action){
            case "blacklist" -> userService.blacklistUser((BlacklistUserRequest) payload);
            case "promote" -> userService.promoteToAdmin((PromoteUserToAdminRequest) payload);
            case "view_users" -> {
                return userService.getUsersOverview((ViewUsers.Request) payload);
            }
        }
        return null; // just a default return value. Some actions above may return stuff.
    }
}

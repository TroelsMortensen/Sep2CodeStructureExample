package networking.sockethandlers;

import dtos.user.BlacklistUserRequest;
import dtos.user.PromoteUserToAdminRequest;
import services.user.UserService;

public class UserSocketHandler implements SocketHandler {
    private final UserService userService;

    public UserSocketHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String handle(String action, Object payload) {
        switch (action){
            case "blacklist" -> userService.blacklistUser((BlacklistUserRequest) payload);
            case "promote" -> userService.promoteToAdmin((PromoteUserToAdminRequest) payload);
        }
        return null; // just a default return value. Some actions above may return stuff.
    }
}

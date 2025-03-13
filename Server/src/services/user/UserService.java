package services.user;

import dtos.user.BlacklistUserRequest;
import dtos.user.PromoteUserToAdminRequest;
import dtos.user.ViewUsers;

import java.util.List;

public interface UserService {
    void promoteToAdmin(PromoteUserToAdminRequest request);
    void blacklistUser(BlacklistUserRequest request);

    List<ViewUsers.UserDto> getUsersOverview(ViewUsers.Request payload);
}

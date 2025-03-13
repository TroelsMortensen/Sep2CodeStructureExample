package services.user;

import dtos.user.BlacklistUserRequest;
import dtos.user.PromoteUserToAdminRequest;

public interface UserService {
    void promoteToAdmin(PromoteUserToAdminRequest request);
    void blacklistUser(BlacklistUserRequest request);
}

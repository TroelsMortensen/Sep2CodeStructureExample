package networking.user;

import dtos.user.PromoteUserRequest;
import dtos.user.ViewUsers;

import java.util.List;

public interface UsersClient
{
    List<ViewUsers.UserDisplayDto> getUsers();

    void promoteUser(PromoteUserRequest request);
}

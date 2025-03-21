package networking.user;

import dtos.Request;
import dtos.user.BlacklistUserRequest;
import dtos.user.PromoteUserRequest;
import dtos.user.ViewUsers;
import networking.SocketService;

import java.util.List;

public class SocketUsersClient implements UsersClient
{
    @Override
    public List<ViewUsers.UserDisplayDto> getUsers()
    {
        Request request = new Request("users", "view_users", new ViewUsers.Request(0, 100, null));
        return (List<ViewUsers.UserDisplayDto>) SocketService.sendRequest(request);
    }

    @Override
    public void promoteUser(PromoteUserRequest promoteRequest)
    {
        Request request = new Request("users", "promote", promoteRequest);
        SocketService.sendRequest(request);
    }

    @Override
    public void blacklist(BlacklistUserRequest blacklistRequest)
    {
        Request request = new Request("users", "blacklist", blacklistRequest);
        SocketService.sendRequest(request);
    }
}

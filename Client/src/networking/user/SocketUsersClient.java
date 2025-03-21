package networking.user;

import dtos.Request;
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
}

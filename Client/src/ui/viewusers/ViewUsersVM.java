package ui.viewusers;

import dtos.user.ViewUsers;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import networking.user.UsersClient;
import state.AppState;

import java.util.ArrayList;
import java.util.List;

public class ViewUsersVM
{
    private final UsersClient userService;
    private final BooleanProperty showPromoteButtonProp = new SimpleBooleanProperty();
    private final BooleanProperty showBlacklistButtonProp = new SimpleBooleanProperty();
    private final BooleanProperty enableChangePasswordProp = new SimpleBooleanProperty();
    private final ObservableList<UserFx> users = FXCollections.observableArrayList();

    public ViewUsersVM(UsersClient userService)
    {
        this.userService = userService;
        showPromoteButtonProp.set(AppState.getCurrentUser().isAdmin());
        showBlacklistButtonProp.set(AppState.getCurrentUser().isAdmin());
    }

    public BooleanProperty showPromoteButtonPropProperty()
    {
        return showPromoteButtonProp;
    }

    public BooleanProperty showBlacklistButtonPropProperty()
    {
        return showBlacklistButtonProp;
    }

    public BooleanProperty enableChangePasswordPropProperty()
    {
        return enableChangePasswordProp;
    }

    public void loadUsers()
    {

        List<ViewUsers.UserDisplayDto> loadedUsers = userService.getUsers();
        for (ViewUsers.UserDisplayDto user : loadedUsers)
        {
            users.add(new UserFx(user));
        }
    }

    public ObservableList<UserFx> getUsersList()
    {
        return users;
    }
}

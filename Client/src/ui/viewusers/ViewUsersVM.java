package ui.viewusers;

import dtos.user.PromoteUserRequest;
import dtos.user.ViewUsers;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import networking.user.UsersClient;
import startup.ViewHandler;
import state.AppState;
import ui.popup.MessageType;

import java.util.List;

public class ViewUsersVM
{
    private final UsersClient userService;
    private final BooleanProperty showPromoteButtonProp = new SimpleBooleanProperty();
    private final BooleanProperty showBlacklistButtonProp = new SimpleBooleanProperty();
    private final BooleanProperty enableChangePasswordProp = new SimpleBooleanProperty();
    private final ObservableList<UserFx> users = FXCollections.observableArrayList();
    private final IntegerProperty selectedIndexProp = new SimpleIntegerProperty();

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

        try
        {
            List<ViewUsers.UserDisplayDto> loadedUsers = userService.getUsers();
            for (ViewUsers.UserDisplayDto user : loadedUsers)
            {
                users.add(new UserFx(user));
            }
        }
        catch (Exception e)
        {
            ViewHandler.popupMessage(MessageType.ERROR, e.getMessage());
        }
    }

    public ObservableList<UserFx> getUsersList()
    {
        return users;
    }

    public ObservableValue<? extends TableView.TableViewSelectionModel<UserFx>> getSelectionProperty()
    {
        return null;
    }

    public IntegerProperty getSelectedIndexProperty()
    {
        return selectedIndexProp;
    }

    public void promote()
    {
        int index = selectedIndexProp.get();
        if (index < 0) return; // TODO disable promote button instead
        UserFx user = users.get(index);
        PromoteUserRequest request = new PromoteUserRequest(user.emailPropProperty().get());
        try
        {
            userService.promoteUser(request);
            ViewHandler.popupMessage(MessageType.SUCCESS, user.firstNamePropProperty().get() + " has been promoted to admin!");
        }
        catch (Exception e)
        {
            ViewHandler.popupMessage(MessageType.ERROR, e.getMessage());
        }
    }
}

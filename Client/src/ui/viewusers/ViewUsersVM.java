package ui.viewusers;

import dtos.user.BlacklistUserRequest;
import dtos.user.PromoteUserRequest;
import dtos.user.ViewUsers;
import javafx.beans.Observable;
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
    private final BooleanProperty disableChangePasswordButtonProp = new SimpleBooleanProperty(true);
    private final ObservableList<UserFx> users = FXCollections.observableArrayList();
    private final IntegerProperty selectedIndexProp = new SimpleIntegerProperty();

    private final BooleanProperty disablePromoteButtonProp = new SimpleBooleanProperty(true);
    private final BooleanProperty disableBlacklistButtonProp = new SimpleBooleanProperty(true);

    public ViewUsersVM(UsersClient userService)
    {
        this.userService = userService;
        showPromoteButtonProp.set(AppState.getCurrentUser().isAdmin());
        showBlacklistButtonProp.set(AppState.getCurrentUser().isAdmin());
        selectedIndexProp.addListener(this::updateEnablePromoteAndBlacklistButtons);
        selectedIndexProp.addListener(this::updateEnableChangePasswordButton);
    }

    private void updateEnableChangePasswordButton(Observable observable)
    {
        int index = selectedIndexProp.get();
        if (index < 0) return;
        UserFx userFx = users.get(index);
        boolean shouldDisable = !userFx.emailPropProperty().get().equals(AppState.getCurrentUser().email());
        disableChangePasswordButtonProp.set(shouldDisable);
    }

    public BooleanProperty showPromoteButtonPropProperty()
    {
        return showPromoteButtonProp;
    }

    public BooleanProperty showBlacklistButtonPropProperty()
    {
        return showBlacklistButtonProp;
    }

    public BooleanProperty disableChangePasswordButtonPropProperty()
    {
        return disableChangePasswordButtonProp;
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

    public BooleanProperty disablePromoteButtonProp()
    {
        return disablePromoteButtonProp;
    }

    public BooleanProperty disableBlacklistButtonProp()
    {
        return disableBlacklistButtonProp;
    }

    private void updateEnablePromoteAndBlacklistButtons(Observable observable)
    {
        System.out.println("here");
        boolean shouldDisable = selectedIndexProp.get() < 0;
        disablePromoteButtonProp.set(shouldDisable);
        disableBlacklistButtonProp.set(shouldDisable);
    }

    public void promote()
    {
        int index = selectedIndexProp.get();

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

    public void blacklist()
    {
        int index = selectedIndexProp.get();

        UserFx user = users.get(index);

        if (AppState.getCurrentUser().email().equals(user.emailPropProperty().get()))
        {
            ViewHandler.popupMessage(MessageType.ERROR, "You cannot blacklist yourself, dummy.");
            return;
        }

        BlacklistUserRequest request = new BlacklistUserRequest(user.emailPropProperty().get(), "Eventually I will come up with a good reason"); // TODO I should show a popup with a text field, I guess.. eventually
        try
        {
            userService.blacklist(request);
            user.isBlacklistedProperty().set(true);
            ViewHandler.popupMessage(MessageType.WARNING, user.firstNamePropProperty().get() + " has been blacklisted!");
        }
        catch (Exception e)
        {
            ViewHandler.popupMessage(MessageType.ERROR, e.getMessage());
        }
    }

}

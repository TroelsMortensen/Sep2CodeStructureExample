package ui.login;

import dtos.auth.LoginRequest;
import dtos.user.UserDataDto;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import networking.authentication.AuthenticationService;
import startup.ViewHandler;
import startup.ViewType;
import state.AppState;
import utils.StringUtils;

public class LoginVM
{
    private final StringProperty emailProp = new SimpleStringProperty();
    private final StringProperty passwordProp = new SimpleStringProperty();
    private final StringProperty messageProp = new SimpleStringProperty();
    private final BooleanProperty enableLoginButtonProp = new SimpleBooleanProperty(true);

    private final AuthenticationService authService;

    public LoginVM(AuthenticationService authService)
    {
        this.authService = authService;
        emailProp.addListener(this::updateRegisterButtonState);
        passwordProp.addListener(this::updateRegisterButtonState);
    }

    public StringProperty emailProperty()
    {
        return emailProp;
    }

    public StringProperty passwordProperty()
    {
        return passwordProp;
    }

    public StringProperty messageProperty()
    {
        return messageProp;
    }

    public BooleanProperty enableLoginButtonProperty()
    {
        return enableLoginButtonProp;
    }

    private void updateRegisterButtonState(Observable observable)
    {
        boolean shouldDisable =
                StringUtils.isNullOrEmpty(emailProp.get()) ||
                        StringUtils.isNullOrEmpty(passwordProp.get());

        enableLoginButtonProp.set(shouldDisable);
    }

    public void login()
    {
        messageProp.set("");
        LoginRequest loginRequest = new LoginRequest(emailProp.get(), passwordProp.get());
        try
        {
            UserDataDto user = authService.login(loginRequest);
            AppState.setCurrentUser(user);
            ViewHandler.showView(ViewType.VIEWUSERS);
        }
        catch (Exception e)
        {
            messageProp.set(e.getMessage());
        }
    }
}

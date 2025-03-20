package ui.register;

import dtos.auth.RegisterUserRequest;
import javafx.beans.Observable;
import javafx.beans.property.*;
import networking.authentication.AuthenticationService;
import utils.StringUtils;

public class RegisterVM
{
    private final StringProperty emailProp = new SimpleStringProperty();
    private final StringProperty passwordProp = new SimpleStringProperty();
    private final StringProperty repeatProp = new SimpleStringProperty();
    private final StringProperty firstNameProp = new SimpleStringProperty();
    private final StringProperty lastNameProp = new SimpleStringProperty();


    private final StringProperty messageProp = new SimpleStringProperty();
    private final BooleanProperty enableRegisterButtonProp = new SimpleBooleanProperty(true);
    private final AuthenticationService authService;

    public RegisterVM(AuthenticationService authService)
    {
        this.authService = authService;
        emailProp.addListener(this::updateRegisterButtonState);
        passwordProp.addListener(this::updateRegisterButtonState);
        repeatProp.addListener(this::updateRegisterButtonState);
    }

    public void registerUser()
    {
        messageProp.set(""); // clea potential existing message

        // validate all input is present
        if (emailProp.get() == null || emailProp.get().isEmpty())
        {
            messageProp.set("Email cannot be empty");
            return;
        }
        if (firstNameProp.get() == null || firstNameProp.get().isEmpty())
        {
            messageProp.set("First name cannot be empty");
            return;
        }
        if (lastNameProp.get() == null || lastNameProp.get().isEmpty())
        {
            messageProp.set("Last name cannot be empty");
            return;
        }
        if (passwordProp.get() == null || passwordProp.get().isEmpty())
        {
            messageProp.set("Password cannot be empty");
            return;
        }
        if (!passwordProp.get().equals(repeatProp.get()))
        {
            messageProp.set("Passwords do not match");
            System.out.println(passwordProp.get()+repeatProp.get());
            return;
        }

        try
        {
            authService.registerUser(new RegisterUserRequest(
                    emailProp.get(),
                    passwordProp.get(),
                    firstNameProp.get(),
                    lastNameProp.get())
            );

            messageProp.set("Success");
            // clear fields
            clearFields();
        }
        catch (Exception e)
        {
            // might receive exception from lower layer (i.e. client)
            messageProp.set(e.getMessage());
        }
    }

    private void clearFields()
    {
        emailProp.set("");
        passwordProp.set("");
        repeatProp.set("");
        firstNameProp.set("");
        lastNameProp.set("");
    }

    private void updateRegisterButtonState(Observable observable)
    {
        boolean shouldDisable =
                StringUtils.isNullOrEmpty(emailProp.get()) ||
                        StringUtils.isNullOrEmpty(passwordProp.get()) ||
                        StringUtils.isNullOrEmpty(repeatProp.get()) ||
                        StringUtils.isNullOrEmpty(firstNameProp.get()) ||
                        StringUtils.isNullOrEmpty(lastNameProp.get());

        enableRegisterButtonProp.set(shouldDisable);
    }

    public StringProperty emailProperty()
    {
        return emailProp;
    }

    public StringProperty passwordProperty()
    {
        return passwordProp;
    }

    public StringProperty repeatProperty()
    {
        return repeatProp;
    }

    public StringProperty messageProperty()
    {
        return messageProp;
    }

    public BooleanProperty enableRegisterButtonProperty()
    {
        return enableRegisterButtonProp;
    }

    public StringProperty firstNameProperty()
    {
        return firstNameProp;
    }

    public StringProperty lastNameProperty()
    {
        return lastNameProp;
    }
}

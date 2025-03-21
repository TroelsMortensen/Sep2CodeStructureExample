package ui.viewusers;

import dtos.user.ViewUsers;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserFx
{
    private final StringProperty emailProp = new SimpleStringProperty();
    private final StringProperty firstNameProp = new SimpleStringProperty();
    private final StringProperty lastNameProp = new SimpleStringProperty();
    private final BooleanProperty isBlacklisted = new SimpleBooleanProperty();

    public UserFx(ViewUsers.UserDisplayDto user)
    {
        emailProp.set(user.email());
        firstNameProp.set(user.firstName());
        lastNameProp.set(user.lastName());
        isBlacklisted.set(user.isBlacklisted());
    }

    public StringProperty emailPropProperty()
    {
        return emailProp;
    }

    public StringProperty firstNamePropProperty()
    {
        return firstNameProp;
    }

    public StringProperty lastNamePropProperty()
    {
        return lastNameProp;
    }

    public BooleanProperty isBlacklistedProperty()
    {
        return isBlacklisted;
    }
}

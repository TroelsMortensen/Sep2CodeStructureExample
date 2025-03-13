package persistence.repositories.user;

import model.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserListRepository implements UserRepository{

    private final static List<User> users = new ArrayList<>();

    @Override
    public void add(User user) {
        users.add(user);
    }

    @Override
    public User getSingle(String email) {
        for (User user : users) {
            if(email.equals(user.getEmail()))
            {
                return user;
            }
        }
        return null;
    }

    @Override
    public void delete(String email) {

    }

    @Override
    public void save(User user) {
        // simulate updating user, by first removing existing, then inserting updated user
        users.removeIf(usr -> usr.getEmail().equals(user.getEmail()));

        users.add(user);
    }

    @Override
    public void getMany() {

    }
}

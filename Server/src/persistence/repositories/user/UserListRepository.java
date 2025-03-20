package persistence.repositories.user;

import model.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserListRepository implements UserRepository {

    private final static List<User> users = new ArrayList<>();

    @Override
    public void add(User user) {
        users.add(user);
    }


    /**
     * Just find a User entity by a given email.
     * Return null, if no matching user was found.
     * I could have thrown an exception instead, it might be simpler.
     * @param email
     * @return
     */
    @Override
    public User getSingle(String email) {
        for (User user : users) {
            if (email.equals(user.getEmail())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void delete(String email) {
        // Will implement later. Just remove from list.
    }

    @Override
    public void save(User user) {
        // simulate updating user, by first removing existing, then inserting updated user
        users.removeIf(usr -> usr.getEmail().equals(user.getEmail()));

        users.add(user);
    }

    @Override
    public List<User> getMany(int pageIndex, int pageSize, String firstNameContains) {
        List<User> result = new ArrayList<>();
        for (int i = 0; i < pageSize; i++) {
            // this is an attempt at implementing paging, as seen in dbs, maybe it works
            User user = users.get(pageIndex * pageSize + i);
            if(user.getFirstName().contains(firstNameContains)){
                result.add(user);
            }
        }
        return result;
    }
}

package persistence.repositories.user;

import model.entities.User;

import java.util.List;

public interface UserRepository {
    void add(User user);
    User getSingle(String email);
    void delete(String email);
    void save(User user);
    List<User> getMany(int i, int pageSize, String s);
}

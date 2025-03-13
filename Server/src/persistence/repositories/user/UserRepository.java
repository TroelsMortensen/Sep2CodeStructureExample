package persistence.repositories.user;

import model.entities.User;

public interface UserRepository {
    void add(User user);
    User getSingle(String email);
    void delete(String email);
    void save(User user);
    void getMany();
}

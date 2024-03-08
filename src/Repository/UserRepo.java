package Repository;

import model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo {
    List<User> getAllUsers();
    void createUser(User user);
    void deleteUser(Integer id);
    User updateUser(User user);
    Optional<User> searchUser(Integer id);
    List<User> getAllUsersSortedByName();

}

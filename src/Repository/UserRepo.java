package Repository;

import model.User;

import java.util.List;

public interface UserRepo {
    List<User> getAllUsers();
    void createUser(User user);
    void deleteUser(Integer id);
    User updateUser(User user);

}

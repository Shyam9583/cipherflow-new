package service;

import model.User;

public interface UserService {
    boolean createUser(User user);

    User getUser(String userId);

    boolean updateUser(User user);

    boolean deleteUser(User user);

    boolean deleteUser(String userId);
}

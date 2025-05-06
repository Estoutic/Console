package storage;

import exceptions.UserNotFoundException;
import model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserStorage {
    private final Map<String, User> users;

    public UserStorage() {
        this.users = new HashMap<>();
    }

    public boolean addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        String userName = user.getUserName();
        if (hasUser(userName)) {
            return false;
        }

        users.put(userName, user);
        return true;
    }

    public User getUser(String userName) {
        User user = users.get(userName);
        if (user == null) {
            throw new UserNotFoundException(userName);
        }
        return user;
    }

    public boolean hasUser(String userName) {
        return users.containsKey(userName);
    }

    public int getUserCount() {
        return users.size();
    }

    public Set<String> getAllUserNames() {
        return users.keySet();
    }
}
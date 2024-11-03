package org.das.dao;

import org.das.model.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserDao {
    private final Map<String, User> users;

    public UserDao() {
        this.users = new HashMap<>();
    }

    public Optional<User> getUser(String login) {
        return Optional.ofNullable(users.get(login));
    }

    public Optional<User> getUser(UUID id) {
        return Optional.ofNullable(users.get(id));
    }

    public void saveUser(User user) {
        this.users.put(user.getLogin(), user);
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public boolean userExist(String login) {
       return users.containsKey(login);
    }
}

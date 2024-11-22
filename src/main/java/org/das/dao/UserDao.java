package org.das.dao;

import org.das.model.User;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserDao {
    private final Map<String, User> users;

    public UserDao() {
        this.users = new HashMap<>();
    }

    public Optional<User> getUser(UUID id) {
        return getUsers().stream().filter(user -> user.getUserId().equals(id)).findFirst();
    }

    public User saveUser(User user) {
        this.users.put(user.getLogin(), user);
        return user;
    }

    public Collection<User> getUsers() {
        return users.values();
    }

    public boolean userExist(String login) {
       return users.containsKey(login);
    }
}
package org.das.dao;

import org.das.model.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDao {
    private final Map<String, User> users;

    public UserDao() {
        this.users = new HashMap<>();
    }

    public User getUser(String login) {
        return users.get(login);
    }

    public void setUser(User user) {
        this.users.put(user.getLogin(), user);
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public boolean userExist(String login) {
       return users.containsKey(login);
    }
}

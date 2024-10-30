package org.das.service;

import org.das.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private final Map<String, User> userMap;

    public UserServiceImpl() {
        this.userMap = new HashMap<>();
    }

    @Override
    public User userCreate(String login) {
        return null;
    }

    @Override
    public void showAllUsers() {
    }
}

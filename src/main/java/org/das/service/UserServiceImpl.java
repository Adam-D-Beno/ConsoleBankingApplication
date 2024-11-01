package org.das.service;

import org.das.model.Account;
import org.das.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private final Map<String, User> userMap;
    private final AccountService accountService;

    @Autowired
    public UserServiceImpl(AccountServiceImpl accountService) {
        this.accountService = accountService;
        this.userMap = new HashMap<>();

    }

    @Override
    public User userCreate(String login) {
        if (userMap.containsKey(login)) {
            return userMap.get(login);
        }
        User user = new User(login);
        Account account = accountService.accountCreate(user.getUserId());
        user.setAccounts(account);
        userMap.put(user.getLogin(), user);
        return user;
    }

    @Override
    public void showAllUsers() {
        userMap.forEach((key, value) -> System.out.println(value));
    }
}

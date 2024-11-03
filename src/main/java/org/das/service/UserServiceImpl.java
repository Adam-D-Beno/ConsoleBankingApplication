package org.das.service;

import org.das.dao.UserDao;
import org.das.model.Account;
import org.das.model.User;
import org.das.validate.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao users;
    private final AccountService accountService;
    private final UserValidation userValidation;

    @Autowired
    public UserServiceImpl(UserDao users, AccountServiceImpl accountService, UserValidation userValidation) {
        this.users = users;
        this.accountService = accountService;
        this.userValidation = userValidation;
    }

    @Override
    public User userCreate(String login) {
        if (users.userExist(login)) {
            throw new RuntimeException("User with login: " + login + " all read exist");
        }
        User user = new User(login);
        user.setAccounts(accountService.accountCreate(user.getUserId()));
        users.saveUser(user);
        return user;
    }

    @Override
    public void showAllUsers() {
        users.getUsers().forEach((key, value) -> System.out.println(value));
    }
}

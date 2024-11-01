package org.das.service;

import org.das.dao.UserDao;
import org.das.model.Account;
import org.das.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao users;
    private final AccountService accountService;

    @Autowired
    public UserServiceImpl(UserDao users, AccountServiceImpl accountService) {
        this.users = users;
        this.accountService = accountService;

    }

    @Override
    public User userCreate(String login) {
        if (users.userExist(login)) {
            return users.getUser(login);
        }
        User user = new User(login);
        Account account = accountService.accountCreate(user.getUserId());
        user.setAccounts(account);
        users.setUser(user);
        return user;
    }

    @Override
    public void showAllUsers() {
        users.getUsers().forEach((key, value) -> System.out.println(value));
    }
}

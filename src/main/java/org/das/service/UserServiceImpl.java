package org.das.service;

import org.das.dao.UserDao;
import org.das.model.User;
import org.das.validate.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final AccountService accountService;
    private final UserValidation userValidation;

    @Autowired
    public UserServiceImpl(UserDao userDao, AccountServiceImpl accountService, UserValidation userValidation) {
        this.userDao = userDao;
        this.accountService = accountService;
        this.userValidation = userValidation;
    }

    @Override
    public User create(String login) {
        userValidation.userLoginCorrect(login);
        userValidation.userAlreadyExist(login);
        User user = new User(login);
        userDao.saveUser(user);
        user.setAccounts(accountService.create(user.getUserId()));
        return user;
    }

    @Override
    public void showAllUsers() {
        userDao.getUsers().forEach(System.out::println);
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        return userDao.getUser(id);
    }
}

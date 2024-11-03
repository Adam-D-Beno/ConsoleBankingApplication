package org.das.validate;

import org.apache.commons.lang3.StringUtils;
import org.das.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidation {
    private final UserDao userDao;

    @Autowired
    public UserValidation(UserDao userDao) {
        this.userDao = userDao;
    }

    public void userAlreadyExist(String login) {
        if (userDao.userExist(login)) {
            throw new RuntimeException("User with login: " + login + " already exist");
        }
    }

    public void userLoginCorrect(String login) {
       if (StringUtils.isBlank(login)) {
           throw new RuntimeException("Login: " + login + " is NULL or Empty");
       }
    }
}

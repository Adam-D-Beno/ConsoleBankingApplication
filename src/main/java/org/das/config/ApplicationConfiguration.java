package org.das.config;

import org.das.dao.AccountDao;
import org.das.dao.UserDao;
import org.das.service.AccountService;
import org.das.service.AccountServiceImpl;
import org.das.service.UserService;
import org.das.service.UserServiceImpl;
import org.das.utils.AccountProperties;
import org.das.validate.AccountValidation;
import org.das.validate.UserValidation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Scanner;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }

    @Bean
    public AccountValidation accountValidation() {
        return new AccountValidation();
    }

    @Bean
    public UserValidation userValidation(UserDao userDao) {
        return new UserValidation(userDao);
    }

    @Bean
    public AccountDao accountDao() {
        return new AccountDao(new HashMap<>());
    }

    @Bean
    public UserDao userDao() {
        return new UserDao();
    }

    @Bean
    public AccountService accountService(UserDao userDao,
                                         AccountDao accountDao,
                                         AccountValidation accountValidation,
                                         AccountProperties accountProperties
    ) {
        return new AccountServiceImpl(userDao, accountDao, accountValidation, accountProperties);
    }

    @Bean
    public UserService userService(UserDao userDao,

                                   AccountService accountService,
                                   UserValidation userValidation) {
        return new UserServiceImpl(userDao, accountService, userValidation);
    }

    @Bean
    public AccountProperties accountProperties(
            @Value("${account.default-amount}") int defaultAmount,
            @Value("${account.transfer-commission}") double transferCommission) {
        return new AccountProperties(defaultAmount, transferCommission);
    }
}

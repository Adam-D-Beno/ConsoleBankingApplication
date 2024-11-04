package org.das.utils;

import org.das.validate.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ExecuteUserInputs {
    private final UserValidation userValidation;

    @Autowired
    public ExecuteUserInputs(UserValidation userValidation) {
        this.userValidation = userValidation;
    }

    public String executeOperationsUserCreate(Scanner scanner) {
        System.out.println("Please enter the login for new user");
        String login = scanner.nextLine();
        userValidation.userLoginCorrect(login);
        return login;
    }
    public void executeOperationsShowAllUsers(Scanner scanner) {
    }
    public void executeOperationsAccountCreate(Scanner scanner) {
    }
    public void executeOperationsAccountClose(Scanner scanner) {
    }
    public void executeOperationsAccountWithdraw(Scanner scanner) {
    }
    public void executeOperationsAccountDeposit(Scanner scanner) {
    }
    public void executeOperationsAccountTransfer(Scanner scanner) {
    }
}

package org.das;

import org.das.model.Account;
import org.das.model.User;
import org.das.service.AccountService;
import org.das.service.UserService;
import org.das.utils.ExecuteUserInputs;
import org.das.validate.AccountValidation;
import org.das.validate.UserValidation;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.Scanner;

public class OperationsConsoleListener {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("org.das");
        AccountService accountService = context.getBean(AccountService.class);
        UserService userService = context.getBean(UserService.class);
        AccountValidation accountValidation = context.getBean(AccountValidation.class);
        UserValidation userValidation = context.getBean(UserValidation.class);
        ExecuteUserInputs executeUserInputs = context.getBean(ExecuteUserInputs.class);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("""
                     Please enter one of operation type:
                    -ACCOUNT_CREATE
                    -SHOW_ALL_USERS
                    -ACCOUNT_CLOSE
                    -ACCOUNT_WITHDRAW
                    -ACCOUNT_DEPOSIT
                    -ACCOUNT_TRANSFER
                    -USER_CREATE""");

            String userEnter = scanner.nextLine();
            if (userEnter.equals("-ACCOUNT_CREATE")) {
                String login = executeUserInputs.executeOperationsUserCreate(scanner);
                User user = userService.userCreate(login);
                System.out.println("Account created successfully " + user.toString());
            }
            System.out.println(userEnter);
        }
    }
}
package org.das;

import org.das.model.Account;
import org.das.service.AccountService;
import org.das.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.Scanner;

public class OperationsConsoleListener {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("org.das");
        AccountService accountService = context.getBean(AccountService.class);
        UserService userService = context.getBean(UserService.class);
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
            System.out.println(userEnter);
        }
    }
}
package org.das.utils;

import org.das.model.Account;
import org.das.model.User;
import org.das.service.AccountService;
import org.das.service.UserService;
import org.das.validate.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.UUID;

@Component
public class ExecuteOperation {
    private final UserValidation userValidation;
    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    public ExecuteOperation(UserValidation userValidation, UserService userService,
                            AccountService accountService) {
        this.userValidation = userValidation;
        this.userService = userService;
        this.accountService = accountService;
    }

    public void executeOperationsUserCreate(Scanner scanner) {
        System.out.println("Please enter the login for new user ");
        String login = scanner.nextLine();
        userValidation.userLoginCorrect(login);
        User user = userService.userCreate(login);
        System.out.println("User created successfully " + user.toString());

    }
    public void executeOperationsAccountCreate(Scanner scanner) {
        System.out.println("Enter the user id for which to create an account: ");
        String userId = scanner.nextLine();
        userValidation.userLoginCorrect(userId);
        Account account = accountService.accountCreate(UUID.fromString(userId));
        System.out.println("New account created with ID: " + account.getAccountId() +
                userService.getUserByLogin(account.getUserId()).get().getLogin());
    }
    public void executeOperationsAccountClose(Scanner scanner) {
        System.out.println("Enter account ID to close: ");
        String accountId = scanner.nextLine();
        userValidation.userLoginCorrect(accountId);
        accountService.accountClose(UUID.fromString(accountId));
        System.out.println("Account with ID " + accountId + " has been closed.");
    }
    public void executeOperationsAccountWithdraw(Scanner scanner) {
        System.out.println("Enter account ID to withdraw from: ");
        String accountId = scanner.nextLine();
        userValidation.userLoginCorrect(accountId);
        System.out.println("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        accountService.accountWithdraw(UUID.fromString(accountId), BigDecimal.valueOf(amount));
        System.out.println("Amount " + amount + " withdraw to account ID: " + accountId);
    }
    public void executeOperationsAccountDeposit(Scanner scanner) {
        System.out.println("Enter account ID: ");
        String accountId = scanner.nextLine();
        userValidation.userLoginCorrect(accountId);
        System.out.println("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        accountService.accountDeposit(UUID.fromString(accountId), BigDecimal.valueOf(amount));
        System.out.println("Amount " + amount + " deposited to account ID: " + accountId);
    }
    public void executeOperationsAccountTransfer(Scanner scanner) {
        System.out.println("Enter source account ID: ");
        String source = scanner.nextLine();
        userValidation.userLoginCorrect(source);
        System.out.println("Enter target account ID: ");
        String target = scanner.nextLine();
        userValidation.userLoginCorrect(target);
        System.out.println("Enter amount to transfer: ");
        double amount = scanner.nextDouble();
        accountService.accountTransfer(UUID.fromString(source), UUID.fromString(target), BigDecimal.valueOf(amount));
        System.out.println(" Amount " + amount + " transferred from account ID " + source + " to account ID " + target);
    }
    public void showAllUsers() {
        System.out.println("List of all users: ");
        userService.showAllUsers();
    }
}

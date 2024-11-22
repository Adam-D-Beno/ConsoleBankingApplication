package org.das.service;

import org.das.model.Account;
import org.das.model.User;
import org.das.validate.UserValidation;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.UUID;

public class ExecuteOperation {
    private  final UserValidation userValidation;
    private  final UserService userService;
    private  final AccountService accountService;

    public ExecuteOperation(UserValidation userValidation,
                            UserService userService,
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
        System.out.println("User %s created successfully ".formatted(user));
    }

    public void executeOperationsAccountCreate(Scanner scanner) {
        System.out.println("Enter the user id for which to create an account: ");
        String userId = scanner.nextLine();
        userValidation.userLoginCorrect(userId);
        Account account = accountService.accountCreate(UUID.fromString(userId));
        User user = userService.getUserById(account.getUserId()).get();
        user.addAccount(account);
        System.out.println("New account created with ID =%s for user with id=%s"
                .formatted(account.getAccountId(), user.getUserId()));

    }

    public void executeOperationsAccountClose(Scanner scanner) {
        System.out.println("Enter account ID to close: ");
        String accountId = scanner.nextLine();
        userValidation.userLoginCorrect(accountId);
        Account account = accountService.accountClose(UUID.fromString(accountId));
        User user = userService.getUserById(account.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("No such user with id=%s"
                        .formatted(account.getUserId())));
        user.getAccounts().remove(account);
        System.out.println("Account with ID=%s fro user id=%s has been closed"
                .formatted(account.getAccountId(), user.getUserId()));
    }

    public void executeOperationsAccountWithdraw(Scanner scanner) {
        System.out.println("Enter account ID to withdraw from: ");
        String accountId = scanner.nextLine();
        userValidation.userLoginCorrect(accountId);
        System.out.println("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        accountService.accountWithdraw(UUID.fromString(accountId), BigDecimal.valueOf(amount));
        System.out.println("Amount %s withdraw to account ID=%s ".formatted(amount, accountId));
    }

    public void executeOperationsAccountDeposit(Scanner scanner) {
        System.out.println("Enter account ID: ");
        String accountId = scanner.nextLine();
        userValidation.userLoginCorrect(accountId);
        System.out.println("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        accountService.accountDeposit(UUID.fromString(accountId), BigDecimal.valueOf(amount));
        System.out.println("Amount %s deposit to account ID=%s ".formatted(amount, accountId));
    }

    public void executeOperationsAccountTransfer(Scanner scanner) {
        System.out.println("Enter source account ID: ");
        String accountIdFrom = scanner.nextLine();
        userValidation.userLoginCorrect(accountIdFrom);
        System.out.println("Enter target account ID: ");
        String accountIdTo = scanner.nextLine();
        userValidation.userLoginCorrect(accountIdTo);
        System.out.println("Enter amount to transfer: ");
        double amount = scanner.nextDouble();
        accountService.accountTransfer(UUID.fromString(accountIdFrom), UUID.fromString(accountIdTo), BigDecimal.valueOf(amount));
        System.out.println("Amount %s transferred from account ID =%s to account ID =%s"
                .formatted(amount, accountIdFrom, accountIdTo));
    }

    public void showAllUsers() {
        System.out.println("List of all users: ");
        userService.showAllUsers();
    }
}

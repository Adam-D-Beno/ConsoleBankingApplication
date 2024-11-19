package org.das.service.oparations;

import org.das.service.AccountService;
import org.das.utils.ConsoleOperationType;
import org.das.validate.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.UUID;

@Component
public class DepositCommand implements OperationCommand {
    private final UserValidation userValidation;
    private final AccountService accountService;
    private final Scanner scanner;

    @Autowired
    public DepositCommand(UserValidation userValidation, AccountService accountService, Scanner scanner) {
        this.userValidation = userValidation;
        this.accountService = accountService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter account ID: ");
        String accountId = scanner.nextLine();
        userValidation.userLoginCorrect(accountId);
        System.out.println("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        accountService.deposit(UUID.fromString(accountId), BigDecimal.valueOf(amount));
        System.out.println("Amount " + amount + " deposited to account ID: " + accountId);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }
}

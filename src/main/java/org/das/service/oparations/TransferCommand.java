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
public class TransferCommand implements OperationCommand {
    private final UserValidation userValidation;
    private final AccountService accountService;
    private final Scanner scanner;

    @Autowired
    public TransferCommand(UserValidation userValidation, AccountService accountService, Scanner scanner) {
        this.userValidation = userValidation;
        this.accountService = accountService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter source account ID: ");
        String source = scanner.nextLine();
        userValidation.userLoginCorrect(source);
        System.out.println("Enter target account ID: ");
        String target = scanner.nextLine();
        userValidation.userLoginCorrect(target);
        System.out.println("Enter amount to transfer: ");
        double amount = scanner.nextDouble();
        accountService.transfer(UUID.fromString(source), UUID.fromString(target), BigDecimal.valueOf(amount));
        System.out.println(" Amount " + amount + " transferred from account ID " + source + " to account ID " + target);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_TRANSFER;
    }
}

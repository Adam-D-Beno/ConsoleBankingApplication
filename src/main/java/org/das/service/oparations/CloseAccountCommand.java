package org.das.service.oparations;

import org.das.model.Account;
import org.das.model.User;
import org.das.service.AccountService;
import org.das.service.UserService;
import org.das.utils.ConsoleOperationType;
import org.das.validate.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Scanner;
import java.util.UUID;

@Component
public class CloseAccountCommand implements OperationCommand {
    private final UserValidation userValidation;
    private final AccountService accountService;
    private final UserService userService;
    private final Scanner scanner;

    @Autowired
    public CloseAccountCommand(UserValidation userValidation,
                               AccountService accountService,
                               UserService userService,
                               Scanner scanner) {
        this.userValidation = userValidation;
        this.accountService = accountService;
        this.userService = userService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter account ID to close: ");
        String accountId = scanner.nextLine();
        userValidation.userLoginCorrect(accountId);
        Account account = accountService.close(UUID.fromString(accountId));
        User user = userService.getUserById(account.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("No such user with id=%s"
                        .formatted(account.getUserId())));
        user.getAccounts().remove(account);
        System.out.println("Account with ID=%s fro user id=%s has been closed"
                .formatted(account.getAccountId(), user.getUserId()));
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CLOSE;
    }
}

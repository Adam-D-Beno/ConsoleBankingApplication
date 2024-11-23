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

import static java.util.UUID.fromString;

@Component
public class CreateAccountCommand implements OperationCommand {
    private final UserValidation userValidation;
    private final AccountService accountService;
    private final UserService userService;
    private final Scanner scanner;

    @Autowired
    public CreateAccountCommand(UserValidation userValidation,
                                AccountService accountService,
                                UserService userService,
                                Scanner scanner) {
        this.userValidation = userValidation;
        this.accountService = accountService;
        this.userService = userService;
        this.scanner = scanner;
    }


    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }

    @Override
    public void execute() {
        System.out.println("Enter the user id for which to create an account: ");
        String userId = scanner.nextLine();
        userValidation.userLoginCorrect(userId);
        UUID uuid;
        try {
            uuid = fromString(userId);
        } catch (Exception e) {
            throw new IllegalArgumentException("wrong input user id " +e.getMessage());
        }
        User user = userService.getUserById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("No such user with id%s"
                        .formatted(userId)));
        Account account = accountService.create(user.getUserId());
        user.addAccount(account);
        System.out.println("New account created with ID =%s for user with id=%s"
                .formatted(account.getAccountId(), user.getUserId()));
    }
}

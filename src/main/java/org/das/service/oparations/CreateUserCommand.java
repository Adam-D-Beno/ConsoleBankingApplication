package org.das.service.oparations;

import org.das.model.User;
import org.das.service.UserService;
import org.das.utils.ConsoleOperationType;
import org.das.validate.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CreateUserCommand implements OperationCommand {
    private final UserValidation userValidation;
    private final UserService userService;
    private final Scanner scanner;

    @Autowired
    public CreateUserCommand(UserValidation userValidation, UserService userService, Scanner scanner) {
        this.userValidation = userValidation;
        this.userService = userService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Please enter the login for new user ");
        String login = scanner.nextLine();
        userValidation.userLoginCorrect(login);
        User user = userService.create(login);
        System.out.println("User created successfully " + user.toString());
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.USER_CREATE;
    }
}

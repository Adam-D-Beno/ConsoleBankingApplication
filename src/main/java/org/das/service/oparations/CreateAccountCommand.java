package org.das.service.oparations;

import org.das.model.User;
import org.das.service.UserService;
import org.das.utils.ConsoleOperationType;
import org.das.validate.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class CreateAccountCommand implements OperationCommand {
    private final UserValidation userValidation;
    private final UserService userService;

    @Autowired
    public CreateAccountCommand(UserValidation userValidation, UserService userService) {
        this.userValidation = userValidation;
        this.userService = userService;
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the login for new user ");
        String login = scanner.nextLine();
        userValidation.userLoginCorrect(login);
        User user = userService.create(login);
        System.out.println("User created successfully " + user.toString());
    }
}

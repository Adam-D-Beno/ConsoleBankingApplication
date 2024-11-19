package org.das.service.oparations;

import org.das.service.UserService;
import org.das.utils.ConsoleOperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShowAllUsersCommand implements OperationCommand {
    private final UserService userService;

    @Autowired
    public ShowAllUsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute() {
        System.out.println("List of all users: ");
        userService.showAllUsers();
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.SHOW_ALL_USERS;
    }
}

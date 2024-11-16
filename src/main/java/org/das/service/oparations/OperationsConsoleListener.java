package org.das.service.oparations;

import org.das.utils.ConsoleOperationType;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OperationsConsoleListener implements Runnable {
    private Map<ConsoleOperationType, OperationCommand> commandMap;
    private final Scanner scanner;

    public OperationsConsoleListener(List<OperationCommand> commands, Scanner scanner) {
        this.scanner = scanner;
        commandMap = new ConcurrentHashMap<>();
        commands.forEach(command -> commandMap.put(command.getOperationType(), command));
    }

    @Override
    public void run() {
        while (true) {
            String userInput = listenNextOperation();
            try {
                ConsoleOperationType operationType = ConsoleOperationType.valueOf(userInput.toUpperCase());
                Optional.ofNullable(commandMap.get(operationType)).ifPresent(OperationCommand::execute);
            } catch (Exception e) {
                System.out.printf("Error executing command %s: error=%s%n", userInput, e.getMessage());
            }
        }
    }

    private String listenNextOperation() {
        System.out.println("""
                 Please enter one of operation type:
                -ACCOUNT_CREATE
                -SHOW_ALL_USERS
                -ACCOUNT_CLOSE
                -ACCOUNT_WITHDRAW
                -ACCOUNT_DEPOSIT
                -ACCOUNT_TRANSFER
                -USER_CREATE""");

        return scanner.nextLine();
    }
}
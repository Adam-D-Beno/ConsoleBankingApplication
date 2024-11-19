package org.das.service;

import org.das.service.oparations.OperationCommand;
import org.das.utils.ConsoleOperationType;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class OperationsConsoleListener implements Runnable {
    private final Map<ConsoleOperationType, OperationCommand> commandMap;
    private final Scanner scanner;

    public OperationsConsoleListener(Map<ConsoleOperationType,
            OperationCommand> commandMap, Scanner scanner) {
        this.commandMap = commandMap;
        this.scanner = scanner;
    }

    @Override
    public void run() {
        listenUpdates();
    }

    private void listenUpdates() {
        while (true) {
            var nextOperation =  listenNextOperation();
            processNextOperation(nextOperation);
        }
    }

    private void processNextOperation(ConsoleOperationType operation) {
        try {
          commandMap.get(operation).execute();
        } catch (Exception e) {
            System.out.printf("Error executing command %s: error=%s%n", operation, e.getMessage());
        }
    }

    private ConsoleOperationType listenNextOperation() {
        System.out.println("""
                 Please enter one of operation type:
                -ACCOUNT_CREATE
                -SHOW_ALL_USERS
                -ACCOUNT_CLOSE
                -ACCOUNT_WITHDRAW
                -ACCOUNT_DEPOSIT
                -ACCOUNT_TRANSFER
                -USER_CREATE""");
        while (true) {
         var nextOperation = scanner.nextLine();
            try{
                return ConsoleOperationType.valueOf(nextOperation.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("No such command found");
            }
        }
    }
}
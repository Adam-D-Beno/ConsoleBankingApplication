package org.das;

import org.das.utils.ConsoleOperationType;
import org.das.utils.ExecuteOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Scanner;
@Component
public class OperationsConsoleListener {
    private final Scanner scanner;
    private final ExecuteOperation executeOperation;

    @Autowired
    public OperationsConsoleListener(Scanner scanner, ExecuteOperation executeOperation) {
        this.scanner = scanner;
        this.executeOperation = executeOperation;
    }

    public void listenUpdates() {
        while (true) {
            var operationType = listenNextOperation();
            try {
                processNextOperation(operationType);
            } catch (Exception e) {
                System.out.printf("Error executing command %s: error=%s%n", operationType, e.getMessage());
            }
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
            try {
                var nextOperation = scanner.nextLine();
                return ConsoleOperationType.valueOf(nextOperation);
            } catch (IllegalArgumentException e) {
                System.out.println("No such command ");
            }
        }
    }

    private void processNextOperation(ConsoleOperationType operation) {

        if (operation.equals(ConsoleOperationType.ACCOUNT_CREATE)) {
            executeOperation.executeOperationsAccountCreate(scanner);
            return;
        }
        if (operation.equals(ConsoleOperationType.SHOW_ALL_USERS)) {
            executeOperation.showAllUsers();
            return;
        }
        if (operation.equals(ConsoleOperationType.ACCOUNT_CLOSE)) {
            executeOperation.executeOperationsAccountClose(scanner);
            return;
        }
        if (operation.equals(ConsoleOperationType.ACCOUNT_WITHDRAW)) {
            executeOperation.executeOperationsAccountWithdraw(scanner);
            return;
        }
        if (operation.equals(ConsoleOperationType.ACCOUNT_DEPOSIT)) {
            executeOperation.executeOperationsAccountDeposit(scanner);
            return;
        }
        if (operation.equals(ConsoleOperationType.USER_CREATE)) {
            executeOperation.executeOperationsUserCreate(scanner);
            return;
        }
        if (operation.equals(ConsoleOperationType.ACCOUNT_TRANSFER)) {
            executeOperation.executeOperationsAccountTransfer(scanner);
        }
    }
}
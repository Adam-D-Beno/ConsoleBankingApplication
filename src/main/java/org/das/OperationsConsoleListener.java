package org.das;

import org.das.utils.ExecuteOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

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
            String operationType = listenNextOperation();
            try {
                processNextOperation(operationType);
            } catch (Exception e) {
                System.out.printf("Error executing command %s: error=%s%n", operationType, e.getMessage());
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

    private void processNextOperation(String operation) {

        if (operation.equals("ACCOUNT_CREATE")) {
            executeOperation.executeOperationsAccountCreate(scanner);
        }
        if (operation.equals("SHOW_ALL_USERS")) {
            executeOperation.showAllUsers();
        }
        if (operation.equals("ACCOUNT_CLOSE")) {
            executeOperation.executeOperationsAccountClose(scanner);
        }
        if (operation.equals("ACCOUNT_WITHDRAW")) {
            executeOperation.executeOperationsAccountWithdraw(scanner);
        }
        if (operation.equals("ACCOUNT_DEPOSIT")) {
            executeOperation.executeOperationsAccountDeposit(scanner);
        }
        if (operation.equals("USER_CREATE")) {
            executeOperation.executeOperationsUserCreate(scanner);
        }
        if (operation.equals("ACCOUNT_TRANSFER")) {
            executeOperation.executeOperationsAccountTransfer(scanner);
        }
    }
}
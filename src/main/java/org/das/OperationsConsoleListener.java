package org.das;

import org.das.utils.ConsoleOperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

@Component
public class OperationsConsoleListener {
    private final Scanner scanner;
    private final Map<ConsoleOperationType, Consumer<Scanner>> operationMap;

    @Autowired
    public OperationsConsoleListener(Scanner scanner,
                                     Map<ConsoleOperationType, Consumer<Scanner>> operationMap) {
        this.scanner = scanner;
        this.operationMap = operationMap;
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
        System.out.println("Please enter one of operation type");
        printAllAvailableOperation();

        while (true) {
            try {
                var nextOperation = scanner.nextLine();
                return ConsoleOperationType.valueOf(nextOperation);
            } catch (IllegalArgumentException e) {
                System.out.println("No such command ");
            }
        }
    }

    private void printAllAvailableOperation() {
        operationMap.keySet().forEach(System.out::println);
    }

    private void processNextOperation(ConsoleOperationType operation) {
        operationMap.get(operation).accept(scanner);
    }
}
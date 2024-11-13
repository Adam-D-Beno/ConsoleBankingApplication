package org.das;

import org.das.service.oparations.OperationCommand;
import org.das.utils.ConsoleOperationType;
import org.das.utils.ExecuteOperation;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
@Component
public class OperationsConsoleListener implements Runnable {
    private Map<ConsoleOperationType, OperationCommand> commandMap;

    public OperationsConsoleListener(List<OperationCommand> commands) {
        commands.forEach(command -> commandMap.put(command.getOperationType(), command));
    }

    @Override
    public void run() {

    }
}
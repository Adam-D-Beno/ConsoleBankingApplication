package org.das.config;

import org.das.service.ExecuteOperation;
import org.das.utils.ConsoleOperationType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

@Configuration
public class OperationCommandConfig {

    @Bean
    public Map<ConsoleOperationType, Consumer<Scanner>> processorMap(Scanner scanner, ExecuteOperation executeOperation) {
        var processorMap = new EnumMap<ConsoleOperationType, ExecuteOperation>(ConsoleOperationType.class);
        processorMap.put(ConsoleOperationType.USER_CREATE, s -> executeOperation.executeOperationsUserCreate(s));
//        processorMap.put(ConsoleOperationType.ACCOUNT_CREATE, createAccountProcessor);
//        processorMap.put(ConsoleOperationType.SHOW_ALL_USERS, showAllUsersProcessor);
        return processorMap;
    }
}

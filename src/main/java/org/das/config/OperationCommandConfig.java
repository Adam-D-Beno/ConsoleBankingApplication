package org.das.config;

import org.das.service.AccountService;
import org.das.service.ExecuteOperation;
import org.das.service.UserService;
import org.das.utils.ConsoleOperationType;
import org.das.validate.UserValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

@Configuration
public class OperationCommandConfig {

    @Bean
    public Map<ConsoleOperationType, Consumer<Scanner>> processorMap(ExecuteOperation executeOperation) {
        var processorMap = new EnumMap<ConsoleOperationType, Consumer<Scanner>>(ConsoleOperationType.class);
        processorMap.put(ConsoleOperationType.USER_CREATE, executeOperation::executeOperationsUserCreate);
        processorMap.put(ConsoleOperationType.ACCOUNT_CREATE, executeOperation::executeOperationsAccountCreate);
        processorMap.put(ConsoleOperationType.SHOW_ALL_USERS, s -> executeOperation.showAllUsers());
        processorMap.put(ConsoleOperationType.ACCOUNT_CLOSE, executeOperation::executeOperationsAccountClose);
        processorMap.put(ConsoleOperationType.ACCOUNT_WITHDRAW, executeOperation::executeOperationsAccountWithdraw);
        processorMap.put(ConsoleOperationType.ACCOUNT_DEPOSIT, executeOperation::executeOperationsAccountDeposit);
        processorMap.put(ConsoleOperationType.ACCOUNT_TRANSFER, executeOperation::executeOperationsAccountTransfer);
        return processorMap;
    }

    @Bean
    public ExecuteOperation executeOperation(UserValidation userValidation,
                                             UserService userService,
                                             AccountService accountService
    ) {
        return new ExecuteOperation(userValidation, userService, accountService);
    }
}

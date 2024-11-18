package org.das.config;

import org.das.service.oparations.OperationCommand;
import org.das.utils.ConsoleOperationType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Configuration
public class OperationCommandConfiguration {

     @Bean
     public Map<ConsoleOperationType, OperationCommand> commandMap(
             List<OperationCommand> commands
     ) {
          var commandMap = new EnumMap<ConsoleOperationType, OperationCommand>(ConsoleOperationType.class);
          commands.forEach(command -> commandMap.put(command.getOperationType(), command));
          return commandMap;
     }
}

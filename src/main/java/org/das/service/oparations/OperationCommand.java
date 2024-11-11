package org.das.service.oparations;

import org.das.utils.ConsoleOperationType;

public interface OperationCommand {
    void execute();
    ConsoleOperationType getOperationType();
}

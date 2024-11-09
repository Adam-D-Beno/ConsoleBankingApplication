package org.das.service;

import org.das.utils.ConsoleOperationType;

public interface OperationCommand {
    void execute();
    ConsoleOperationType getOperationType();
}

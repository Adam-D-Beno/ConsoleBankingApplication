package org.das.service;

import org.das.model.Account;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountService {
    Account create(UUID userId);
    Account close(UUID accountId);
    void deposit(UUID accountId, BigDecimal amount);
    void transfer(UUID senderId, UUID recipientId, BigDecimal amount);
    void withdraw(UUID accountId, BigDecimal amount);
}

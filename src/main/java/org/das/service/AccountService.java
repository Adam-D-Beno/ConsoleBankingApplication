package org.das.service;

import org.das.model.Account;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountService {
    Account accountCreate(UUID userId);
    Account accountClose(UUID accountId);
    void accountDeposit(UUID accountId, BigDecimal amount);
    void accountTransfer(UUID senderId, UUID recipientId, BigDecimal amount);
    void accountWithdraw(UUID accountId, BigDecimal amount);
}

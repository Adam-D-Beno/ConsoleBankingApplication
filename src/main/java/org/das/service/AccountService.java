package org.das.service;

import org.das.model.Account;

import java.util.UUID;

public interface AccountService {
    Account accountCreate(UUID userId);
    void accountClose(UUID accountId);
    void accountDeposit(UUID accountId, double amount);
    void accountTransfer(UUID senderId, UUID recipientId, double amount);
    void accountWithdraw(UUID accountId, double amount);
}

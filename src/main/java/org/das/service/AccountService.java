package org.das.service;

import org.das.model.Account;

import java.util.UUID;

public interface AccountService {
    Account accountCreate(UUID userId);
    boolean accountClose(UUID accountId);
    boolean accountDeposit(UUID accountId, double amount);
    boolean accountTransfer(UUID senderId, UUID recipientId, double amount);
    boolean accountWithdraw(UUID accountId, double amount);
}

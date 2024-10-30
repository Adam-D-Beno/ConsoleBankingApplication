package org.das.service;

import org.das.model.Account;

import java.util.UUID;

public class AccountServiceImpl implements AccountService {
    @Override
    public Account accountCreate(UUID userId) {
        return new Account(userId);
    }

    @Override
    public boolean accountClose(UUID accountId) {
        return false;
    }

    @Override
    public boolean accountDeposit(UUID accountId, double amount) {
        return false;
    }

    @Override
    public boolean accountTransfer(UUID senderId, UUID recipientId, double amount) {
        return false;
    }

    @Override
    public boolean accountWithdraw(UUID accountId, double amount) {
        return false;
    }
}

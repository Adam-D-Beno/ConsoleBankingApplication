package org.das.service;

import org.das.dao.UserDao;
import org.das.model.Account;
import org.das.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    private final UserDao users;

    @Autowired
    public AccountServiceImpl(UserDao users) {
        this.users = users;
    }


    @Override
    public Account accountCreate(UUID userId) {
        return new Account(userId);
    }

    @Override
    public boolean accountClose(UUID accountId) {
        return false;
    }

    @Override
    public void accountDeposit(UUID accountId, double amount) {
        Account account = findAccountById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not exist"));;
        account.increaseAmount(amount);

    }

    @Override
    public boolean accountTransfer(UUID senderId, UUID recipientId, double amount) {
        return false;
    }

    @Override
    public void accountWithdraw(UUID accountId, double amount) {
        Account account = findAccountById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not exist"));;
        account.decreaseAmount(amount);
    }


    private Optional<Account> findAccountById(UUID accountId) {

        for (User user : users.getUsers().values()) {
            Optional<Account> account = user.getAccountById(accountId);
            if (account.isPresent()) {
               return account;
            }
        }
        return Optional.empty();
    }
}

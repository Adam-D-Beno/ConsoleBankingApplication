package org.das.service;

import org.das.dao.UserDao;
import org.das.model.Account;
import org.das.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

//todo use validation in this class
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
    public void accountClose(UUID accountId) {
        users.getUsers().values().stream()
                .map(User::getAccounts)
                .forEach(accounts -> accounts.removeIf(acc -> acc.getAccountId().equals(accountId)));
    }

    @Override
    public void accountDeposit(UUID accountId, BigDecimal amount) {
        Account account = findAccountById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not exist"));
        account.increaseAmount(amount);

    }

    @Override
    public void accountTransfer(UUID senderId, UUID recipientId, BigDecimal amount) {
        Account sender = findAccountById(senderId)
                .orElseThrow(() -> new RuntimeException("Account senderId not exist"));

        Account recipient = findAccountById(recipientId)
                .orElseThrow(() -> new RuntimeException("Account recipientId not exist"));

        sender.decreaseAmount(amount);
        recipient.increaseAmount(amount);
    }

    @Override
    public void accountWithdraw(UUID accountId, BigDecimal amount) {
        Account account = findAccountById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not exist"));

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

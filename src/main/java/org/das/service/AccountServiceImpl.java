package org.das.service;

import org.das.dao.AccountDao;
import org.das.dao.UserDao;
import org.das.model.Account;
import org.das.utils.AccountProperties;
import org.das.validate.AccountValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    private final UserDao userDao;
    private final AccountDao accountDao;
    private final AccountValidation accountValidation;
    private final AccountProperties accountProperties;

    @Autowired
    public AccountServiceImpl(UserDao userDao, AccountDao accountDao,
                              AccountValidation accountValidation, AccountProperties accountProperties) {
        this.userDao = userDao;
        this.accountDao = accountDao;
        this.accountValidation = accountValidation;
        this.accountProperties = accountProperties;
    }

    @Override
    public Account create(UUID userId) {
        Account account = new Account(userId);
        setDefaultAmount(account);
        return accountDao.save(account);
    }

    private void setDefaultAmount(Account account) {
        if (isFirstAccount(account)) {
            account.setMoneyAmount(BigDecimal.valueOf(accountProperties.getDefaultAmount()));
        }
    }

    @Override
    public void close(UUID accountId) {
        Account account = accountDao.getAccount()
                .stream()
                .filter(findAccount -> findAccount.getAccountId().equals(accountId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such account: id=%s".formatted(accountId)));

    }

    @Override
    public void deposit(UUID accountId, BigDecimal amount) {
        accountValidation.negativeAmount(amount);
        Account account = accountDao.getAccount(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not exist id=%s".formatted(accountId)));
        account.increaseAmount(amount);
    }

    @Override
    public void withdraw(UUID accountId, BigDecimal amount) {
        accountValidation.negativeAmount(amount);
        Account account = accountDao.getAccount(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not exist id=%s".formatted(accountId)));
        accountValidation.negativeBalance(account, amount);
        account.decreaseAmount(amount);
    }

    @Override
    public void transfer(UUID senderId, UUID recipientId, BigDecimal amount) {
        accountValidation.negativeAmount(amount);
        Account fromAccount = accountDao.getAccount(senderId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id=%s".formatted(senderId)));
        accountValidation.negativeBalance(fromAccount, amount);
        Account toAccount = accountDao.getAccount(recipientId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id=%s".formatted(recipientId)));
        isOwnAccountTransfer(fromAccount, toAccount);
        //todo change
        amount = amount.multiply(BigDecimal.valueOf(accountProperties.getTransferComission()));
        fromAccount.decreaseAmount(amount);
        toAccount.increaseAmount(amount);
    }

    private boolean hasNoAccounts(Account account) {
               return userDao.getUsers().stream()
    .filter(user -> user.getUserId().equals(account.getUserId()))
    .anyMatch(user -> user.getAccounts().isEmpty());
    }

    private boolean isFirstAccount(Account account) {
        return hasNoAccounts(account);
    }

    private void isOwnAccountTransfer(Account fromAccount, Account toAccount) {
        if (fromAccount.getUserId().equals(toAccount.getUserId())) {
            throw new IllegalArgumentException("Account from id=%s and account to id=%s  transfer is same"
                    .formatted(fromAccount.getAccountId(), toAccount.getAccountId()));
        }
    }
}

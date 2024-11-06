package org.das.service;

import org.das.dao.AccountDao;
import org.das.dao.UserDao;
import org.das.model.Account;
import org.das.validate.AccountValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    private final UserDao userDao;
    private final AccountDao accountDao;
    private final AccountValidation accountValidation;
    @Value("${account.default-amount}")
    private String defaultAmount;
    @Value("${account.transfer-commission}")
    private String transferCommission;

    @Autowired
    public AccountServiceImpl(UserDao userDao, AccountDao accountDao, AccountValidation accountValidation) {
        this.userDao = userDao;
        this.accountDao = accountDao;
        this.accountValidation = accountValidation;
    }

    @Override
    public Account accountCreate(UUID userId) {
        Account account = new Account(userId);
        setDefaultAmount(account);
        accountDao.saveAccount(account);
        return account;
    }

    private void setDefaultAmount(Account account) {
        if (isFirstAccount(account)) {
            account.decreaseAmount(BigDecimal.valueOf(Double.parseDouble(defaultAmount)));
        }
    }

    @Override
    public void accountClose(UUID accountId) {
        accountValidation.accountAlreadyExist(accountId);
        Account account = accountDao.getAccounts().get(accountId);
        if (hasNoAccounts(account)) {
            throw new RuntimeException("Account: " + account + " cant delete, because user have only one account");
        }
        accountDao.getAccounts().remove(accountId);
    }

    @Override
    public void accountDeposit(UUID accountId, BigDecimal amount) {
        accountValidation.negativeAmount(amount);
        accountValidation.accountAlreadyExist(accountId);
        Account account = accountDao.getAccounts(accountId)
                .orElseThrow(() -> new RuntimeException("Account not exist"));
        account.increaseAmount(amount);
    }

    @Override
    public void accountWithdraw(UUID accountId, BigDecimal amount) {
        accountValidation.negativeAmount(amount);
        accountValidation.accountAlreadyExist(accountId);
        Account account = accountDao.getAccounts(accountId)
                .orElseThrow(() -> new RuntimeException("Account not exist"));
        accountValidation.negativeBalance(account, amount);
        account.decreaseAmount(amount);
    }

    @Override
    public void accountTransfer(UUID senderId, UUID recipientId, BigDecimal amount) {
        accountValidation.negativeAmount(amount);
        accountValidation.accountAlreadyExist(senderId);
        accountValidation.accountAlreadyExist(recipientId);
        Account fromAccount = accountDao.getAccounts(senderId)
                .orElseThrow(() -> new RuntimeException("Account senderId not exist"));
        accountValidation.negativeBalance(fromAccount, amount);
        Account toAccount = accountDao.getAccounts(recipientId)
                .orElseThrow(() -> new RuntimeException("Account recipientId not exist"));
        if (!isOwnAccountTransfer(fromAccount, toAccount)) {
            amount = amount.multiply(BigDecimal.valueOf(Double.parseDouble(transferCommission)));
        }
        fromAccount.decreaseAmount(amount);
        toAccount.increaseAmount(amount);
    }

    private boolean hasNoAccounts(Account account) {
               return userDao.getUsers().values().stream()
    .filter(user -> user.getUserId().equals(account.getUserId()))
    .anyMatch(user -> user.getAccounts().isEmpty());
    }

    private boolean isFirstAccount(Account account) {
        return hasNoAccounts(account);
    }

    private boolean isOwnAccountTransfer(Account fromAccount, Account toAccount) {
        return fromAccount.getUserId().equals(toAccount.getUserId());
    }
}

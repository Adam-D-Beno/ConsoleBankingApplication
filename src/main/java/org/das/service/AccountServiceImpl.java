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
        return accountDao.save(account);
    }

    private void setDefaultAmount(Account account) {
        if (isFirstAccount(account)) {
            account.setMoneyAmount(BigDecimal.valueOf(Double.parseDouble(defaultAmount)));
        }
    }

    @Override
    public void accountClose(UUID accountId) {
         Account account = accountDao.getAccounts()
                .stream()
                .filter(findAccount -> findAccount.getAccountId().equals(accountId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No such account: id=%s".formatted(accountId)));

        if (hasNoAccounts(account)) {
            throw new IllegalArgumentException(("Account with id=%s cant delete, " +
                    "because user have only one account").formatted(accountId));
        }
        accountDao.remove(accountId);
    }

    @Override
    public void accountDeposit(UUID accountId, BigDecimal amount) {
        accountValidation.negativeAmount(amount);
        Account account = accountDao.getAccounts(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not exist id=%s".formatted(accountId)));
        account.increaseAmount(amount);
    }

    @Override
    public void accountWithdraw(UUID accountId, BigDecimal amount) {
        accountValidation.negativeAmount(amount);
        Account account = accountDao.getAccounts(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not exist id=%s".formatted(accountId)));
        accountValidation.negativeBalance(account, amount);
        account.decreaseAmount(amount);
    }

    @Override
    public void accountTransfer(UUID senderId, UUID recipientId, BigDecimal amount) {
        accountValidation.negativeAmount(amount);
        Account fromAccount = accountDao.getAccounts(senderId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id=%s".formatted(senderId)));
        accountValidation.negativeBalance(fromAccount, amount);
        Account toAccount = accountDao.getAccounts(recipientId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id=%s".formatted(recipientId)));
        isOwnAccountTransfer(fromAccount, toAccount);
        //todo change
        amount = amount.multiply(BigDecimal.valueOf(Double.parseDouble(transferCommission)));
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


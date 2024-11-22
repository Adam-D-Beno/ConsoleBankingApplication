package org.das.service;

import org.das.dao.AccountDao;
import org.das.dao.UserDao;
import org.das.model.Account;
import org.das.utils.AccountProperties;
import org.das.validate.AccountValidation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class AccountServiceImpl implements AccountService {
    private final UserDao userDao;
    private final AccountDao accountDao;
    private final AccountValidation accountValidation;
   private final AccountProperties accountProperties;

    public AccountServiceImpl(UserDao userDao, AccountDao accountDao,
                              AccountValidation accountValidation, AccountProperties accountProperties) {
        this.userDao = userDao;
        this.accountDao = accountDao;
        this.accountValidation = accountValidation;
        this.accountProperties = accountProperties;
    }

    @Override
    public Account accountCreate(UUID userId) {
        Account account = new Account(getRandomId(), userId);
        setDefaultAmount(account);
        return accountDao.save(account);
    }

    private void setDefaultAmount(Account account) {
        if (isFirstAccount(account)) {
            account.setMoneyAmount(BigDecimal.valueOf(accountProperties.getDefaultAmount()));
        }
    }

    @Override
    public Account accountClose(UUID accountId) {
        Account account = accountDao.getAccount(accountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id=%s".formatted(accountId)));
        if (hasNoAccounts(account)) {
            throw new IllegalArgumentException(("Account with id=%s cant delete, " +
                    "because user have only one account").formatted(accountId));
        }
        accountDao.remove(accountId);
        return account;
    }

    @Override
    public void accountDeposit(UUID accountId, BigDecimal amount) {
        accountValidation.negativeAmount(amount);
        Account account = accountDao.getAccount(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not exist id=%s".formatted(accountId)));
        account.increaseAmount(amount);
    }

    @Override
    public void accountWithdraw(UUID accountId, BigDecimal amount) {
        accountValidation.negativeAmount(amount);
        Account account = accountDao.getAccount(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not exist id=%s".formatted(accountId)));
        accountValidation.negativeBalance(account, amount);
        account.decreaseAmount(amount);
    }

    @Override
    public void accountTransfer(UUID senderId, UUID recipientId, BigDecimal amount) {
        accountValidation.negativeAmount(amount);
        Account fromAccount = accountDao.getAccount(senderId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id=%s".formatted(senderId)));
        accountValidation.negativeBalance(fromAccount, amount);
        Account toAccount = accountDao.getAccount(recipientId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id=%s".formatted(recipientId)));
        ownAccountTransfer(fromAccount, toAccount);
        BigDecimal amountAfterCommission = calculateAmountAfterCommission(amount);
        fromAccount.decreaseAmount(amount);
        toAccount.increaseAmount(amountAfterCommission);
    }

    private BigDecimal calculateAmountAfterCommission(BigDecimal amount) {
        BigDecimal commission = BigDecimal.valueOf(accountProperties.getTransferCommission());
        BigDecimal multiplier = BigDecimal.ONE.subtract(commission);
        return amount.multiply(multiplier).setScale(0, RoundingMode.HALF_UP);
    }

    private boolean hasNoAccounts(Account account) {
               return userDao.getUsers().stream()
    .filter(user -> user.getUserId().equals(account.getUserId()))
    .anyMatch(user -> user.getAccounts().isEmpty());
    }

    private boolean isFirstAccount(Account account) {
        return hasNoAccounts(account);
    }

    private void ownAccountTransfer(Account fromAccount, Account toAccount) {
        if (fromAccount.getAccountId().equals(toAccount.getAccountId())) {
            throw new IllegalArgumentException("Account from id=%s and account to id=%s  transfer is same"
                    .formatted(fromAccount.getAccountId(), toAccount.getAccountId()));
        }
    }

    private UUID getRandomId() {
        return UUID.randomUUID();
    }

    private Account getAccount(UUID accountId) {
        return accountDao.getAccount(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not exist id=%s".formatted(accountId)));

    }
}


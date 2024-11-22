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
        if (isFirstAccount(account.getAccountId())) {
            account.setMoneyAmount(BigDecimal.valueOf(accountProperties.getDefaultAmount()));
        }
        return accountDao.save(account);
    }

    @Override
    public Account accountClose(UUID accountId) {
        Account account = getAccount(accountId);
        if (isOnlyAccount(account.getUserId())) {
            throw new IllegalArgumentException(("Account with id=%s cant delete, " +
                    "because user have only one account").formatted(accountId));
        }
        accountDao.remove(accountId);
        return account;
    }

    @Override
    public void accountDeposit(UUID accountId, BigDecimal amount) {
        accountValidation.negativeAmount(amount);
        Account account = getAccount(accountId);
        account.increaseAmount(amount);
    }

    @Override
    public void accountWithdraw(UUID accountId, BigDecimal amount) {
        accountValidation.negativeAmount(amount);
        Account account = getAccount(accountId);
        accountValidation.negativeBalance(account, amount);
        account.decreaseAmount(amount);
    }

    @Override
    public void accountTransfer(UUID senderId, UUID recipientId, BigDecimal amount) {
        accountValidation.negativeAmount(amount);
        Account fromAccount = getAccount(senderId);
        accountValidation.negativeBalance(fromAccount, amount);
        Account toAccount = getAccount(recipientId);
        accountValidation.validateDifferentAccounts(fromAccount, toAccount);
        BigDecimal amountAfterCommission = calculateAmountAfterCommission(amount);
        fromAccount.decreaseAmount(amount);
        toAccount.increaseAmount(amountAfterCommission);
    }

    private BigDecimal calculateAmountAfterCommission(BigDecimal amount) {
        BigDecimal commission = BigDecimal.valueOf(accountProperties.getTransferCommission());
        return amount.multiply(BigDecimal.ONE.subtract(commission)).setScale(2, RoundingMode.HALF_UP);
    }

    private boolean isOnlyAccount(UUID userId) {
        return userDao.getUser(userId)
                .map(user -> user.getAccounts().size() <= 1)
                .orElse(true);
    }

    private boolean isFirstAccount(UUID userId) {
        return isOnlyAccount(userId);
    }

    private UUID getRandomId() {
        return UUID.randomUUID();
    }

    private Account getAccount(UUID accountId) {
        return accountDao.getAccount(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not exist id=%s".formatted(accountId)));

    }
}


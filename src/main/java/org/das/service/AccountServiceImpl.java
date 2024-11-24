package org.das.service;

import org.das.dao.AccountDao;
import org.das.dao.UserDao;
import org.das.model.Account;
import org.das.utils.AccountProperties;
import org.das.validate.AccountValidation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao;
    private final AccountValidation accountValidation;
   private final AccountProperties accountProperties;

    public AccountServiceImpl(AccountDao accountDao,
                              AccountValidation accountValidation, AccountProperties accountProperties) {
        this.accountDao = accountDao;
        this.accountValidation = accountValidation;
        this.accountProperties = accountProperties;
    }

    @Override
    public Account accountCreate(UUID userId) {
        Account newAccount = new Account(getRandomId(), userId);
        accountDao.save(newAccount);
        if (isFirstAccount(newAccount.getUserId())) {
            newAccount.setMoneyAmount(BigDecimal.valueOf(accountProperties.getDefaultAmount()));
        }
        return  newAccount;
    }

    @Override
    public Account accountClose(UUID accountId) {
        Account account = getAccount(accountId);
        if (isOnlyAccount(account.getUserId())) {
            throw new IllegalArgumentException(("Account with id=%s cant delete, " +
                    "because user have only one account").formatted(accountId));
        }
        if (account.getMoneyAmount().doubleValue() >= 1) {
            var accountNextId = getAccountNextId(account);
            accountTransfer(account.getAccountId(), accountNextId, account.getMoneyAmount());
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
        accountValidation.isSameAccount(fromAccount, toAccount);
        if (isAccountOneUser(fromAccount, toAccount)) {
            fromAccount.decreaseAmount(amount);
            toAccount.increaseAmount(amount);
            return;
        }
        BigDecimal amountAfterCommission = calculateAmountAfterCommission(amount);
        fromAccount.decreaseAmount(amount);
        toAccount.increaseAmount(amountAfterCommission);
    }

    private UUID getAccountNextId(Account account) {
        return getAllUserAccounts(account.getUserId()).stream()
                .map(Account::getAccountId)
                .filter(id -> !id.equals(account.getAccountId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such account id=%s to transfer for user id=%s"
                        .formatted(account.getAccountId(), account.getUserId())));
    }

    private boolean isAccountOneUser(Account fromAccount, Account toAccount) {
        return fromAccount.getUserId().equals(toAccount.getUserId());
    }

    private BigDecimal calculateAmountAfterCommission(BigDecimal amount) {
        BigDecimal commission = BigDecimal.valueOf(accountProperties.getTransferCommission());
        return amount.multiply(BigDecimal.ONE.subtract(commission)).setScale(2, RoundingMode.HALF_UP);
    }

    private boolean isOnlyAccount(UUID userId) {
        return getAllUserAccounts(userId).size() == 1;
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

    public List<Account> getAllUserAccounts(UUID userId) {
        return   accountDao.getAllAccounts().stream()
                .filter(account -> account.getUserId().equals(userId))
                .toList();
    }
}


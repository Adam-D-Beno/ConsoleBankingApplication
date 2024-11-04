package org.das.service;

import org.das.dao.AccountDao;
import org.das.dao.UserDao;
import org.das.model.Account;
import org.das.validate.AccountValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    private final UserDao userDao;
    private final AccountDao accountDao;
    private final AccountValidation accountValidation;

    @Autowired
    public AccountServiceImpl(UserDao userDao, AccountDao accountDao, AccountValidation accountValidation) {
        this.userDao = userDao;
        this.accountDao = accountDao;
        this.accountValidation = accountValidation;
    }

    @Override
    public Account accountCreate(UUID userId) {
        Account account = new Account(userId);
        accountDao.saveAccount(account);
        return account;
    }

    @Override
    public void accountClose(UUID accountId) {
        accountValidation.accountAlreadyExist(accountId);
        Account account = accountDao.getAccounts().get(accountId);
        if (countSizeAccountByUser(account)) {
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
        Account sender = accountDao.getAccounts(senderId)
                .orElseThrow(() -> new RuntimeException("Account senderId not exist"));
        accountValidation.negativeBalance(sender, amount);
        Account recipient = accountDao.getAccounts(recipientId)
                .orElseThrow(() -> new RuntimeException("Account recipientId not exist"));
        sender.decreaseAmount(amount);
        recipient.increaseAmount(amount);
    }

    private boolean countSizeAccountByUser(Account account) {
        return userDao.getUsers().entrySet().stream().
                map(Map.Entry::getValue)
                .filter(user -> user.getUserId().equals(account.getUserId()))
                .map(findUser -> findUser.getAccounts().size())
                .anyMatch(accSize -> accSize == 1);
    }
}

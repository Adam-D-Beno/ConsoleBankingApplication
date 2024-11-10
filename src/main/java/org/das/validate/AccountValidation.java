package org.das.validate;

import org.das.dao.AccountDao;
import org.das.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class AccountValidation {
    private final AccountDao accountDao;

    @Autowired
    public AccountValidation(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void negativeAmount(BigDecimal amount) {
        if (amount.signum() == -1) {
            throw new IllegalArgumentException("Amount is negative: " + amount);
        }
    }

    public void negativeBalance(Account account, BigDecimal amount) {
        BigDecimal balance = account.getMoneyAmount();
        if (balance.subtract(amount).signum() == -1) {
            throw new IllegalArgumentException(("No such money to transfer from account with id=%s, money amount=%s," +
                    "attempted withdraw=%s".formatted(account.getAccountId(), account.getMoneyAmount(), amount)));
        }
    }

    public void accountExist(UUID accountId) {
        if (!accountDao.accountExist(accountId)) {
            throw new IllegalArgumentException("User with account id=%s already exist".formatted(accountId));
        }
    }
}

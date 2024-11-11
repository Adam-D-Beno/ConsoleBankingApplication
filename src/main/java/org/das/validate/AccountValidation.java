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
            throw new RuntimeException("Amount is negative: " + amount);
        }
    }

    public void negativeBalance(Account account, BigDecimal amount) {
        BigDecimal balance = account.getMoneyAmount();
        if (balance.subtract(amount).signum() == -1) {
            throw new RuntimeException("Insufficient balance: " + balance
                    + " account id " + account.getAccountId());
        }
    }

    public void accountAlreadyExist(UUID id) {
        if (accountDao.AccountExist(id)) {
            throw new RuntimeException("User with account id: " + id + " already exist");
        }
    }
}

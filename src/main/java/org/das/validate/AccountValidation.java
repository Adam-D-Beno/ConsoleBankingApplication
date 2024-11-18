package org.das.validate;

import org.das.model.Account;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

public class AccountValidation {

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
}

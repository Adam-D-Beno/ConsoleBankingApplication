package org.das.validate;

import org.das.model.Account;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class AccountValidation {

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
}

package org.das.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Account {
    private final UUID accountId;
    private final UUID userId;

    public void setMoneyAmount(BigDecimal moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    private BigDecimal moneyAmount;

    public Account(UUID accountId, UUID userId) {
        this.accountId = accountId;
        this.userId = userId;
        this.moneyAmount = BigDecimal.ZERO;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public UUID getUserId() {
        return userId;
    }

    public BigDecimal getMoneyAmount() {
        return moneyAmount;
    }

    public void increaseAmount(BigDecimal amount) {
        this.moneyAmount = moneyAmount.add(amount);
    }
    public void decreaseAmount(BigDecimal amount) {
        this.moneyAmount = moneyAmount.subtract(amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;
        return accountId.equals(account.accountId) && userId.equals(account.userId);
    }

    @Override
    public int hashCode() {
        int result = accountId.hashCode();
        result = 31 * result + userId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", userId=" + userId +
                ", moneyAmount=" + moneyAmount +
                '}';
    }
}

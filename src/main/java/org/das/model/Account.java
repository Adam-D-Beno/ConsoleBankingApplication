package org.das.model;

import java.util.UUID;

public class Account {
    private UUID accountId;
    private UUID userId;
    private double moneyAmount;

    public Account(UUID userId) {
        this.userId = userId;
        this.accountId = UUID.randomUUID();
    }

    public UUID getAccountId() {
        return accountId;
    }

    public UUID getUserId() {
        return userId;
    }

    public double getMoneyAmount() {
        return moneyAmount;
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

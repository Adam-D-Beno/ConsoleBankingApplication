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
}

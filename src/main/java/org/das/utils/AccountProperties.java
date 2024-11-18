package org.das.utils;

import org.springframework.beans.factory.annotation.Value;

public class AccountProperties {
    private final int defaultAmount;
    private final double transferCommission;

    public AccountProperties( int defaultAmount,
                              double transferCommission) {
        this.defaultAmount = defaultAmount;
        this.transferCommission = transferCommission;
    }

    public int getDefaultAmount() {
        return defaultAmount;
    }

    public double getTransferCommission() {
        return transferCommission;
    }
}

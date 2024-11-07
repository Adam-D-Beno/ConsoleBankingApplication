package org.das.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AccountProperties {
    private final int defaultAmount;
    private final double transferComission;

    public AccountProperties(@Value("${account.default-amount}") int defaultAmount,
                             @Value("${account.transfer-commission}") double transferComission) {
        this.defaultAmount = defaultAmount;
        this.transferComission = transferComission;
    }

    public int getDefaultAmount() {
        return defaultAmount;
    }

    public double getTransferComission() {
        return transferComission;
    }

}

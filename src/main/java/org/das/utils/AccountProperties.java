package org.das.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AccountProperties {
    private final String defaultAmount;
    private final String transferCommission;

    public AccountProperties(@Value("${account.default-amount}") String defaultAmount,
                             @Value("${account.transfer-commission}") String transferCommission) {
        this.defaultAmount = defaultAmount;
        this.transferCommission = transferCommission;
    }

    public String getDefaultAmount() {
        return defaultAmount;
    }

    public String getTransferCommission() {
        return transferCommission;
    }
}

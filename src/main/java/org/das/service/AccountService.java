package org.das.service;

import org.das.model.Account;

public interface AccountService {
    Account accountCreate();
    boolean accountClose();
    boolean accountDeposit();
    boolean accountTransfer();
    boolean accountWithdraw();
}

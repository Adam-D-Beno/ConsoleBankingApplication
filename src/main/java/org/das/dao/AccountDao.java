package org.das.dao;

import org.das.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class AccountDao {
    private final Map<UUID, Account> accounts;

    @Autowired
    public AccountDao(Map<UUID, Account> accounts) {
        this.accounts = accounts;
    }

    public Account save(Account account) {
        accounts.put(account.getAccountId(), account);
        return account;
    }

    public Collection<Account> getAllAccounts() {
       return accounts.values();
    }

    public Optional<Account> getAccount(UUID id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public void remove(UUID id) {
        accounts.remove(id);
    }
}

package org.das.dao;

import org.das.model.Account;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class AccountDao {
    private final Map<UUID, Account> accounts;

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

    public void remove(UUID accountId) {
        accounts.remove(accountId);
    }
}

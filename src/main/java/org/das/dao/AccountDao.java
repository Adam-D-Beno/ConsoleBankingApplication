package org.das.dao;

import org.das.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class AccountDao {
    private final Map<UUID, Account> accounts;

    @Autowired
    public AccountDao(Map<UUID, Account> accounts) {
        this.accounts = accounts;
    }

    public void save(Account account) {
        accounts.put(account.getAccountId(), account);
    }

    public Collection<Account> getAccount() {
        return accounts.values();
    }

    public Optional<Account> getAccount(UUID id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public boolean accountExist(UUID id) {
        return accounts.containsKey(id);
    }

    public void remove(UUID id) {
        accounts.remove(id);
    }
}

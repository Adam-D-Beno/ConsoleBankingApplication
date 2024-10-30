package org.das.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class User {
    private UUID userId;
    private String login;
    private List<Account> accounts;


    public User() {
        this.userId = UUID.randomUUID();
        this.accounts = new ArrayList<>();
    }

    public UUID getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Account account) {
        this.accounts.add(account);
    }
}

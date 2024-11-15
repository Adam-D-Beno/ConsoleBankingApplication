package org.das.model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class User {
    private final UUID userId;
    private final String login;
    private final List<Account> accounts;

    public User(UUID userId, String login, List<Account> accounts) {
        this.userId = userId;
        this.accounts = accounts;
        this.login = login;
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

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public Optional<Account> getAccountById(UUID accountId) {
        return accounts.stream().filter(account -> account.getAccountId().equals(accountId)).findFirst();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return userId.equals(user.userId) && login.equals(user.login);
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + login.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", accounts=" + accounts +
                '}';
    }
}

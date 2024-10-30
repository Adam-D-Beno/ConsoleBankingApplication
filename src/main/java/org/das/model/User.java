package org.das.model;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class User {
    private UUID uuid;
    private String login;
    private List<Account> accounts;


}

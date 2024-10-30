package org.das.model;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Account {
    private UUID uuid;
    private UUID userUuid;
    private double moneyAmount;

}

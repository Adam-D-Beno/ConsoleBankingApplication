package org.das.service;

import org.das.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
   User userCreate(String login);
   void showAllUsers();
   Optional<User> getUserByLogin(UUID id);

}

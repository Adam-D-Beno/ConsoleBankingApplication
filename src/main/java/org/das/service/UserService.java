package org.das.service;

import org.das.model.User;

public interface UserService {
   User userCreate(String login);
   void showAllUsers();

}

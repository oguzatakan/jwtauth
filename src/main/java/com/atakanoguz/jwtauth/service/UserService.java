package com.atakanoguz.jwtauth.service;

import com.atakanoguz.jwtauth.entity.User;

import java.security.Principal;
import java.util.List;

public interface UserService {

    User saveUser(User user);

    User getUser(String username, Principal principal);

    void updateUser(User user, Principal principal);

    void deleteUser(String username, Principal principal);

    List<User> getUsers();

}

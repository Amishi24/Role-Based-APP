package com.amishi.rolebasedapp.service;

import com.amishi.rolebasedapp.model.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    List<User> getAllUsers();
    User getUserByUsername(String username);
    User login(String email, String password);
}
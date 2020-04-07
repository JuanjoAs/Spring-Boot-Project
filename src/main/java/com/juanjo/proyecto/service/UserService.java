package com.juanjo.proyecto.service;

import com.juanjo.proyecto.model.User;

public interface UserService {
  
 public User findUserByEmail(String email);
 
 public void saveUser(User user);
}
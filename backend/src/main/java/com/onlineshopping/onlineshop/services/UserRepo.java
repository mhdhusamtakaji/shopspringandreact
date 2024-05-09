package com.onlineshopping.onlineshop.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineshopping.onlineshop.models.UserModel;

public interface UserRepo extends JpaRepository<UserModel, Integer>{
    UserModel findByUsername(String username);
    UserModel findByToken(String token);
}

package com.onlineshopping.onlineshop.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineshopping.onlineshop.models.OrderModel;

public interface OrderRepo extends JpaRepository<OrderModel, Integer>{
    
}

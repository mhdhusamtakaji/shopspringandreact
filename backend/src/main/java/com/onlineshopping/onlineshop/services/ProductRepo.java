package com.onlineshopping.onlineshop.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineshopping.onlineshop.models.ProductModel;

public interface ProductRepo extends JpaRepository<ProductModel, Integer>{
    ProductModel findByname(String name);
}

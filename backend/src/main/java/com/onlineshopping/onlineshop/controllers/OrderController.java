package com.onlineshopping.onlineshop.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.onlineshopping.onlineshop.CustomResponse;
import com.onlineshopping.onlineshop.models.OrderModel;
import com.onlineshopping.onlineshop.models.UserModel;
import com.onlineshopping.onlineshop.services.OrderRepo;
import com.onlineshopping.onlineshop.services.UserRepo;



@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	OrderRepo orderRepo;

	@Autowired
	UserRepo userRepo;
	
	@GetMapping({"", "/"})
	public ResponseEntity<?> index() {
		List<OrderModel> orders = orderRepo.findAll();
		if(!orders.isEmpty()) {
			return ResponseEntity.ok(orders);			
		} else {
			CustomResponse error = new CustomResponse("No orders found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getOrder(@PathVariable("id") int id) {
		final OrderModel order = orderRepo.findById(id).orElse(null);
		if (order == null) {
			CustomResponse error = new CustomResponse("Order not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		}
		else {
			return ResponseEntity.ok(order);
		}
	}
	
	@PostMapping({"", "/"})
	public ResponseEntity<?> createOrder(@RequestBody OrderModel order, @RequestHeader("Authorization") String authorizationHeader) {
		 // Check if Authorization header is present
		 if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }
        // Extract token from Authorization header
        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix

        // Retrieve user from database using token
        UserModel authenticatedUser = userRepo.findByToken(token);
        if (authenticatedUser != null) {
            order.setUser(authenticatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

		orderRepo.save(order);
		return ResponseEntity.status(HttpStatus.CREATED).body(order);
	}

	@GetMapping("/users/{user_id}")
	public ResponseEntity<?> fetchUserOrders(@PathVariable("user_id") int user_id) {
		UserModel user = userRepo.findById(user_id).orElse(null);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
		List<OrderModel> orders = user.getOrders();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(orders);
	}
	
}
package com.onlineshopping.onlineshop.controllers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.onlineshopping.onlineshop.CustomResponse;
import com.onlineshopping.onlineshop.models.ProductModel;
import com.onlineshopping.onlineshop.services.ProductRepo;

@RestController
@RequestMapping("/products")
public class ProductController {
	@Autowired
	ProductRepo productRepo;
		
	@GetMapping({"", "/"})
	public List<ProductModel> index() {
		final List<ProductModel> products = productRepo.findAll();
		return products;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getProduct(@PathVariable("id") int id) {
	    ProductModel product = productRepo.findById(id).orElse(null);
	    if (product != null) {
	        return ResponseEntity.ok(product);
	    } else {
	    	CustomResponse errorResponse = new CustomResponse("Product not found");
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	    }
	}
	
	@PostMapping({"", "/"})
	public ResponseEntity<?> addProduct(@RequestBody ProductModel product) {
		ProductModel savedProduct = productRepo.save(product);
		  return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);	
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable("id") int id, @RequestBody ProductModel productDetails) {
		final ProductModel product = productRepo.findById(id).orElse(null);
		if (product == null) {
			CustomResponse error = new CustomResponse("Product not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		} else {
			 if (productDetails.getName() != null) {
		            product.setName(productDetails.getName());
		        }
		        if (productDetails.getPrice() != null) {
		            product.setPrice(productDetails.getPrice());
		        }
		        if (productDetails.getDescription() != null) {
		            product.setDescription(productDetails.getDescription());
		        }
			
			ProductModel savedProduct = productRepo.save(product);
			return ResponseEntity.ok(savedProduct);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") int id) {
		ProductModel product = productRepo.findById(id).orElse(null);
		
		if (product == null) {
			CustomResponse error = new CustomResponse("Product not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		}
		else {
			productRepo.delete(product);
			return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse("Deleted Successfully"));
		}
	}
}
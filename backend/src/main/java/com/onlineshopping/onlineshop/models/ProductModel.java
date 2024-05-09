package com.onlineshopping.onlineshop.models;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "products")
public class ProductModel {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@NotNull
	@NotEmpty
    @Column(unique = true)
	private String name;
	@NotNull
	private Double price;
	private String description;
	
	 @ManyToMany(mappedBy = "products")
	    private List<OrderModel> orders;
	
	// Default Constructor
	public ProductModel() {}
	
	
	public ProductModel(@NotEmpty String name, @NotNull Double price, String description) {
		super();
		this.name = name;
		this.price = price;
		this.description = description;
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ProductModel [id=" + id + ", name=" + name + ", price=" + price + ", description=" + description + "]";
	}
	
	
	
}

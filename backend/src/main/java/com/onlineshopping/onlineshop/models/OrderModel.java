package com.onlineshopping.onlineshop.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="orders")
public class OrderModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	 @ManyToMany(fetch = FetchType.EAGER)
	    @JoinTable(
	        name = "order_product",
	        joinColumns = @JoinColumn(name = "order_id"),
	        inverseJoinColumns = @JoinColumn(name = "product_id")
	    )
	    private List<ProductModel> products;

	 @Column(name = "total")
	private Double total;
	 @Column(name = "date")
	private LocalDate date;

	@ManyToOne(fetch = FetchType.EAGER) 
    @JoinColumn(name = "user_id", referencedColumnName = "id") 
    private UserModel user;
	 
	 
	public OrderModel() {}
	
	public OrderModel(@NotNull List<ProductModel> products, @NotNull Double total, LocalDate date) {
		super();
		this.products = products;
		this.total = total;
		this.date = date;
	}
	
	public List<ProductModel> getProducts() {
		return products;
	}
	public void setProducts(List<ProductModel> products) {
		this.products = products;
	}
	public Double getTotal() {
		return total;
	}
	@PrePersist
	public void setDefaults() {
	    double calculatedTotal = this.getProducts().stream()
	                            .mapToDouble(ProductModel::getPrice)
	                            .sum();
	    this.total = calculatedTotal;
	    this.date = LocalDate.now();
	}

	public LocalDate getDate() {
		return date;
	}
	
	public int getId() {
		return id;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}

	 
	 
}
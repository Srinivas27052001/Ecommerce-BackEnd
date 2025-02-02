package com.ecommerce.myshop.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	
	@OneToMany(mappedBy="cart",cascade=CascadeType.ALL,orphanRemoval =true)
	@Column(name="cart_items")
	private Set<CartItem> cartItem=new HashSet<>();
	
	@Column(name="total_price")
	private double totalPrice;
	
	@Column(name="total_item")
	private int totalItem;
	
	private int totalDiscountPrice;
	
	private int discount;
	
	




	public Cart(Long id, User user, Set<CartItem> cartItem, double totalPrice, int totalItem, int totalDiscountPrice,
			int discount) {
		super();
		this.id = id;
		this.user = user;
		this.cartItem = cartItem;
		this.totalPrice = totalPrice;
		this.totalItem = totalItem;
		this.totalDiscountPrice = totalDiscountPrice;
		this.discount = discount;
	}



	public Cart() {
		// TODO Auto-generated constructor stub
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public Set<CartItem> getCartItem() {
		return cartItem;
	}



	public void setCartItem(Set<CartItem> cartItem) {
		this.cartItem = cartItem;
	}



	public double getTotalPrice() {
		return totalPrice;
	}



	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}



	public int getTotalItem() {
		return totalItem;
	}



	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}



	public int getTotalDiscountPrice() {
		return totalDiscountPrice;
	}



	public void setTotalDiscountPrice(int totalDiscountPrice) {
		this.totalDiscountPrice = totalDiscountPrice;
	}



	public int getDiscount() {
		return discount;
	}



	public void setDiscount(int discount) {
		this.discount = discount;
	}


	
}
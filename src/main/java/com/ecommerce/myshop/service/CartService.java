package com.ecommerce.myshop.service;

import com.ecommerce.myshop.exception.ProductException;
import com.ecommerce.myshop.model.Cart;
import com.ecommerce.myshop.model.User;
import com.ecommerce.myshop.request.AddItemRequest;

public interface CartService {
	
	public Cart creatCart(User user);
	
	public String addCartItem(Long userId,AddItemRequest req) throws ProductException;
	
	public  Cart findUserCart(Long userId);
	

}

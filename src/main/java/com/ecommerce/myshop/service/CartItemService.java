package com.ecommerce.myshop.service;

import com.ecommerce.myshop.exception.CartItemException;
import com.ecommerce.myshop.exception.UserException;
import com.ecommerce.myshop.model.Cart;
import com.ecommerce.myshop.model.CartItem;
import com.ecommerce.myshop.model.Product;

public interface CartItemService {

	
	public CartItem  creatCartItem(CartItem cartItem);
	
	public CartItem updateCartItem(Long userId,Long id,CartItem cartItem) throws CartItemException,UserException;
 
	public CartItem isCartItemExist(Cart cart,Product product,String size,Long userId);
	
	public void removeCartItem(Long userId,Long cartItemId) throws CartItemException,UserException;
	
	public CartItem findCartitemById(Long cartItemid) throws CartItemException;
	

}

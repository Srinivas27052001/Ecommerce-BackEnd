package com.ecommerce.myshop.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.myshop.exception.CartItemException;
import com.ecommerce.myshop.exception.UserException;
import com.ecommerce.myshop.model.Cart;
import com.ecommerce.myshop.model.CartItem;
import com.ecommerce.myshop.model.Product;
import com.ecommerce.myshop.model.User;
import com.ecommerce.myshop.repository.CartItemRepository;
import com.ecommerce.myshop.repository.CartRepository;

@Service
public class CartItemServiceImplementation implements CartItemService {

	
	private CartItemRepository cartItemRepository;
	private UserService userService;
	private CartRepository cartRepository;
	
	
	
	public CartItemServiceImplementation(CartItemRepository cartItemRepository, UserService userService,
			CartRepository cartRepository) {
		super();
		this.cartItemRepository = cartItemRepository;
		this.userService = userService;
		this.cartRepository = cartRepository;
	}

	@Override
	public CartItem creatCartItem(CartItem cartItem) {
		
		cartItem.setQuantity(1);
		cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
		cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());
		
		CartItem createdItem=cartItemRepository.save(cartItem);
		
		return createdItem;
	}

	@Override
	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
		
		CartItem item=findCartitemById(id);
		User user=userService.findUserById(item.getUserId());
//		2*100=200
		if(user.getId().equals(userId))
		{
			item.setQuantity(cartItem.getQuantity());
			item.setPrice(item.getQuantity()*item.getProduct().getPrice());
			item.setDiscountedPrice(item.getProduct().getDiscountedPrice()*(item.getQuantity()));
			
			
		}
		return cartItemRepository.save(item);
	}

	@Override
	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
		
		CartItem cartItem=cartItemRepository.isCartItemExist(cart, product, size, userId);
		
		return cartItem;
	}

	@Override
	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
		CartItem cartItem=findCartitemById(cartItemId);
		
		User user=userService.findUserById(cartItem.getUserId());
		
	     User reqUser=userService.findUserById(userId);
	     
	     if(user.getId().equals(reqUser.getId())){
	    	 cartItemRepository.deleteById(cartItemId);
	     }
	     else {
	    	 throw new UserException("you cant remove another users item");
	    	 
	     }
	     
		
	}

	@Override
	public CartItem findCartitemById(Long cartItemid) throws CartItemException {
        Optional<CartItem> opt=cartItemRepository.findById(cartItemid);
        
        if(opt.isPresent())
        {
        	return opt.get();
        }
        
         throw new CartItemException("cartItem not found with id"+cartItemid);
	}

}

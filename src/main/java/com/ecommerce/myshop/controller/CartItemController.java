package com.ecommerce.myshop.controller;

import javax.xml.catalog.CatalogException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.myshop.exception.CartItemException;
import com.ecommerce.myshop.exception.UserException;
import com.ecommerce.myshop.model.CartItem;
import com.ecommerce.myshop.model.User;
import com.ecommerce.myshop.response.ApiResponse;
import com.ecommerce.myshop.service.CartItemService;
import com.ecommerce.myshop.service.UserService;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/api/cart_items")
public class CartItemController {

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartItemService cartItemService;
	
	
	@DeleteMapping("/{cartItemId}")
	public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long cartItemId,@RequestHeader("Authorization")String jwt)throws UserException,CartItemException{
		
		User user=userService.findUserProfileByJwt(jwt);
		
		
			cartItemService.removeCartItem(user.getId(), cartItemId);

		ApiResponse res=new ApiResponse();
		res.setMessage(" deleted item  from cart");
		res.setStatus(true);
		
		return new ResponseEntity<ApiResponse>(res,HttpStatus.OK);
	}
	
	@PutMapping("/{cartItemId}")
	public ResponseEntity<CartItem>updateCartItemHandler(@PathVariable Long cartItemId, @RequestBody CartItem cartItem, @RequestHeader("Authorization")String jwt) throws CartItemException, UserException{
		
		User user=userService.findUserProfileByJwt(jwt);
		
		CartItem updatedCartItem =cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
		
		//ApiResponse res=new ApiResponse("Item Updated",true);
		
		return new ResponseEntity<>(updatedCartItem,HttpStatus.ACCEPTED);
	}
	
    
	
	
	
}

package com.ecommerce.myshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.myshop.exception.ProductException;
import com.ecommerce.myshop.exception.UserException;
import com.ecommerce.myshop.model.Cart;
import com.ecommerce.myshop.model.User;
import com.ecommerce.myshop.request.AddItemRequest;
import com.ecommerce.myshop.response.ApiResponse;
import com.ecommerce.myshop.service.CartService;
import com.ecommerce.myshop.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/cart/")
@Tag(name="Cart Mangement",description="find user cart, add item to cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/")
//	@Operation(description="find cart by user id")
	public ResponseEntity<Cart>findUserCart(@RequestHeader("Authorization")String jwt)throws UserException{
		User user=userService.findUserProfileByJwt(jwt);
		Cart cart=cartService.findUserCart(user.getId());
		
		return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
	
	
	@PutMapping("/add")
//	@Operation(description="add item to cart")
	public ResponseEntity<ApiResponse>addItemToCart(@RequestBody AddItemRequest req,@RequestHeader("Authorization")String jwt)throws UserException,ProductException{
		
		User user=userService.findUserProfileByJwt(jwt);
		
		cartService.addCartItem(user.getId(), req);
		
		ApiResponse res=new ApiResponse();
		res.setMessage(" item added to cart successfully");
		res.setStatus(true);
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	

	
}

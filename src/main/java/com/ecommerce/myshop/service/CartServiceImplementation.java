package com.ecommerce.myshop.service;


import org.springframework.stereotype.Service;

import com.ecommerce.myshop.exception.ProductException;
import com.ecommerce.myshop.model.Cart;
import com.ecommerce.myshop.model.CartItem;
import com.ecommerce.myshop.model.Product;
import com.ecommerce.myshop.model.User;
import com.ecommerce.myshop.repository.CartRepository;
import com.ecommerce.myshop.request.AddItemRequest;

@Service
public class CartServiceImplementation implements CartService {

	private CartRepository cartRepository;
	private CartItemService cartItemService;
	private ProductService productService;
	
	
	
	
	public CartServiceImplementation(CartRepository cartRepository, CartItemService cartItemService,
			ProductService productService) {
		super();
		this.cartRepository = cartRepository;
		this.cartItemService = cartItemService;
		this.productService = productService;
	}

	@Override
	public Cart creatCart(User user) {
		
		Cart cart =new Cart();
		
		cart.setUser(user);
		
		return cartRepository.save(cart);
	}

	@Override
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
	
		Cart cart=cartRepository.findByUserId(userId);
		
		Product product=productService.findProductById(req.getProductId());
		CartItem isPresent=cartItemService.isCartItemExist(cart, product, req.getSize(), userId);
		
		if(isPresent==null)
		{
			CartItem cartItem=new CartItem();
			
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setQuantity(req.getQuantity());
			cartItem.setUserId(userId);
			
			int price=req.getQuantity()*product.getDiscountedPrice();
			cartItem.setPrice(price);
			cartItem.setSize(req.getSize());
			
			CartItem createdCartItem=cartItemService.creatCartItem(cartItem);
			
			cart.getCartItem().add(createdCartItem);
		}
		
		return "Item Added To Cart";
	}

	@Override
	public Cart findUserCart(Long userId) {
		
		Cart cart=cartRepository.findByUserId(userId);
		
		int totalPrice=0;
		
		int totalDiscountedPrice=0;
		int totalItem=0;
		
//		5item
//		1 100rs
//		2 200rs
//		3 100rs
//		4 1999re
		for(CartItem cartItem:cart.getCartItem())
		{
			totalPrice=totalPrice+cartItem.getPrice();
			totalDiscountedPrice=totalDiscountedPrice+cartItem.getDiscountedPrice();
			totalItem=totalItem+cartItem.getQuantity();
			
		}
		
		cart.setTotalDiscountPrice(totalDiscountedPrice);
		cart.setTotalItem(totalItem);
		cart.setTotalPrice(totalPrice);
		cart.setDiscount(totalPrice-totalDiscountedPrice);
		
		
		return cartRepository.save(cart);
	}

}

package com.ecommerce.myshop.service;

import java.util.List;

import com.ecommerce.myshop.exception.ProductException;
import com.ecommerce.myshop.model.Rating;
import com.ecommerce.myshop.model.User;
import com.ecommerce.myshop.request.RatingRequest;

public interface RatingService {

	public Rating createRating(RatingRequest req,User user)throws ProductException;
	public List<Rating> getProductsRating(Long productId);
	
}

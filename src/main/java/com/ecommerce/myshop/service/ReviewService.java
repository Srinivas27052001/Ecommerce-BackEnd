package com.ecommerce.myshop.service;

import java.util.List;

import com.ecommerce.myshop.exception.ProductException;
import com.ecommerce.myshop.model.Review;
import com.ecommerce.myshop.model.User;
import com.ecommerce.myshop.request.ReviewRequest;

public interface ReviewService {

	public Review createReview(ReviewRequest req,User user)throws ProductException;
	
	public List<Review> getAllReview(Long productId);
}

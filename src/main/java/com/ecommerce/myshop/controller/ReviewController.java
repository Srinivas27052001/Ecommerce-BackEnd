package com.ecommerce.myshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.myshop.exception.ProductException;
import com.ecommerce.myshop.exception.UserException;
import com.ecommerce.myshop.model.Rating;
import com.ecommerce.myshop.model.Review;
import com.ecommerce.myshop.model.User;
import com.ecommerce.myshop.request.RatingRequest;
import com.ecommerce.myshop.request.ReviewRequest;
import com.ecommerce.myshop.service.ReviewService;
import com.ecommerce.myshop.service.UserService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private  UserService userService;
	
	
	@PostMapping("/create")
	public ResponseEntity<Review>createReview(@RequestBody ReviewRequest req,@RequestHeader("Authorization")String jwt)throws UserException,ProductException{
		
		User user=userService.findUserProfileByJwt(jwt);
		
		Review review=reviewService.createReview(req, user);
		return new ResponseEntity<Review>(review,HttpStatus.CREATED);
		
		
	}
	
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Review>> getProductsReview(@PathVariable Long productId,@RequestHeader("Authorization")String jwt)throws UserException,ProductException
	{
		User user=userService.findUserProfileByJwt(jwt);
		
		List<Review> reviews=reviewService.getAllReview(productId);
		return new ResponseEntity<List<Review>>(reviews,HttpStatus.CREATED);
		
		
	}
}

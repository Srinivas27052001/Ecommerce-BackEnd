package com.ecommerce.myshop.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.myshop.exception.ProductException;
import com.ecommerce.myshop.model.Product;
import com.ecommerce.myshop.model.Review;
import com.ecommerce.myshop.model.User;
import com.ecommerce.myshop.repository.ProductRepository;
import com.ecommerce.myshop.repository.ReviewRepository;
import com.ecommerce.myshop.request.ReviewRequest;

@Service
public class ReviewServiceImplementation implements ReviewService {

	private ReviewRepository reviewRepository;
	private ProductService productService;
	private ProductRepository productRepository;
	

	
	
	public ReviewServiceImplementation(ReviewRepository reviewRepository, ProductService productService,
			ProductRepository productRepository) {
		super();
		this.reviewRepository = reviewRepository;
		this.productService = productService;
		this.productRepository = productRepository;
	}

	@Override
	public Review createReview(ReviewRequest req, User user) throws ProductException {
		
		Product product=productService.findProductById(req.getProductId());
		
		Review review = new Review();
		review.setUser(user);
		review.setProduct(product);
		review.setReview(req.getReview());
		review.setCreatAt(LocalDateTime.now());
		
		return reviewRepository.save(review) ;
	}

	@Override
	public List<Review> getAllReview(Long productId) {
	
		
		return reviewRepository.getAllProductsReview(productId);
	}

	
}

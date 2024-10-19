package com.ecommerce.myshop.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.myshop.exception.ProductException;
import com.ecommerce.myshop.model.Product;
import com.ecommerce.myshop.model.Rating;
import com.ecommerce.myshop.model.User;
import com.ecommerce.myshop.repository.RatingRepository;
import com.ecommerce.myshop.request.RatingRequest;

@Service
public class RatingServiceImplementation implements RatingService{

	private RatingRepository ratingRepository;
	private ProductService productService;
	
	
	
	public RatingServiceImplementation(RatingRepository ratingRepository, ProductService productService) {
		super();
		this.ratingRepository = ratingRepository;
		this.productService = productService;
	}

	@Override
	public Rating createRating(RatingRequest req, User user) throws ProductException {
		Product product=productService.findProductById(req.getProductId());
		
		Rating rating=new Rating();
		rating.setProduct(product);
		rating.setUser(user);
		rating.setRating(req.getRating());
		rating.setCreatedAt(LocalDateTime.now());

		return ratingRepository.save(rating);
	}

	@Override
	public List<Rating> getProductsRating(Long productId) {
		// TODO Auto-generated method stub
		return ratingRepository.getAllProductsRating(productId);
	}
	
	

}

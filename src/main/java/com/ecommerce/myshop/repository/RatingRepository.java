package com.ecommerce.myshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.myshop.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
//	@Query("SELECT r FROM Rating r Where r.productId=:productId")

	@Query("SELECT r FROM Rating r WHERE r.product.id=:productId")
	public List<Rating > getAllProductsRating(@Param("productId")Long productId);
}

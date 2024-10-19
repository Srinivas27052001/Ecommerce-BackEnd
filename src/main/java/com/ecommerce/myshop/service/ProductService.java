package com.ecommerce.myshop.service;


import java.util.List;

import org.springframework.data.domain.Page;

import com.ecommerce.myshop.exception.ProductException;
import com.ecommerce.myshop.model.Product;
import com.ecommerce.myshop.request.CreateProductRequest;

public interface ProductService {

	
	public Product createProduct(CreateProductRequest req);
	
	public String deleteProduct(Long productId) throws ProductException;
	
	public Product updateProduct(Long productId,Product req) throws  ProductException;
	public List<Product> getAllProducts();
	
	public Product findProductById(Long id) throws ProductException;
	
	public List<Product> findProductByCategory(String category);
	
	public Page<Product> getAllProduct(String category,List<String>colors,List<String>sizes,Integer miniPrice,Integer maxPrice,
			Integer minDiscount,String sort,String stock,Integer pageNumber, Integer pageSize);

	public List<Product> searchProduct(String q);


}

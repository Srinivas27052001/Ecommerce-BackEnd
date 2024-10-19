package com.ecommerce.myshop.request;

import lombok.Data;

@Data
public class AddItemRequest {

	private Long productId;
	private String size;
	private int quantity;
	private Integer price;
	
	
	
	public AddItemRequest() {
		// TODO Auto-generated constructor stub
	}
	
	
}

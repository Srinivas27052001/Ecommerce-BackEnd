package com.ecommerce.myshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.myshop.model.OrderItem;
import com.ecommerce.myshop.repository.OrderItemRepository;

@Service
public class OrderItemServiceImplemention implements OrderItemService{

	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {
		
		
		return orderItemRepository.save(orderItem);
	}

	
}

package com.ecommerce.myshop.service;

import java.util.List;


import com.ecommerce.myshop.exception.OrderException;
import com.ecommerce.myshop.model.Address;
import com.ecommerce.myshop.model.Order;
import com.ecommerce.myshop.model.User;

public interface OrderService {

	public Order createOrder(User user,Address shippingAddress);
	
	public Order findOrderById(Long orderId) throws OrderException;
	
	public List<Order> userOrderHistory(Long userId);
	
	public Order placeOrder(Long orderId) throws OrderException;
	
	public Order confirmedOrder(Long orderId) throws OrderException;
	
	public Order shippedOrder(Long orderId) throws OrderException;
	
	public Order deliveredOrder(Long orderId) throws OrderException;
	
	public Order canceledOrder(Long orderId) throws OrderException;
	
	public List<Order>getAllOrders();
	
	public void deleteOrder(Long orderId) throws OrderException;
	
}

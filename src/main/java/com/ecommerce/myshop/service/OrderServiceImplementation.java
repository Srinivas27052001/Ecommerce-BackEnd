package com.ecommerce.myshop.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.myshop.exception.OrderException;
import com.ecommerce.myshop.model.Address;
import com.ecommerce.myshop.model.Cart;
import com.ecommerce.myshop.model.CartItem;
import com.ecommerce.myshop.model.Order;
import com.ecommerce.myshop.model.OrderItem;
import com.ecommerce.myshop.model.User;
import com.ecommerce.myshop.repository.AddressRepository;
import com.ecommerce.myshop.repository.CartRepository;
import com.ecommerce.myshop.repository.OrderItemRepository;
import com.ecommerce.myshop.repository.OrderRepository;
import com.ecommerce.myshop.repository.UserRepository;
import com.ecommerce.myshop.user.domain.OrderStatus;

@Service
public class OrderServiceImplementation implements OrderService{

	private OrderRepository orderRepository;
	private CartService cartService;
	private AddressRepository addressRepository;
	private UserRepository userRepository;
	private OrderItemService orderItemService;
	private OrderItemRepository orderItemRepository;


	
	


	public OrderServiceImplementation(OrderRepository orderRepository, CartService cartService,
			AddressRepository addressRepository, UserRepository userRepository, OrderItemService orderItemService,
			OrderItemRepository orderItemRepository) {
		super();
		this.orderRepository = orderRepository;
		this.cartService = cartService;
		this.addressRepository = addressRepository;
		this.userRepository = userRepository;
		this.orderItemService = orderItemService;
		this.orderItemRepository = orderItemRepository;
	}

	@Override
	public Order createOrder(User user, Address shippingAddress) {
		
		shippingAddress.setUser(user);
		Address address=addressRepository.save(shippingAddress);
		user.getAddress().add(address);
		
		userRepository.save(user);
		Cart cart=cartService.findUserCart(user.getId());
		List<OrderItem> orderItems=new ArrayList<>();
		
		for(CartItem item:cart.getCartItem())
		{
		     OrderItem orderItem=new OrderItem();
		     
		     orderItem.setPrice(item.getPrice());
		     orderItem.setProduct(item.getProduct());
		     orderItem.setQuantity(item.getQuantity());
		     orderItem.setSize(item.getSize());
		     orderItem.setUserId(item.getUserId());
		     orderItem.setDiscountedPrice(item.getDiscountedPrice());
		     
		     OrderItem createdOrderItem=orderItemRepository.save(orderItem);
		     
		     orderItems.add(createdOrderItem);
		     
		     
		}
		
		Order createOrder=new Order();
		
		createOrder.setUser(user);
		createOrder.setOrderItems(orderItems);
		createOrder.setCreatedAt(null);
		
		createOrder.setTotalDiscountedPrice(cart.getTotalDiscountPrice());
		createOrder.setDiscount(cart.getDiscount());
		createOrder.setTotalItem(cart.getTotalItem());
		createOrder.setTotalPrice(cart.getTotalPrice());
		createOrder.setShippingAddress(address);
		createOrder.setOrderDate(LocalDateTime.now());
		createOrder.setOrderStatus(OrderStatus.PENDING);
		createOrder.getPaymentDetails().setStatus("PENDING");
		createOrder.setCreatedAt(LocalDateTime.now());
		
		Order savedOrder=orderRepository.save(createOrder);
		
		for(OrderItem item:orderItems)
		{
			item.setOrder(savedOrder);
			orderItemRepository.save(item);
		}
		
		return savedOrder;
	}

	@Override
	public Order placeOrder(Long orderId) throws OrderException {
		
		Order order=findOrderById(orderId);
		order.setOrderStatus(OrderStatus.PLACED);
		order.getPaymentDetails().setStatus("COMPLETED");
		
		return order;
	}

	@Override
	public Order confirmedOrder(Long orderId) throws OrderException {
		
		Order order=findOrderById(orderId);
		order.setOrderStatus(OrderStatus.CONFIRMED);
		
		return orderRepository.save(order);
	}

	@Override
	public Order shippedOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus(OrderStatus.SHIPPED);
	
		return orderRepository.save(order);
	}

	@Override
	public Order deliveredOrder(Long orderId) throws OrderException {
	
	     Order order=findOrderById(orderId);
	     order.setOrderStatus(OrderStatus.DELIVERED);
	     
		return orderRepository.save(order);
	}

	@Override
	public Order canceledOrder(Long orderId) throws OrderException {
		
		Order order=findOrderById(orderId);
		order.setOrderStatus(OrderStatus.CANCELLED);
		
		return orderRepository.save(order);
	}

	@Override
	public Order findOrderById(Long orderId) throws OrderException {

		Optional<Order> opt=orderRepository.findById(orderId);
		if(opt.isPresent())
		{
			return opt.get();
			
		}
		throw new OrderException("order not exist wit id"+orderId);
		
	}

	@Override
	public List<Order> userOrderHistory(Long userId) {
		List<Order> orders=orderRepository.getUsersOrders(userId);
		
		return orders;
	}

	
	@Override
	public List<Order> getAllOrders() {
	   
		return orderRepository.findAll();
	}

	@Override
	public void deleteOrder(Long orderId) throws OrderException {
		
		Order order=findOrderById(orderId);
		orderRepository.deleteById(orderId);
		

	}

}

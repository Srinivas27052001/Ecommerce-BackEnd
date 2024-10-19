package com.ecommerce.myshop.controller;

import org.hibernate.internal.build.AllowSysOut;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.myshop.exception.OrderException;
import com.ecommerce.myshop.model.Order;
import com.ecommerce.myshop.repository.OrderRepository;
import com.ecommerce.myshop.response.ApiResponse;
import com.ecommerce.myshop.service.OrderService;
import com.ecommerce.myshop.service.UserService;
import com.ecommerce.myshop.user.domain.OrderStatus;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@RestController
@RequestMapping("/api")
public class PaymentController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Value("${razorpay.api.key}")
	String apiKey;
	
	@Value("${razorpay.api.secret}")
	String apiSecret;
	

	
	
	@PostMapping("/payments/{orderId}")
	public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable Long orderId,
			@RequestHeader("Authorization")String jwt) throws OrderException, RazorpayException
	{
		Order order=orderService.findOrderById(orderId);
		try {
			RazorpayClient razorpay=new RazorpayClient(apiKey,apiSecret);
			
			System.out.println(apiKey);
			JSONObject paymentLinkRequest=new JSONObject();
			
			paymentLinkRequest.put("amount",order.getTotalDiscountedPrice()*100);
			paymentLinkRequest.put("currency","INR");
			
			JSONObject customer=new JSONObject();
			customer.put("name", order.getUser().getFirstName());
			customer.put("email", order.getUser().getEmail());
			paymentLinkRequest.put("customer", customer);
			
			JSONObject notify=new JSONObject();
			notify.put("sms", true);
			notify.put("email", true);
			paymentLinkRequest.put("notify", notify);
			
			paymentLinkRequest.put("callback_url","http://localhost:3000/payment/"+orderId);
			paymentLinkRequest.put("callback_method", "get");
			
			PaymentLink payment=razorpay.paymentLink.create(paymentLinkRequest);
			
			String paymentLinkId=payment.get("Id");
			String paymentLinkUrl=payment.get("short_url");
			
			PaymentLinkResponse res=new PaymentLinkResponse();
			res.setPayment_link_id(paymentLinkId);
			res.setPayment_link_url(paymentLinkUrl);
			
  
		      
//		      PaymentLink fetchedPayment = razorpay.paymentLink.fetch(paymentLinkId);
//		      
//		      order.setOrderId(fetchedPayment.get("order_id"));
//		      orderRepository.save(order);
//			
			return new ResponseEntity<PaymentLinkResponse>(res,HttpStatus.CREATED);
					
		}catch (Exception e) {
			
			throw new RazorpayException(e.getMessage());
		}
	}
		
	
	@GetMapping("/payments")
	public ResponseEntity<ApiResponse> redirect(@RequestParam(name="payment_id")String paymentId,@RequestParam
				(name="order_id")Long orderId) throws OrderException, RazorpayException{
		
		
		System.out.println(orderId +"    "+paymentId);
			 
		Order order=orderService.findOrderById(orderId);
		
		
		
		
		
		
		RazorpayClient razorpay=new RazorpayClient(apiKey, apiSecret);
		System.out.println("paymentid"+paymentId);
		
		try {
			
			Payment payment=razorpay.payments.fetch(paymentId);
			if(payment.get("status").equals("captured")) { 
				
				System.out.println("payment"+payment);
				String p=payment.get("status");
				System.out.println(p);
				order.getPaymentDetails().setPaymentId(paymentId);
				order.getPaymentDetails().setStatus("COMPLETED");
				order.setOrderStatus(OrderStatus.PLACED);
				System.out.println(paymentId);
				
				orderRepository.save(order)
				;
				
				
			}
			
		   ApiResponse res=new ApiResponse();
		   res.setMessage("your order get placed");
           res.setStatus(true);		   
		   
           return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
			
		}catch (Exception e) {
			throw new RazorpayException(e.getMessage());
			
		}

	
	}
	
	

}

package com.ecommerce.myshop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.myshop.config.JwtProvider;
import com.ecommerce.myshop.exception.UserException;
import com.ecommerce.myshop.model.Cart;
import com.ecommerce.myshop.model.User;
import com.ecommerce.myshop.repository.UserRepository;
import com.ecommerce.myshop.request.LoginRequest;
import com.ecommerce.myshop.response.AuthResponse;
import com.ecommerce.myshop.service.CartService;
import com.ecommerce.myshop.service.CustomerUserServiceImplementation;


@RestController
@RequestMapping("/auth")
public class AuthController {

  private UserRepository userRepository;
 
  private JwtProvider jwtProvider;

  
  private PasswordEncoder passwordEncoder;
  private CustomerUserServiceImplementation customerUserService;
  private CartService cartService;
   
	



	public AuthController(UserRepository userRepository, JwtProvider jwtProvider, PasswordEncoder passwordEncoder,
		CustomerUserServiceImplementation customerUserService, CartService cartService) {
	super();
	this.userRepository = userRepository;
	this.jwtProvider = jwtProvider;
	this.passwordEncoder = passwordEncoder;
	this.customerUserService = customerUserService;
	this.cartService = cartService;
}

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> creatUserHandler(@RequestBody User user) throws UserException
	{
		String email=user.getEmail();
		String password=user.getPassword();
		String firstName=user.getFirstName();
		String lastName=user.getLastName();
		
	
		
	   User isEmailExist=userRepository.findByEmail(email);
	   
	   if(isEmailExist!=null)
	   {
		   throw new UserException("Email is Already Used With Another Account");
	   }
	   
	   
	   User createUser=new User();
			   createUser.setEmail(email);
	           createUser.setPassword(passwordEncoder.encode(password));
	           createUser.setFirstName(firstName);
	           createUser.setLastName(lastName);
	         
	           User savedUser=userRepository.save(createUser);
	           
	           Cart cart=cartService.creatCart(savedUser);
	           
	      	 Authentication authentication=new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
	      	 SecurityContextHolder.getContext().setAuthentication(authentication);
	      	 
	      	 String token=jwtProvider.generateToken(authentication);
	      	 
	      	 AuthResponse authResponse=new AuthResponse();
	      	 authResponse.setJwt(token);
	     	 authResponse.setMessage("Signup Success");
	      	 
	      	 return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
	      	 
	      	 
	}
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest){
		
		String username=loginRequest.getEmail();
		String password=loginRequest.getPassword();
		Authentication authentication=authenticate(username,password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token=jwtProvider.generateToken(authentication);
     	 
     	 AuthResponse authResponse=new AuthResponse();
     	 authResponse.setJwt(token);
     	 authResponse.setMessage("Signin Success");
     	 
     	 return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
		

		
	}



	private Authentication authenticate(String username, String password) {
	
		UserDetails userDetails=customerUserService.loadUserByUsername(username);
		if(userDetails==null)
		{
			throw new BadCredentialsException("Invalid Username or Password");
		}
		
		if(!passwordEncoder.matches(password, userDetails.getPassword()))
		{
			throw new BadCredentialsException("Invlid password or Password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
	}
	

}

package com.ecommerce.myshop.service;

import java.util.ArrayList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

import com.ecommerce.myshop.model.User;
import com.ecommerce.myshop.repository.UserRepository;

@Service
public class CustomerUserServiceImplementation implements UserDetailsService{

	private UserRepository userRepository;
	
	
	
	
	
	public CustomerUserServiceImplementation(UserRepository userRepository) {
		
		this.userRepository=userRepository;
	}





	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 
		       User user=userRepository.findByEmail(username);
		       if(user==null)
		       {
		    	   throw new UsernameNotFoundException("user not found with email-"+username);
		       }
		       List<GrantedAuthority> authorities=new ArrayList<>();
		       
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
	}


}

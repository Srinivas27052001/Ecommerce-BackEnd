package com.ecommerce.myshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.myshop.config.JwtProvider;
import com.ecommerce.myshop.exception.UserException;
import com.ecommerce.myshop.model.User;
import com.ecommerce.myshop.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {

	
	private UserRepository userRepository;
	private JwtProvider jwtProvider;
	
	
	public UserServiceImplementation(UserRepository userRepository, JwtProvider jwtProvider) {
		super();
		this.userRepository = userRepository;
		this.jwtProvider = jwtProvider;
	}

	@Override
	public User findUserById(Long userid) throws UserException {
	    Optional<User> user=userRepository.findById(userid);
	    if(user.isPresent())
	    {
	    	return user.get();
	    }
	    
	    throw new UserException("User Not found with id:"+userid);
	
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		
		String email=jwtProvider.getEmailFromToken(jwt);
				
				
				User user=userRepository.findByEmail(email);
		if(user==null)
		{
			throw new UserException("User not found with email"+email);
		}
		
		return user;
	}
	
	@Override
	public List<User> findAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAllByOrderByCreatedAtDesc();
	}

}

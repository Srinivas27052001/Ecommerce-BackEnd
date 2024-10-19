package com.ecommerce.myshop.service;

import java.util.List;

import com.ecommerce.myshop.exception.UserException;
import com.ecommerce.myshop.model.User;

public interface UserService {

	
	public User findUserById(Long userid) throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;

	public List<User> findAllUsers();
	
	
}

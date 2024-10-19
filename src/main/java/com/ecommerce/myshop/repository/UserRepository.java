package com.ecommerce.myshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.myshop.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	public User findByEmail(String email);

	public List<User> findAllByOrderByCreatedAtDesc();
}

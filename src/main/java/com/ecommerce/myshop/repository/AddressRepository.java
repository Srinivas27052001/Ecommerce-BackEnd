package com.ecommerce.myshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.myshop.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

	
}

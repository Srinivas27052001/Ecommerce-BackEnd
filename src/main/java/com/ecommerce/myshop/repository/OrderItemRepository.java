package com.ecommerce.myshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.myshop.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}

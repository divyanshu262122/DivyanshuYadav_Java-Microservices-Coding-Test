package com.example.orders.repository;

import com.example.orders.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<Order> save(Order order);

    Optional<Order> findById(String orderId);

    List<Order> findAll();
}

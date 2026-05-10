package com.example.orders.repository;

import com.example.orders.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe, in-memory implementation of {@link OrderRepository} backed by
 * a {@link ConcurrentHashMap}.
 */
@Repository
public class InMemoryOrderRepository implements OrderRepository {

    private static final Logger log = LoggerFactory.getLogger(InMemoryOrderRepository.class);

    /**
     * Primary data store. ConcurrentHashMap chosen over synchronizedMap because:
     * (1) It uses segment-level locking, allowing higher read/write concurrency.
     * (2) Its atomic compound operations (putIfAbsent, computeIfAbsent) eliminate
     *     the need for external synchronization for the most common patterns.
     */
    private final ConcurrentHashMap<String, Order> store = new ConcurrentHashMap<>();

    @Override
    public Optional<Order> save(Order order) {
        Order existing = store.putIfAbsent(order.getOrderId(), order);
        if (existing != null) {
            log.warn("Attempted to insert duplicate orderId={}", order.getOrderId());
            return Optional.empty();   
        }
        log.info("Order persisted: orderId={}", order.getOrderId());
        return Optional.of(order);
    }

    @Override
    public Optional<Order> findById(String orderId) {
        return Optional.ofNullable(store.get(orderId));
    }

    @Override
    public List<Order> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(store.values()));
    }
}

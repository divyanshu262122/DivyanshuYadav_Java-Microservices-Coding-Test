package com.example.orders.service;

import com.example.orders.dto.OrderRequest;
import com.example.orders.dto.OrderResponse;
import com.example.orders.dto.StatusUpdateRequest;
import com.example.orders.exception.DuplicateOrderException;
import com.example.orders.exception.InvalidOrderStatusTransitionException;
import com.example.orders.exception.OrderNotFoundException;
import com.example.orders.model.Order;
import com.example.orders.model.OrderStatus;
import com.example.orders.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    
    @Override
    public OrderResponse createOrder(OrderRequest request) {
        log.info("Creating order: orderId={}, customer={}, amount={}",
                request.getOrderId(), request.getCustomerName(), request.getAmount());

        Order newOrder = new Order(
                request.getOrderId(),
                request.getCustomerName(),
                request.getAmount(),
                OrderStatus.NEW
        );

        return orderRepository.save(newOrder)
                .map(saved -> {
                    log.info("Order created successfully: orderId={}", saved.getOrderId());
                    return toResponse(saved);
                })
                .orElseThrow(() -> new DuplicateOrderException(request.getOrderId()));
    }

    
    @Override
    public OrderResponse getOrderById(String orderId) {
        log.info("Fetching order: orderId={}", orderId);
        Order order = fetchOrderOrThrow(orderId);
        return toResponse(order);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        log.info("Fetching all orders");
        List<OrderResponse> orders = orderRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        log.info("Returned {} orders", orders.size());
        return orders;
    }

    
    @Override
    public OrderResponse updateOrderStatus(String orderId, StatusUpdateRequest request) {
        log.info("Updating status for orderId={} to {}", orderId, request.getStatus());

        Order order = fetchOrderOrThrow(orderId);

        /*
         * Synchronize on the individual Order instance to make the transition check
         * and the state mutation atomic. Locking on the map itself (or a global lock)
         * would unnecessarily serialize unrelated orders; per-object locking is more
         * granular and scalable under concurrent load.
         */
        synchronized (order) {
            OrderStatus currentStatus   = order.getStatus();
            OrderStatus requestedStatus = request.getStatus();

            if (!currentStatus.canTransitionTo(requestedStatus)) {
                log.warn("Invalid status transition for orderId={}: {} → {}",
                        orderId, currentStatus, requestedStatus);
                throw new InvalidOrderStatusTransitionException(currentStatus, requestedStatus);
            }

            order.applyStatusTransition(requestedStatus);
        }

        log.info("Order status updated: orderId={}, newStatus={}", orderId, order.getStatus());
        return toResponse(order);
    }

    
    private Order fetchOrderOrThrow(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    /**
     * Manual DTO mapper: converts an {@link Order} domain object to an {@link OrderResponse}.
     */
    private OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getOrderId(),
                order.getCustomerName(),
                order.getAmount(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
}

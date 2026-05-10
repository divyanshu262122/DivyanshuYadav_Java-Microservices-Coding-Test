package com.example.orders.service;

import com.example.orders.dto.OrderRequest;
import com.example.orders.dto.OrderResponse;
import com.example.orders.dto.StatusUpdateRequest;

import java.util.List;


public interface OrderService {

    /**
     * Creates a new order from the given request.
     */
    OrderResponse createOrder(OrderRequest request);

    /**
     * Retrieves an order by its unique identifier.
     */
    OrderResponse getOrderById(String orderId);

    /**
     * Transitions the status of an existing order.
     */
    OrderResponse updateOrderStatus(String orderId, StatusUpdateRequest request);

    /**
     * Returns a snapshot of all orders in the system.
     */
    List<OrderResponse> getAllOrders();
}

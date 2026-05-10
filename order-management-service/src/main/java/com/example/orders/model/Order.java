package com.example.orders.model;

import java.time.Instant;


public class Order {

    private final String orderId;
    private final String customerName;
    private final Double amount;
    private volatile OrderStatus status;          // volatile for safe reads across threads
    private final Instant createdAt;
    private volatile Instant updatedAt;

    public Order(String orderId, String customerName, Double amount, OrderStatus status) {
        this.orderId      = orderId;
        this.customerName = customerName;
        this.amount       = amount;
        this.status       = status;
        this.createdAt    = Instant.now();
        this.updatedAt    = this.createdAt;
    }

    //  Accessors 

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Double getAmount() {
        return amount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }


    /**
     * Updates the order status and records the modification timestamp.
     * Called exclusively from the Service layer after transition validation.
     */
    public void applyStatusTransition(OrderStatus newStatus) {
        this.status    = newStatus;
        this.updatedAt = Instant.now();
    }

    @Override
    public String toString() {
        return "Order{orderId='" + orderId + "', customer='" + customerName +
               "', amount=" + amount + ", status=" + status + '}';
    }
}

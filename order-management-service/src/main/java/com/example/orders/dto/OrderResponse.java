package com.example.orders.dto;

import com.example.orders.model.OrderStatus;

import java.time.Instant;

public class OrderResponse {

    private String orderId;
    private String customerName;
    private Double amount;
    private OrderStatus status;
    private Instant createdAt;
    private Instant updatedAt;


    public OrderResponse() {
    	// No-arg constructor needed for JSON deserialization in Jackson
    }

    public OrderResponse(String orderId, String customerName, Double amount,
                         OrderStatus status, Instant createdAt, Instant updatedAt) {
        this.orderId      = orderId;
        this.customerName = customerName;
        this.amount       = amount;
        this.status       = status;
        this.createdAt    = createdAt;
        this.updatedAt    = updatedAt;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}

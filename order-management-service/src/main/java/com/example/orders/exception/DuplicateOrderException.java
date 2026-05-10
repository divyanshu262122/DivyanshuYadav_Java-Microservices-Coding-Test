package com.example.orders.exception;


public class DuplicateOrderException extends RuntimeException {

    private final String orderId;

    public DuplicateOrderException(String orderId) {
        super("An order with ID '" + orderId + "' already exists.");
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}

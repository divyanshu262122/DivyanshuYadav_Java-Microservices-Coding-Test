package com.example.orders.exception;

import com.example.orders.model.OrderStatus;


public class InvalidOrderStatusTransitionException extends RuntimeException {

    private final OrderStatus currentStatus;
    private final OrderStatus requestedStatus;

    public InvalidOrderStatusTransitionException(OrderStatus currentStatus, OrderStatus requestedStatus) {
        super(String.format(
                "Cannot transition order from status '%s' to '%s'. " +
                "Allowed transitions: NEW→PROCESSING, PROCESSING→COMPLETED.",
                currentStatus, requestedStatus));
        this.currentStatus   = currentStatus;
        this.requestedStatus = requestedStatus;
    }

    public OrderStatus getCurrentStatus() {
        return currentStatus;
    }

    public OrderStatus getRequestedStatus() {
        return requestedStatus;
    }
}

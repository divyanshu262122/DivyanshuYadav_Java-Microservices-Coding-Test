package com.example.orders.dto;

import com.example.orders.model.OrderStatus;
import jakarta.validation.constraints.NotNull;


public class StatusUpdateRequest {

    @NotNull(message = "status must not be null")
    private OrderStatus status;


    public StatusUpdateRequest() {
    	// No-arg constructor needed for JSON deserialization in Jackson
    }

    public StatusUpdateRequest(OrderStatus status) {
        this.status = status;
    }


    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}

package com.example.orders.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class OrderRequest {

    @NotBlank(message = "orderId must not be blank")
    @Size(max = 64, message = "orderId must not exceed 64 characters")
    private String orderId;

    @NotBlank(message = "customerName must not be blank")
    @Size(max = 255, message = "customerName must not exceed 255 characters")
    private String customerName;

    @NotNull(message = "amount must not be null")
    @Positive(message = "amount must be greater than 0")
    private Double amount;


    public OrderRequest() {
    }

    public OrderRequest(String orderId, String customerName, Double amount) {
        this.orderId      = orderId;
        this.customerName = customerName;
        this.amount       = amount;
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
}

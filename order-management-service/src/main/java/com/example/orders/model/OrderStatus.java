package com.example.orders.model;

import java.util.EnumSet;
import java.util.Set;

public enum OrderStatus {
    NEW,
    PROCESSING,
    COMPLETED;

    private Set<OrderStatus> allowedNext;

    // Runs after ALL enum constants are constructed — no forward-reference issue.
    static {
        NEW.allowedNext        = EnumSet.of(PROCESSING);
        PROCESSING.allowedNext = EnumSet.of(COMPLETED);
        COMPLETED.allowedNext  = EnumSet.noneOf(OrderStatus.class);
    }

    public boolean canTransitionTo(OrderStatus next) {
        return allowedNext.contains(next);
    }
}
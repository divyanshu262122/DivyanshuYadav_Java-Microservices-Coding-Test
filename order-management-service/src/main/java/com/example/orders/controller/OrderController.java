package com.example.orders.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.orders.dto.OrderRequest;
import com.example.orders.dto.OrderResponse;
import com.example.orders.dto.StatusUpdateRequest;
import com.example.orders.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {

	private static final Logger log = LoggerFactory.getLogger(OrderController.class);

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	/**
	 * POST /orders — Create a new order. Returns {@code 201 Created} with the order resource in the body.
	 */
	@PostMapping
	public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
		log.info("POST ::: /orders — orderId={}", request.getOrderId());
		OrderResponse response = orderService.createOrder(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	/**
	 * GET /orders/{orderId} — Retrieve a single order by ID. Returns {@code 200 OK}
	 * or propagates {@code 404} via GlobalExceptionHandler.
	 */
	@GetMapping("/{orderId}")
	public ResponseEntity<OrderResponse> getOrderById(@PathVariable String orderId) {
		log.info("GET ::: /orders/{}", orderId);
		OrderResponse response = orderService.getOrderById(orderId);
		return ResponseEntity.ok(response);
	}

	/**
	 * PUT /orders/{orderId}/status — Transition an order's status. Returns
	 * {@code 200 OK} with the updated order, or propagates {@code 404} /
	 * {@code 422}.
	 */
	@PutMapping("/{orderId}/status")
	public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable String orderId,
			@Valid @RequestBody StatusUpdateRequest request) {
		log.info("PUT ::: /orders/{}/status — requestedStatus={}", orderId, request.getStatus());
		OrderResponse response = orderService.updateOrderStatus(orderId, request);
		return ResponseEntity.ok(response);
	}

	/**
	 * GET /orders — List all orders. Returns {@code 200 OK} with an array (empty array if no orders exist).
	 */
	@GetMapping
	public ResponseEntity<List<OrderResponse>> getAllOrders() {
		log.info("GET /orders");
		List<OrderResponse> orders = orderService.getAllOrders();
		return ResponseEntity.ok(orders);
	}
}

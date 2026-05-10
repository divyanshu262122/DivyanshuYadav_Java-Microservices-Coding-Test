# Order Management Microservice

A production-ready **Spring Boot** microservice for managing orders — built as part of the Java + Microservices Coding Test for **Reflections Info Solutions**.

---

## Author

**Divyanshu Yadav**

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.3 |
| Validation | Jakarta Bean Validation |
| Storage | In-Memory (`ConcurrentHashMap`) |
| Testing | JUnit 5 + MockMvc |
| Build Tool | Maven |

---

##  Project Structure

```
src/
├── main/java/com/example/orders/
│   ├── OrdersApplication.java
│   ├── controller/
│   │   └── OrderController.java
│   ├── service/
│   │   ├── OrderService.java
│   │   └── OrderServiceImpl.java
│   ├── repository/
│   │   ├── OrderRepository.java
│   │   └── InMemoryOrderRepository.java
│   ├── model/
│   │   ├── Order.java
│   │   └── OrderStatus.java
│   ├── dto/
│   │   ├── OrderRequest.java
│   │   ├── OrderResponse.java
│   │   └── StatusUpdateRequest.java
│   └── exception/
│       ├── GlobalExceptionHandler.java
│       ├── OrderNotFoundException.java
│       ├── DuplicateOrderException.java
│       ├── InvalidOrderStatusTransitionException.java
│       └── InvalidOrderDataException.java
├── main/resources/
│   └── application.properties
└── test/java/com/example/orders/
    └── controller/
        └── OrderControllerTest.java
```

---

##  How to Run

### Prerequisites
- Java 17+
- Maven 3.8+

### Steps

```bash
# 1. Clone the repository
git clone https://github.com/YOUR_USERNAME/YOUR_REPO_NAME.git
cd YOUR_REPO_NAME

# 2. Build the project
mvn clean install

# 3. Run the application
mvn spring-boot:run
```

Server starts at: **`http://localhost:8080`**

---

## API Endpoints

| Method | Endpoint | Description | Status Code |
|---|---|---|---|
| `POST` | `/orders` | Create a new order | `201 Created` |
| `GET` | `/orders/{orderId}` | Get order by ID | `200 OK` |
| `PUT` | `/orders/{orderId}/status` | Update order status | `200 OK` |
| `GET` | `/orders` | List all orders | `200 OK` |

---

##  Status Transition Rules

```
NEW ──► PROCESSING ──► COMPLETED
```

Any other transition returns `422 Unprocessable Entity`.

---

##  Sample cURL Commands

### Create Order
```bash
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{"orderId":"ORD-001","customerName":"Alice Smith","amount":149.99}'
```

### Get Order by ID
```bash
curl http://localhost:8080/orders/ORD-001
```

### Update Status (NEW → PROCESSING)
```bash
curl -X PUT http://localhost:8080/orders/ORD-001/status \
  -H "Content-Type: application/json" \
  -d '{"status":"PROCESSING"}'
```

### Update Status (PROCESSING → COMPLETED)
```bash
curl -X PUT http://localhost:8080/orders/ORD-001/status \
  -H "Content-Type: application/json" \
  -d '{"status":"COMPLETED"}'
```

### List All Orders
```bash
curl http://localhost:8080/orders
```

---

## Error Handling

All errors follow **RFC 7807 ProblemDetail** format:

```json
{
  "type": "https://example.com/errors/order-not-found",
  "title": "Order Not Found",
  "status": 404,
  "detail": "Order not found with ID: ORD-999",
  "orderId": "ORD-999"
}
```

| Scenario | HTTP Status |
|---|---|
| Order not found | `404 Not Found` |
| Duplicate orderId | `409 Conflict` |
| Invalid amount / blank fields | `400 Bad Request` |
| Invalid status transition | `422 Unprocessable Entity` |

---

## Requirements Checklist

| Requirement | Status |
|---|---|
| POST /orders | ✅ |
| GET /orders/{orderId} | ✅ |
| PUT /orders/{orderId}/status | ✅ |
| GET /orders | ✅ |
| Status transitions (NEW→PROCESSING→COMPLETED) | ✅ |
| 404 Order not found | ✅ |
| 400 Missing / invalid fields | ✅ |
| In-memory storage (no DB) | ✅ |
| **BONUS:** Jakarta Validation (`@Valid`) | ✅ |
| **BONUS:** DTO layer | ✅ |

---

## Approach & Design Decisions

- **Layered Architecture:** Controller → Service → Repository. Each layer has a single responsibility; the Controller never touches the repository directly.
- **Thread-Safe Storage:** `ConcurrentHashMap` with `putIfAbsent()` provides atomic, lock-free inserts. Status transitions use per-object `synchronized` blocks to prevent race conditions without a global lock.
- **Stateless Service:** `OrderServiceImpl` holds zero mutable state — all state lives in the repository — making the service safe as a singleton and horizontally scalable.
- **Extensible Design:** `OrderRepository` is an interface; swapping in a JPA-backed implementation requires zero changes to the Controller or Service layer.

---

## Running Tests

```bash
mvn test
```

9 test cases covering: create order (success + validations + duplicate), get by ID (found + not found), status transitions (valid + invalid), and list all orders.

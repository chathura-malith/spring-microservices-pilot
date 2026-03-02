# 🛒 LapMart — Spring Boot Microservices Pilot

[![Java](https://img.shields.io/badge/Java-17+-orange?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-Netflix%20Eureka-blue)](https://spring.io/projects/spring-cloud)
[![Maven](https://img.shields.io/badge/Build-Maven-red?logo=apachemaven)](https://maven.apache.org/)

A **Spring Boot Microservices** pilot project for a laptop e-commerce platform — **LapMart**. This project demonstrates a production-style microservices architecture using Spring Cloud, Netflix Eureka, API Gateway, OpenFeign, and MapStruct.

---

## 🏗️ Architecture Overview

```
Client
  │
  ▼
┌─────────────────────┐
│      API Gateway     │  :9090  (Spring Cloud Gateway)
│   + LoggingFilter    │
└────────┬────────────┘
         │
         ├──────────────────────┬──────────────────────┐
         ▼                      ▼                      ▼
┌──────────────────┐  ┌──────────────────┐  ┌──────────────────┐
│  Product Service  │  │  Order Service   │  │  Eureka Server   │
│     :9091         │  │     :9092        │  │     :8761         │
│  (REST API, DB)   │  │  (REST + Feign)  │  │ (Service Registry)│
└──────────────────┘  └──────────────────┘  └──────────────────┘
```

- **API Gateway** — Centralized entry point, routes all requests, handles cross-cutting concerns (logging)
- **Eureka Server** — Service registry for service discovery
- **Product Service API** — Manages laptop product catalog
- **Order Service API** — Manages customer orders, communicates with Product Service via Feign

---

## 📁 Project Structure

```
spring-microservices-pilot/
├── api-gateway/                        # Spring Cloud Gateway
│   └── src/main/java/.../api_gateway/
│       ├── ApiGatewayApplication.java
│       ├── config/GatewayConfig.java
│       └── filter/LoggingFilter.java
│
├── eureka-server/                      # Netflix Eureka Server
│   └── src/main/java/.../eureka_server/
│       └── EurekaServerApplication.java
│
├── product-service-api/                # Product Microservice
│   └── src/main/java/.../product_service_api/
│       ├── api/ProductController.java
│       ├── entity/Product.java
│       ├── dto/
│       │   ├── request/RequestProductDto.java
│       │   └── response/ResponseProductDto.java
│       ├── mapper/ProductMapper.java   (MapStruct)
│       ├── repo/ProductRepo.java
│       ├── service/
│       │   ├── ProductService.java
│       │   └── impl/ProductServiceImpl.java
│       ├── exception/
│       │   ├── EntryNotFoundException.java
│       │   └── InsufficientStockException.java
│       ├── advisor/AppWideExceptionHandler.java
│       └── util/StandardResponseDto.java
│
└── order-service-api/                  # Order Microservice
    └── src/main/java/.../order_service_api/
        ├── api/OrderController.java
        ├── entity/
        │   ├── Order.java
        │   └── OrderItem.java
        ├── enums/OrderStatus.java
        ├── dto/
        │   ├── request/RequestOrderDto.java
        │   ├── request/RequestOrderItemDto.java
        │   ├── request/RequestUpdateOrderDto.java
        │   ├── response/ResponseOrderDto.java
        │   ├── response/ResponseOrderItemDto.java
        │   ├── response/paginate/OrderPaginateResponseDto.java
        │   └── external/ExternalProductDto.java
        ├── proxy/ProductServiceProxy.java  (OpenFeign)
        ├── mapper/OrderMapper.java         (MapStruct)
        ├── repo/
        │   ├── OrderRepo.java
        │   └── OrderItemRepo.java
        ├── service/
        │   ├── OrderService.java
        │   └── impl/OrderServiceImpl.java
        ├── exception/
        │   ├── EntryNotFoundException.java
        │   └── ActionNotAllowedException.java
        ├── advisor/AppWideExceptionHandler.java
        └── util/StandardResponseDto.java
```

---

## 🧰 Tech Stack

| Technology | Purpose |
|---|---|
| **Spring Boot 3.x** | Core framework for all microservices |
| **Spring Cloud Gateway** | API Gateway & request routing |
| **Netflix Eureka** | Service discovery & registration |
| **OpenFeign** | Declarative HTTP client (Order → Product) |
| **Spring Data JPA** | ORM & database access |
| **MapStruct** | DTO ↔ Entity object mapping |
| **Maven** | Build tool |
| **Java 17+** | Programming language |

---

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.8+
- A relational database (MySQL / PostgreSQL) for Product & Order services

### 1. Clone the Repository

```bash
git clone https://github.com/chathura-malith/spring-microservices-pilot.git
cd spring-microservices-pilot
```

### 2. Configure Databases

Update `application.properties` in both `product-service-api` and `order-service-api`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/lapmart_products
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### 3. Start Services (in order)

```bash
# 1. Start Eureka Server first
cd eureka-server
./mvnw spring-boot:run

# 2. Start Product Service
cd ../product-service-api
./mvnw spring-boot:run

# 3. Start Order Service
cd ../order-service-api
./mvnw spring-boot:run

# 4. Start API Gateway last
cd ../api-gateway
./mvnw spring-boot:run
```

### 4. Service Ports

| Service | Port | URL |
|---|---|---|
| Eureka Server | 8761 | http://localhost:8761 |
| API Gateway | 9090 | http://localhost:9090 |
| Product Service | 9091 | http://localhost:9091 |
| Order Service | 9092 | http://localhost:9092 |

---

## 🔌 API Endpoints

> All requests should go through the **API Gateway** at `http://localhost:9090`

### Product Service

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/v1/products` | Get all products (paginated) |
| `GET` | `/api/v1/products/{id}` | Get product by ID |
| `POST` | `/api/v1/products` | Create a new product |
| `PUT` | `/api/v1/products/{id}` | Update a product |
| `DELETE` | `/api/v1/products/{id}` | Delete a product |

### Order Service

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/v1/orders` | Get all orders (paginated) |
| `GET` | `/api/v1/orders/{id}` | Get order by ID |
| `POST` | `/api/v1/orders` | Place a new order |
| `PUT` | `/api/v1/orders/{id}` | Update order status |

---

## 🔁 Inter-Service Communication

The **Order Service** communicates with the **Product Service** using **OpenFeign** for:
- Fetching product details when creating an order
- Validating stock availability

```java
// ProductServiceProxy.java (in Order Service)
@FeignClient(name = "product-service-api")
public interface ProductServiceProxy {
    @GetMapping("/api/v1/products/{id}")
    ExternalProductDto getProductById(@PathVariable Long id);
}
```

Service discovery is handled automatically via **Eureka** — no hardcoded URLs needed.

---

## 📦 Key Design Patterns

- **API Gateway Pattern** — Single entry point for all client requests
- **Service Registry Pattern** — Dynamic service discovery via Eureka
- **DTO Pattern** — Separate request/response DTOs from entities
- **Repository Pattern** — Spring Data JPA repositories
- **Global Exception Handling** — `@RestControllerAdvice` in each service
- **Standard Response Wrapper** — Consistent API response format via `StandardResponseDto`
- **Order Status Enum** — Controlled order lifecycle with `OrderStatus` enum

---

## 📊 Response Format

All services return a consistent response structure:

```json
{
  "status": 200,
  "message": "Success",
  "data": { ... }
}
```

---

## 🤝 Contributing

1. Fork the project
2. Create your feature branch: `git checkout -b feature/your-feature`
3. Commit your changes: `git commit -m 'Add some feature'`
4. Push to the branch: `git push origin feature/your-feature`
5. Open a Pull Request

---

## 📄 License

This project is open-source and available under the [MIT License](LICENSE).

---

## 👤 Author

**A.M. Chathura Malith Ariyarathna**

> Built as a pilot/learning project to explore Spring Boot Microservices architecture patterns.

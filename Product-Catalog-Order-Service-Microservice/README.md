# Product Catalog + Order Service - Spring Boot Microservices

This project demonstrates a microservices architecture using Spring Boot, featuring inter-service communication with Feign Client, H2 database, validation, and exception handling.

## Services

### 1. Product Service (Port: 8081)
- Manages products (id, name, price)
- Endpoints:
  - `GET /products` - List all products
  - `GET /products/{id}` - Get product by ID
  - `POST /products` - Add new product

### 2. Order Service (Port: 8082)
- Creates orders by fetching product info from Product Service
- Endpoints:
  - `GET /orders` - List all orders
  - `POST /orders` - Create order with payload: `{"productId": 1, "quantity": 2}`

## Features

- **Feign Client**: Inter-service communication between Order and Product services
- **H2 Database**: In-memory database for both services
- **Validation**: Input validation using Bean Validation API
- **Exception Handling**: Global exception handlers with proper error responses
- **Sample Data**: Pre-loaded sample products


### Run Services

#### Method 1: Using IntelliJ Run Configurations
1. **Product Service:**
   - Navigate to `product-service/src/main/java/com/example/productservice/ProductServiceApplication.java`
   - Right-click and select "Run 'ProductServiceApplication'"
   - Service will start on port 8081

2. **Order Service:**
   - Navigate to `order-service/src/main/java/com/example/orderservice/OrderServiceApplication.java`
   - Right-click and select "Run 'OrderServiceApplication'"
   - Service will start on port 8082

#### Method 2: Using Maven Terminal
1. Open Terminal in IntelliJ (Alt+F12)
2. **Start Product Service:**
   ```bash
   cd product-service
   mvn clean install
   mvn spring-boot:run
   ```
3. **Start Order Service** (in new terminal tab):
   ```bash
   cd order-service
   mvn clean install
   mvn spring-boot:run
   ```

## Testing the APIs

### Product Service APIs

1. **Get all products**:
```bash
curl http://localhost:8081/products
```

2. **Get product by ID**:
```bash
curl http://localhost:8081/products/1
```

3. **Add new product**:
```bash
curl -X POST http://localhost:8081/products \
  -H "Content-Type: application/json" \
  -d '{"name": "New Product", "price": 199.99}'
```

### Order Service APIs

1. **Get all orders**:
```bash
curl http://localhost:8082/orders
```

2. **Create order**:
```bash
curl -X POST http://localhost:8082/orders \
  -H "Content-Type: application/json" \
  -d '{"productId": 1, "quantity": 2}'
```

## H2 Console Access

- Product Service H2 Console: http://localhost:8081/h2-console
- Order Service H2 Console: http://localhost:8082/h2-console

**Connection Details:**
- JDBC URL: `jdbc:h2:mem:productdb` (for Product Service) or `jdbc:h2:mem:orderdb` (for Order Service)
- Username: `sa`
- Password: (leave empty)

## Sample Products

1. Laptop - $999.99
2. Mouse - $29.99
3. Keyboard - $79.99
4. Monitor - $299.99
5. Headphones - $149.99

## Project Structure

```
microservices-project/
├── pom.xml                 # Parent POM
├── product-service/        # Product Service
│   ├── pom.xml
│   └── src/main/java/com/example/productservice/
│       ├── ProductServiceApplication.java
│       ├── DataLoader.java
│       ├── controller/ProductController.java
│       ├── entity/Product.java
│       ├── repository/ProductRepository.java
│       ├── service/ProductService.java
│       ├── dto/ProductRequest.java
│       └── exception/...
├── order-service/          # Order Service
│   ├── pom.xml
│   └── src/main/java/com/example/orderservice/
│       ├── OrderServiceApplication.java
│       ├── controller/OrderController.java
│       ├── entity/Order.java
│       ├── repository/OrderRepository.java
│       ├── service/OrderService.java
│       ├── dto/OrderRequest.java
│       ├── client/ProductClient.java
│       └── exception/...
└── README.md
```

## Architecture Flow

1. Client sends order request to Order Service
2. Order Service validates request using @Valid
3. Order Service calls Product Service via Feign Client
4. Product Service returns product details
5. Order Service calculates total (price × quantity)
6. Order Service saves order to H2 database
7. Order Service returns created order

## Technology Stack

- **Spring Boot 3.1.5**
- **Spring Cloud OpenFeign**
- **Spring Data JPA**
- **H2 Database**
- **Bean Validation API**
- **Maven**
- **Java 17**

## Troubleshooting

1. **Port conflicts**: Ensure ports 8081 and 8082 are available
2. **Maven issues**: Run `mvn clean install` in root directory
3. **Feign client errors**: Ensure Product Service is running before Order Service
4. **Database issues**: Check H2 console for data verification

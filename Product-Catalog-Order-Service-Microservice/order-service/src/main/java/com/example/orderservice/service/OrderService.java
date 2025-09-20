package com.example.orderservice.service;

import com.example.orderservice.client.ProductClient;
import com.example.orderservice.dto.Product;
import com.example.orderservice.entity.Order;
import com.example.orderservice.exception.OrderCreationException;
import com.example.orderservice.exception.ProductNotFoundException;
import com.example.orderservice.repository.OrderRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductClient productClient;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order createOrder(Long productId, Integer quantity) {
        try {
            // Fetch product details from Product Service using Feign Client
            Product product = productClient.getProductById(productId);

            if (product == null) {
                throw new ProductNotFoundException("Product not found with id: " + productId);
            }

            // Calculate total amount
            Double totalAmount = product.getPrice() * quantity;

            // Create and save order
            Order order = new Order(productId, product.getName(), quantity, totalAmount);
            Order savedOrder = orderRepository.save(order);

            System.out.println("Order created successfully: " + savedOrder);
            return savedOrder;

        } catch (FeignException.NotFound e) {
            throw new ProductNotFoundException("Product not found with id: " + productId);
        } catch (FeignException e) {
            throw new OrderCreationException("Failed to fetch product details: " + e.getMessage());
        } catch (Exception e) {
            throw new OrderCreationException("Failed to create order: " + e.getMessage());
        }
    }
}

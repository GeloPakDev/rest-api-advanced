package com.epam.esm.service;

import com.epam.esm.Order;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface OrderService {
    Page<Order> findAll(int page, int size);

    Page<Order> findUsersOrders(Long id, int page, int size);

    Optional<Order> findOrderById(Long id);

    Optional<Order> findUserOrderById(Long userId, Long orderId);

    Order createOrder(Long userId, Order order);
}
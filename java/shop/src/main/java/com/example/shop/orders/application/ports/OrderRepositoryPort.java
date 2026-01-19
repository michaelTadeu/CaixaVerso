package com.example.shop.orders.application.ports;

import com.example.shop.orders.domain.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepositoryPort {
  Order save(Order order);
  Optional<Order> findById(UUID id);
}

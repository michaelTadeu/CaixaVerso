package com.example.orders.application.ports;

import com.example.orders.domain.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepositoryPort {
  Order save(Order order);

  Optional<Order> findById(UUID id);
}

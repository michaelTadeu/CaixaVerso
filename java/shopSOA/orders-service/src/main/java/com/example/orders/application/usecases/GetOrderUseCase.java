package com.example.orders.orders.application.usecases;

import com.example.orders.orders.application.ports.OrderRepositoryPort;
import com.example.orders.orders.domain.Order;
import com.example.orders.shared.errors.NotFoundException;

import java.util.UUID;

public class GetOrderUseCase {
  private final OrderRepositoryPort repo;

  public GetOrderUseCase(OrderRepositoryPort repo) {
    this.repo = repo;
  }

  public Order execute(UUID id) {
    return repo.findById(id).orElseThrow(() -> new NotFoundException("Order not found: " + id));
  }
}

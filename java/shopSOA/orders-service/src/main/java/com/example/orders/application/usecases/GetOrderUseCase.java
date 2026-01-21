package com.example.orders.application.usecases;

import com.example.orders.application.ports.OrderRepositoryPort;
import com.example.orders.domain.Order;
import com.example.shared.errors.NotFoundException;

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

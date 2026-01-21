package com.example.orders.application.usecases;

import com.example.orders.application.ports.OrderRepositoryPort;
import com.example.orders.domain.OrderStatus;
import com.example.orders.domain.rules.OrderStatusTransitionPolicy;

import java.util.UUID;

public class ChangeOrderStatusUseCase {
  private final OrderRepositoryPort repo;

  public ChangeOrderStatusUseCase(OrderRepositoryPort repo) {
    this.repo = repo;
  }

  public void execute(UUID orderId, OrderStatus newStatus) {
    var order = repo.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found"));

    if (!OrderStatusTransitionPolicy.canTransition(order.getStatus(), newStatus)) {
      throw new IllegalStateException("Invalid status transition: " + order.getStatus() + " -> " + newStatus);
    }

    order.setStatus(newStatus);
    repo.save(order);
  }
}

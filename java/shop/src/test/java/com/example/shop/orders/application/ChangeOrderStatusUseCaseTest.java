package com.example.shop.orders.application;

import com.example.shop.orders.application.ports.OrderRepositoryPort;
import com.example.shop.orders.application.usecases.ChangeOrderStatusUseCase;
import com.example.shop.orders.domain.Order;
import com.example.shop.orders.domain.OrderStatus;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ChangeOrderStatusUseCaseTest {

  @Test
  void shouldRejectInvalidTransition() {
    OrderRepositoryPort repo = new InMemoryRepo();
    var useCase = new ChangeOrderStatusUseCase(repo);

    var order = new Order(UUID.randomUUID(), OrderStatus.COMPLETED, List.of());
    repo.save(order);

    assertThrows(IllegalStateException.class, () -> useCase.execute(order.getId(), OrderStatus.IN_PROGRESS));
  }

  static class InMemoryRepo implements OrderRepositoryPort {
    private final Map<UUID, Order> db = new HashMap<>();

    @Override
    public Order save(Order order) {
      db.put(order.getId(), order);
      return order;
    }

    @Override
    public Optional<Order> findById(UUID id) {
      return Optional.ofNullable(db.get(id));
    }
  }
}

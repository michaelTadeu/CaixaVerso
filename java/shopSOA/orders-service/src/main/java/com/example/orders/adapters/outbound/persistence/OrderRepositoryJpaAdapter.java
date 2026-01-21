package com.example.orders.orders.adapters.outbound.persistence;

import com.example.orders.orders.application.ports.OrderRepositoryPort;
import com.example.orders.orders.domain.Order;
import com.example.orders.orders.domain.OrderItem;

import java.util.Optional;
import java.util.UUID;

public class OrderRepositoryJpaAdapter implements OrderRepositoryPort {

  private final SpringDataOrderRepository jpa;

  public OrderRepositoryJpaAdapter(SpringDataOrderRepository jpa) {
    this.jpa = jpa;
  }

  @Override
  public Order save(Order order) {
    var entity = jpa.findById(order.getId()).orElse(new JpaOrderEntity(order.getId(), order.getStatus()));
    entity.setStatus(order.getStatus());
    entity.getItems().clear();

    for (OrderItem item : order.getItems()) {
      entity.getItems().add(new JpaOrderItemEntity(entity, item.getProductId(), item.getQuantity(), item.getUnitPrice()));
    }

    var saved = jpa.save(entity);
    return toDomain(saved);
  }

  @Override
  public Optional<Order> findById(UUID id) {
    return jpa.findById(id).map(this::toDomain);
  }

  private Order toDomain(JpaOrderEntity entity) {
    var items = entity.getItems().stream()
        .map(i -> new OrderItem(i.getProductId(), i.getQuantity(), i.getUnitPrice()))
        .toList();
    return new Order(entity.getId(), entity.getStatus(), items);
  }
}

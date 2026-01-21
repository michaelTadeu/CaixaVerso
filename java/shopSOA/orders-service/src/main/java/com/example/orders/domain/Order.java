package com.example.orders.orders.domain;

import java.math.BigDecimal;
import java.util.*;

public class Order {
  private final UUID id;
  private OrderStatus status;
  private final List<OrderItem> items;

  public Order(UUID id, OrderStatus status, List<OrderItem> items) {
    if (id == null) throw new IllegalArgumentException("id is required");
    if (status == null) throw new IllegalArgumentException("status is required");
    if (items == null || items.isEmpty()) throw new IllegalArgumentException("Order must have at least 1 item");
    this.id = id;
    this.status = status;
    this.items = new ArrayList<>(items);
  }

  public static Order newOrder(List<OrderItem> items) {
    return new Order(UUID.randomUUID(), OrderStatus.NEW, items);
  }

  public UUID getId() { return id; }
  public OrderStatus getStatus() { return status; }
  public List<OrderItem> getItems() { return Collections.unmodifiableList(items); }

  public void setStatus(OrderStatus newStatus) {
    if (newStatus == null) throw new IllegalArgumentException("newStatus is required");
    this.status = newStatus;
  }

  public BigDecimal total() {
    return items.stream()
        .map(OrderItem::subtotal)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}

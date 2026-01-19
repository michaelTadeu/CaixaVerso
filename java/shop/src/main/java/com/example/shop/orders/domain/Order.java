package com.example.shop.orders.domain;

import java.math.BigDecimal;
import java.util.*;

public class Order {
  private final UUID id;
  private OrderStatus status;
  private final List<OrderItem> items;

  public Order(UUID id, OrderStatus status, List<OrderItem> items) {
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

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  public BigDecimal total() {
    return items.stream().map(OrderItem::subtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}

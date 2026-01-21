package com.example.orders.orders.domain.rules;

import com.example.orders.orders.domain.OrderStatus;

import java.util.Map;
import java.util.Set;

public final class OrderStatusTransitionPolicy {
  private static final Map<OrderStatus, Set<OrderStatus>> ALLOWED = Map.of(
      OrderStatus.NEW, Set.of(OrderStatus.IN_PROGRESS, OrderStatus.CANCELLED),
      OrderStatus.IN_PROGRESS, Set.of(OrderStatus.COMPLETED, OrderStatus.CANCELLED),
      OrderStatus.COMPLETED, Set.of(),
      OrderStatus.CANCELLED, Set.of()
  );

  public static boolean canTransition(OrderStatus from, OrderStatus to) {
    return ALLOWED.getOrDefault(from, Set.of()).contains(to);
  }

  private OrderStatusTransitionPolicy() {}
}

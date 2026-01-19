package com.example.shop.orders.application.usecases;

import com.example.shop.orders.application.ports.CatalogQueryPort;
import com.example.shop.orders.application.ports.OrderRepositoryPort;
import com.example.shop.orders.domain.Order;
import com.example.shop.orders.domain.OrderItem;

import java.util.List;
import java.util.UUID;

public class CreateOrderUseCase {
  private final CatalogQueryPort catalog;
  private final OrderRepositoryPort repo;

  public CreateOrderUseCase(CatalogQueryPort catalog, OrderRepositoryPort repo) {
    this.catalog = catalog;
    this.repo = repo;
  }

  public Order execute(List<CreateItem> items) {
    if (items == null || items.isEmpty()) {
      throw new IllegalArgumentException("Order must have at least 1 item");
    }

    var orderItems = items.stream().map(i -> {
      var product = catalog.getProduct(i.productId());
      return new OrderItem(product.id(), i.quantity(), product.price());
    }).toList();

    var order = Order.newOrder(orderItems);
    return repo.save(order);
  }

  public record CreateItem(UUID productId, int quantity) {}
}

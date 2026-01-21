package com.example.shop.orders.application.ports;

import java.math.BigDecimal;
import java.util.UUID;

public interface CatalogQueryPort {
  ProductSnapshot getProduct(UUID productId);

  record ProductSnapshot(UUID id, String name, BigDecimal price) {}
}

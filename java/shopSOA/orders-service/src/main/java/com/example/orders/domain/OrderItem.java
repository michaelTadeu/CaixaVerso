package com.example.orders.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderItem {
  private final UUID productId;
  private final int quantity;

  // Snapshot do preço no momento da compra (crítico para SOA/micro)
  private final BigDecimal unitPrice;

  public OrderItem(UUID productId, int quantity, BigDecimal unitPrice) {
    if (productId == null)
      throw new IllegalArgumentException("productId is required");
    if (quantity <= 0)
      throw new IllegalArgumentException("quantity must be > 0");
    if (unitPrice == null || unitPrice.signum() < 0)
      throw new IllegalArgumentException("unitPrice must be >= 0");
    this.productId = productId;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
  }

  public UUID getProductId() {
    return productId;
  }

  public int getQuantity() {
    return quantity;
  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  public BigDecimal subtotal() {
    return unitPrice.multiply(BigDecimal.valueOf(quantity));
  }
}

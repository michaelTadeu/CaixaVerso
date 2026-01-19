package com.example.shop.orders.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderItem {
  private final UUID productId;
  private final int quantity;
  private final BigDecimal unitPrice;

  public OrderItem(UUID productId, int quantity, BigDecimal unitPrice) {
    if (quantity <= 0) throw new IllegalArgumentException("quantity must be > 0");
    this.productId = productId;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
  }

  public UUID getProductId() { return productId; }
  public int getQuantity() { return quantity; }
  public BigDecimal getUnitPrice() { return unitPrice; }

  public BigDecimal subtotal() {
    return unitPrice.multiply(BigDecimal.valueOf(quantity));
  }
}

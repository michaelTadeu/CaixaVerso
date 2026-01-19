package com.example.shop.orders.adapters.outbound.persistence;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "orders_items")
public class JpaOrderItemEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "order_id")
  private JpaOrderEntity order;

  @Column(nullable = false)
  private UUID productId;

  @Column(nullable = false)
  private int quantity;

  @Column(nullable = false, precision = 19, scale = 2)
  private BigDecimal unitPrice;

  protected JpaOrderItemEntity() {}

  public JpaOrderItemEntity(JpaOrderEntity order, UUID productId, int quantity, BigDecimal unitPrice) {
    this.order = order;
    this.productId = productId;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
  }

  public UUID getId() { return id; }
  public JpaOrderEntity getOrder() { return order; }
  public UUID getProductId() { return productId; }
  public int getQuantity() { return quantity; }
  public BigDecimal getUnitPrice() { return unitPrice; }
}

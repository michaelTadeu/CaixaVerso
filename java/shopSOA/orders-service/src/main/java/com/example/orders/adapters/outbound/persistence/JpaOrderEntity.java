package com.example.orders.orders.adapters.outbound.persistence;

import com.example.orders.orders.domain.OrderStatus;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders_orders")
public class JpaOrderEntity {
  @Id
  private UUID id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private OrderStatus status;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<JpaOrderItemEntity> items = new ArrayList<>();

  protected JpaOrderEntity() {}

  public JpaOrderEntity(UUID id, OrderStatus status) {
    this.id = id;
    this.status = status;
  }

  public UUID getId() { return id; }
  public OrderStatus getStatus() { return status; }
  public void setStatus(OrderStatus status) { this.status = status; }
  public List<JpaOrderItemEntity> getItems() { return items; }
}

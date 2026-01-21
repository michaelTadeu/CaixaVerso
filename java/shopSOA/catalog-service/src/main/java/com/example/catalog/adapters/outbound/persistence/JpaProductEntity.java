package com.example.shop.catalog.adapters.outbound.persistence;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "catalog_products")
public class JpaProductEntity {
  @Id
  private UUID id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, precision = 19, scale = 2)
  private BigDecimal price;

  protected JpaProductEntity() {}

  public JpaProductEntity(UUID id, String name, BigDecimal price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  public UUID getId() { return id; }
  public String getName() { return name; }
  public BigDecimal getPrice() { return price; }
}

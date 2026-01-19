package com.example.shop.catalog.adapters.outbound.persistence;

import com.example.shop.catalog.application.ports.ProductRepositoryPort;
import com.example.shop.catalog.domain.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductRepositoryJpaAdapter implements ProductRepositoryPort {
  private final SpringDataProductRepository jpa;

  public ProductRepositoryJpaAdapter(SpringDataProductRepository jpa) {
    this.jpa = jpa;
  }

  @Override
  public Optional<Product> findById(UUID id) {
    return jpa.findById(id).map(e -> new Product(e.getId(), e.getName(), e.getPrice()));
  }

  @Override
  public List<Product> findAll() {
    return jpa.findAll().stream()
        .map(e -> new Product(e.getId(), e.getName(), e.getPrice()))
        .toList();
  }
}

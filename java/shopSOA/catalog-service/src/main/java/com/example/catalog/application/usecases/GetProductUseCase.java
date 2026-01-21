package com.example.catalog.application.usecases;

import com.example.catalog.application.ports.ProductRepositoryPort;
import com.example.catalog.domain.Product;
import com.example.shared.errors.NotFoundException;

import java.util.UUID;

public class GetProductUseCase {
  private final ProductRepositoryPort repo;

  public GetProductUseCase(ProductRepositoryPort repo) {
    this.repo = repo;
  }

  public Product execute(UUID id) {
    return repo.findById(id).orElseThrow(() -> new NotFoundException("Product not found: " + id));
  }
}

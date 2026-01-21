package com.example.catalog.application.usecases;

import com.example.catalog.application.ports.ProductRepositoryPort;
import com.example.catalog.domain.Product;

import java.util.List;

public class ListProductsUseCase {
  private final ProductRepositoryPort repo;

  public ListProductsUseCase(ProductRepositoryPort repo) {
    this.repo = repo;
  }

  public List<Product> execute() {
    return repo.findAll();
  }
}

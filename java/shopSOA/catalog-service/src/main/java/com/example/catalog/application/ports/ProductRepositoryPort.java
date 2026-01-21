package com.example.catalog.application.ports;

import com.example.catalog.domain.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepositoryPort {
  Optional<Product> findById(UUID id);
  List<Product> findAll();
}

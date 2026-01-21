package com.example.catalog.adapters.inbound.rest;

import com.example.catalog.application.usecases.GetProductUseCase;
import com.example.catalog.application.usecases.ListProductsUseCase;
import com.example.catalog.domain.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/catalog/products")
public class CatalogController {
  private final ListProductsUseCase listProducts;
  private final GetProductUseCase getProduct;

  public CatalogController(ListProductsUseCase listProducts, GetProductUseCase getProduct) {
    this.listProducts = listProducts;
    this.getProduct = getProduct;
  }

  @GetMapping
  public List<Product> list() {
    return listProducts.execute();
  }

  @GetMapping("/{id}")
  public Product get(@PathVariable UUID id) {
    return getProduct.execute(id);
  }
}

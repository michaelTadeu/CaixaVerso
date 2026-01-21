package com.example.catalog.config;

import com.example.catalog.adapters.outbound.persistence.ProductRepositoryJpaAdapter;
import com.example.catalog.adapters.outbound.persistence.SpringDataProductRepository;
import com.example.catalog.application.ports.ProductRepositoryPort;
import com.example.catalog.application.usecases.GetProductUseCase;
import com.example.catalog.application.usecases.ListProductsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CatalogBeansConfig {

  @Bean
  ProductRepositoryPort productRepositoryPort(SpringDataProductRepository jpa) {
    return new ProductRepositoryJpaAdapter(jpa);
  }

  @Bean
  GetProductUseCase getProductUseCase(ProductRepositoryPort repo) {
    return new GetProductUseCase(repo);
  }

  @Bean
  ListProductsUseCase listProductsUseCase(ProductRepositoryPort repo) {
    return new ListProductsUseCase(repo);
  }
}

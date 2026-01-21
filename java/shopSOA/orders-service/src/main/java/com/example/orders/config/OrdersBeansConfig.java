package com.example.orders.orders.config;

import com.example.orders.orders.adapters.outbound.persistence.OrderRepositoryJpaAdapter;
import com.example.orders.orders.adapters.outbound.persistence.SpringDataOrderRepository;
import com.example.orders.orders.application.ports.CatalogQueryPort;
import com.example.orders.orders.application.ports.OrderRepositoryPort;
import com.example.orders.orders.application.usecases.ChangeOrderStatusUseCase;
import com.example.orders.orders.application.usecases.CreateOrderUseCase;
import com.example.orders.orders.application.usecases.GetOrderUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.UUID;

@Configuration
public class OrdersBeansConfig {

  @Bean
  OrderRepositoryPort orderRepositoryPort(SpringDataOrderRepository jpa) {
    return new OrderRepositoryJpaAdapter(jpa);
  }

  @Bean
  RestClient restClient() {
    return RestClient.create();
  }

  /**
   * SOA Adapter: Orders chama Catalog por HTTP.
   * A porta (interface) não muda; só muda a implementação.
   */
  @Bean
  CatalogQueryPort catalogQueryPort(RestClient restClient) {
    return productId -> restClient.get()
        .uri("http://localhost:8081/catalog/products/{id}", productId)
        .retrieve()
        .body(CatalogProductResponse.class)
        .toSnapshot();
  }

  record CatalogProductResponse(UUID id, String name, BigDecimal price) {
    CatalogQueryPort.ProductSnapshot toSnapshot() {
      return new CatalogQueryPort.ProductSnapshot(id, name, price);
    }
  }

  @Bean
  CreateOrderUseCase createOrderUseCase(CatalogQueryPort catalog, OrderRepositoryPort repo) {
    return new CreateOrderUseCase(catalog, repo);
  }

  @Bean
  GetOrderUseCase getOrderUseCase(OrderRepositoryPort repo) {
    return new GetOrderUseCase(repo);
  }

  @Bean
  ChangeOrderStatusUseCase changeOrderStatusUseCase(OrderRepositoryPort repo) {
    return new ChangeOrderStatusUseCase(repo);
  }
}

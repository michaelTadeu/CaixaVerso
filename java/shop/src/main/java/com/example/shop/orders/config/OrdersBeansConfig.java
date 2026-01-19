package com.example.shop.orders.config;

import com.example.shop.catalog.application.usecases.GetProductUseCase;
import com.example.shop.orders.application.ports.CatalogQueryPort;
import com.example.shop.orders.application.ports.OrderRepositoryPort;
import com.example.shop.orders.adapters.outbound.persistence.OrderRepositoryJpaAdapter;
import com.example.shop.orders.adapters.outbound.persistence.SpringDataOrderRepository;
import com.example.shop.orders.application.usecases.ChangeOrderStatusUseCase;
import com.example.shop.orders.application.usecases.CreateOrderUseCase;
import com.example.shop.orders.application.usecases.GetOrderUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrdersBeansConfig {

  @Bean
  OrderRepositoryPort orderRepositoryPort(SpringDataOrderRepository jpa) {
    return new OrderRepositoryJpaAdapter(jpa);
  }

  /**
   * Adapter interno (MONÓLITO):
   * Implementa a porta de consulta ao catálogo chamando o caso de uso de Catalog internamente.
   * Na migração para SOA, este Bean vira um HTTP client mantendo a mesma interface.
   */
  @Bean
  CatalogQueryPort catalogQueryPort(GetProductUseCase getProductUseCase) {
    return productId -> {
      var p = getProductUseCase.execute(productId);
      return new CatalogQueryPort.ProductSnapshot(p.id(), p.name(), p.price());
    };
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

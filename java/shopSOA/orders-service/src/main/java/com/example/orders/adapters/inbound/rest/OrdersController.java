package com.example.orders.orders.adapters.inbound.rest;

import com.example.orders.orders.application.usecases.ChangeOrderStatusUseCase;
import com.example.orders.orders.application.usecases.CreateOrderUseCase;
import com.example.orders.orders.application.usecases.GetOrderUseCase;
import com.example.orders.orders.domain.Order;
import com.example.orders.orders.domain.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrdersController {

  private final CreateOrderUseCase create;
  private final GetOrderUseCase get;
  private final ChangeOrderStatusUseCase changeStatus;

  public OrdersController(CreateOrderUseCase create, GetOrderUseCase get, ChangeOrderStatusUseCase changeStatus) {
    this.create = create;
    this.get = get;
    this.changeStatus = changeStatus;
  }

  @PostMapping
  public ResponseEntity<CreateOrderResponse> create(@Valid @RequestBody CreateOrderRequest req) {
    var created = create.execute(req.items().stream()
        .map(i -> new CreateOrderUseCase.CreateItem(i.productId(), i.quantity()))
        .toList());

    return ResponseEntity.status(201)
        .body(new CreateOrderResponse(created.getId(), created.getStatus(), created.total()));
  }

  @GetMapping("/{id}")
  public Order get(@PathVariable UUID id) {
    return get.execute(id);
  }

  @PutMapping("/{id}/status")
  public ResponseEntity<Void> changeStatus(@PathVariable UUID id, @Valid @RequestBody ChangeStatusRequest req) {
    changeStatus.execute(id, OrderStatus.valueOf(req.newStatus()));
    return ResponseEntity.noContent().build();
  }

  public record CreateOrderRequest(@NotEmpty List<CreateItem> items) {}
  public record CreateItem(@NotNull UUID productId, @Min(1) int quantity) {}
  public record CreateOrderResponse(UUID id, OrderStatus status, BigDecimal total) {}
  public record ChangeStatusRequest(@NotNull String newStatus) {}
}

package com.demo.api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.time.Instant;
import java.util.Map;

@RestController
public class ApiController {

  @Value("${INSTANCE_ID:local}")
  private String instanceId;

  @GetMapping("/health")
  public Map<String, Object> health() {
    return Map.of("status", "ok", "component", "api");
  }

  @GetMapping("/hello")
  public Map<String, Object> hello() throws Exception {
    return Map.of(
        "message", "Olá! Esta é a API Spring Boot.",
        "instanceId", instanceId,
        "hostname", InetAddress.getLocalHost().getHostName(),
        "now", Instant.now().toString()
    );
  }
}

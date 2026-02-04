package com.exercise.worker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class WorkerHealthController {
  @GetMapping("/health")
  public Map<String, Object> health() {
    return Map.of("status", "ok", "service", "worker");
  }
}

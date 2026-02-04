package com.exercise.gateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@RestController
public class GatewayController {

  private final RestTemplate rest = new RestTemplate();

  @Value("${gateway.apiBaseUrl}")
  private String apiBaseUrl;

  @GetMapping("/health")
  public Map<String, Object> health() {
    return Map.of("status", "ok", "service", "gateway");
  }

  // Proxy genérico para a minha API /api/
  @RequestMapping(
      value = "/api/**",
      method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE}
  )
  public ResponseEntity<byte[]> proxyApi(HttpServletRequest request) throws Exception {
    String path = request.getRequestURI();
    String query = request.getQueryString();
    String targetUrl = apiBaseUrl + path + (query != null ? ("?" + query) : "");

    HttpHeaders headers = new HttpHeaders();
    Collections.list(request.getHeaderNames()).forEach(h -> {
      if (!h.equalsIgnoreCase("host")) {
        headers.add(h, request.getHeader(h));
      }
    });

    headers.add("X-Gateway", "java-gateway");
    headers.add("X-Forwarded-For", request.getRemoteAddr());

    byte[] body = StreamUtils.copyToByteArray(request.getInputStream());

    HttpMethod method = HttpMethod.valueOf(request.getMethod());
    HttpEntity<byte[]> entity = new HttpEntity<>(body.length == 0 ? null : body, headers);

    long start = System.currentTimeMillis();
    ResponseEntity<byte[]> resp = rest.exchange(targetUrl, method, entity, byte[].class);
    long ms = System.currentTimeMillis() - start;

    System.out.println("[gateway] " + request.getMethod() + " " + request.getRequestURI() +
        " -> " + targetUrl + " (" + ms + "ms) status=" + resp.getStatusCode());

    return ResponseEntity.status(resp.getStatusCode())
        .headers(resp.getHeaders())
        .body(resp.getBody());
  }
}

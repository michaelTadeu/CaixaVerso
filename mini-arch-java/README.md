# Mini arquitetura (Java/Spring) — sem Docker e sem Nginx

Serviços:
- gateway: http://localhost:8080
- api: http://localhost:3000
- worker: http://localhost:3001

Persistência local (simulando volume):
- data/inbox/events.ndjson
- data/inbox/offset.txt
- data/out/stats.json

## Rodar (3 terminais)
### 1) API
```bash
cd api
mvn spring-boot:run
```

### 2) Worker
```bash
cd worker
mvn spring-boot:run
```

### 3) Gateway
```bash
cd gateway
mvn spring-boot:run
```

## Testes via gateway
Health:
```bash
curl http://localhost:8080/health
```

Enviar evento:
```bash
curl -X POST http://localhost:8080/api/events -H "Content-Type: application/json" -d '{"type":"click","source":"web"}'
```

Stats:
```bash
curl http://localhost:8080/api/stats
```

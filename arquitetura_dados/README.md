# Data Architecture Livecode (Spring Boot) — API + Worker + DB

This repository demonstrates a production-style *data platform slice* using **Spring Boot**:

- **worker**: produces + persists purchase events into Postgres (simulating ingestion)
- **api**: exposes read endpoints (simulating data consumption / serving layer)
- **postgres**: storage

## Prereqs
- Docker + Docker Compose

## Run
```bash
docker compose up --build
```

## Test
```bash
bash scripts/test.sh
```

## Stop
```bash
docker compose down -v
```

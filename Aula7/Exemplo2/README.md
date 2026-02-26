# DW Simulado

Mini projeto para ensinar **Data Warehouse** na prática:

- **OLTP** (transacional) em `schema oltp`
- **DW** (analítico, estrela) em `schema dw` (dimensões + fato)
- **Worker** executa um ETL simples (upsert)
- **API** expõe consultas OLAP (GROUP BY / ROLLUP)

## Subir com Docker
```bash
docker compose up --build
```

## Testar
Health:
```bash
curl -s http://localhost:8080/health
```

Rodar ETL (job):
```bash
curl -X POST http://localhost:8081/jobs/etl/run
```

Consultas OLAP:
```bash
curl -s http://localhost:8080/olap/sales/by-customer
curl -s http://localhost:8080/olap/sales/by-day-channel
curl -s http://localhost:8080/olap/sales/by-category-rollup
```

## Portas
- API: `8080`
- Worker: `8081`
- Postgres: `5435` (host) -> `5432` (container)

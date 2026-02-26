# Mini projeto SQL — Data Warehouse

Objetivo: demonstrar rapidamente **tabela dimensão**, **tabela fato** e uma **query OLAP com GROUP BY** (inclui variações com ROLLUP/CUBE).

## Requisitos
- Docker + Docker Compose

## Como rodar
```bash
docker compose up -d
```

Abra um terminal dentro do container (ou use psql local):
```bash
docker exec -it mini-dw-postgres psql -U dw -d dw_demo
```

Execute os scripts na ordem:
```sql
\i /scripts/01_oltp_schema.sql
\i /scripts/02_dw_schema.sql
\i /scripts/03_seed.sql
\i /scripts/04_etl.sql
\i /scripts/05_olap_queries.sql
```

## Estrutura
- `scripts/01_oltp_schema.sql`: tabelas transacionais (OLTP)
- `scripts/02_dw_schema.sql`: dimensões + fato (OLAP)
- `scripts/03_seed.sql`: dados de exemplo
- `scripts/04_etl.sql`: ETL/ELT simplificado (INSERT ... SELECT)
- `scripts/05_olap_queries.sql`: consultas analíticas (GROUP BY / ROLLUP / CUBE)

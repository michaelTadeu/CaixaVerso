# Nginx + Spring Boot (Live Code por etapas)

Este repositório foi criado para uma aula de Nginx com evolução incremental.

## Branches
- `main` (etapa 1): Nginx básico (estático)
- `reverse-proxy` (etapa 2): adiciona API Spring Boot e Nginx como reverse proxy em `/api`

## Rodar (etapa 1 - main)
```bash
docker compose up
```

Acesse:
- http://localhost:8080
- http://localhost:8080/health

# Projeto: API Spring Boot com Nginx (Reverse Proxy)

## 1) Objetivo
Evoluir a API Spring Boot adicionando Nginx como reverse proxy para melhorar:
- Segurança (headers)
- Controle de tráfego (rate limiting)
- Restrições de payload
- Performance (gzip)
- Observabilidade (logs)

---

## 2) Arquitetura
**Fluxo:**
Cliente -> Nginx (porta 80) -> Spring Boot (porta 8080)

**Cenário usado neste projeto:**  
- [ ] A) Com Docker  
- [ ] B) Sem Docker

**Portas:**
- Nginx: 80
- API: 8080 (interna)

---

## 3) Como executar

### 3.1 Cenário A — Com Docker
**Pré-requisitos:** Docker e Docker Compose

1. Garantir que o arquivo `./app/app.jar` existe.
2. Subir os containers:
   ```bash
   docker compose up --build

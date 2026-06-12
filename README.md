# 🗂️ Taskboard Platform — Backend

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white"/>
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"/>
  <img src="https://img.shields.io/badge/Prometheus-E6522C?style=for-the-badge&logo=prometheus&logoColor=white"/>
  <img src="https://img.shields.io/badge/Grafana-F46800?style=for-the-badge&logo=grafana&logoColor=white"/>
</p>

---

## 📌 Cos'è questo progetto

**Taskboard Platform Backend** è il cuore di una piattaforma di gestione task e progetti basata su **architettura a microservizi**. Il sistema permette agli utenti di creare progetti, gestire task, monitorare le attività tramite analytics e accedere in sicurezza tramite autenticazione JWT.

Il progetto nasce come percorso di studio e approfondimento personale sull'architettura a microservizi con Spring Boot, con focus su:
- Comunicazione tra microservizi tramite API Gateway
- Autenticazione e autorizzazione centralizzata con JWT
- Containerizzazione e orchestrazione con Docker
- Observability con Prometheus e Grafana

---

## 🎯 A cosa è pensato

Questo progetto è pensato per chi vuole esplorare concretamente come si costruisce un backend moderno e scalabile basato su microservizi. È un progetto di studio avanzato che replica pattern reali usati in ambienti enterprise, con:
- Separazione delle responsabilità per dominio (auth, progetti, analytics)
- Un gateway centralizzato come unico punto di ingresso
- Monitoring integrato fin dall'inizio

---

## 🏗️ Architettura

```
                        ┌─────────────────────────────────┐
                        │         CLIENT (Angular)         │
                        └────────────────┬────────────────┘
                                         │ HTTP :8080
                        ┌────────────────▼────────────────┐
                        │         GATEWAY SERVICE          │
                        │    Spring Cloud Gateway :8080    │
                        └──────┬────────────┬─────────────┘
                               │            │            │
               ┌───────────────▼──┐  ┌──────▼──────┐  ┌──▼───────────────┐
               │   AUTH SERVICE   │  │PROJECT SVC  │  │ ANALYTICS SERVICE │
               │   JWT + Spring   │  │Spring Boot  │  │   Spring Boot     │
               │   Security :8081 │  │   :8082     │  │      :8083        │
               └────────┬─────────┘  └──────┬──────┘  └────────┬──────────┘
                        │                   │                   │
               ┌────────▼───────────────────▼───────────────────▼──────────┐
               │                      MongoDB                               │
               │  taskboard-auth  /  taskboard-project  /  taskboard-analytics │
               └────────────────────────────────────────────────────────────┘

               ┌──────────────────────────────────────────────────────────┐
               │           MONITORING STACK                               │
               │  Prometheus :9090  ←→  Grafana :3000                    │
               └──────────────────────────────────────────────────────────┘
```

---

## ⚙️ Microservizi

### 🔐 Auth Service (`:8081`)
Gestisce registrazione, login e validazione dei token JWT.
- Spring Security + JJWT 0.12.5
- MongoDB per la persistenza degli utenti
- Endpoint `/actuator/health` per healthcheck
- Metriche esposte via Micrometer → Prometheus

### 📁 Project Service (`:8082`)
Gestisce la creazione e il ciclo di vita dei progetti e dei task.
- CRUD completo su progetti e task
- Validazione del token JWT tramite chiamata interna all'auth-service
- Database separato su MongoDB (`taskboard-project`)

### 📊 Analytics Service (`:8083`)
Raccoglie e restituisce dati aggregati sull'utilizzo della piattaforma.
- Statistiche su task e progetti
- Accesso protetto tramite validazione JWT
- Database separato su MongoDB (`taskboard-analytics`)

### 🌐 Gateway Service (`:8080`)
Unico punto di ingresso per tutti i client.
- Routing verso i servizi interni
- Gestione CORS centralizzata
- Spring Cloud Gateway

---

## 🛠️ Stack Tecnico

| Componente | Tecnologia |
|---|---|
| Linguaggio | Java 21 |
| Framework | Spring Boot 3.x |
| API Gateway | Spring Cloud Gateway |
| Sicurezza | Spring Security + JWT (JJWT 0.12.5) |
| Database | MongoDB (istanza condivisa, DB separati) |
| Containerizzazione | Docker + Docker Compose |
| Build | Maven (multi-module) |
| Monitoring | Prometheus + Grafana |
| Metriche | Micrometer + Spring Actuator |
| Boilerplate | Lombok |

---

## 🐳 Come avviarlo

### Prerequisiti
- Docker Desktop installato e avviato
- Porte `8080`, `8081`, `8082`, `8083`, `27017`, `9090`, `3000` libere

### Avvio completo con Docker Compose

```bash
# Clona il repository
git clone https://github.com/Lele97/Taskboard-Platform-Backend.git
cd Taskboard-Platform-Backend

# Build e avvio di tutti i servizi
docker compose up --build
```

Tutti i servizi partono con health check automatici. Il gateway è disponibile su `http://localhost:8080`.

### Build manuale con Maven

```bash
mvn clean install -DskipTests
```

### Accesso al monitoring
| Tool | URL | Credenziali |
|---|---|---|
| Prometheus | `http://localhost:9090` | — |
| Grafana | `http://localhost:3000` | `admin` / `admin` |

---

## 📂 Struttura del progetto

```
taskboard-platform-backend/
├── auth-service/           # Microservizio autenticazione
├── project-service/        # Microservizio gestione progetti
├── analytics-service/      # Microservizio analytics
├── gateway-service/        # API Gateway
├── prometheus/             # Configurazione Prometheus
├── grafana/                # Configurazione Grafana + datasource
├── Dockerfile              # Multi-stage Dockerfile (tutti i servizi)
├── docker-compose.yml      # Orchestrazione locale
├── docker-bake.hcl         # Build parallelo immagini Docker
└── pom.xml                 # POM padre multi-modulo Maven
```

---

## 👤 Autore

**Gabriele Grandinetti**
- 🌐 [gabrielegrandinetti.dev](https://gabrielegrandinetti.dev)
- 💼 [GitHub @Lele97](https://github.com/Lele97)
- 📧 gabriele.grandinetti@hotmail.com

> Progetto realizzato a scopo di studio e approfondimento personale sull'architettura a microservizi con Spring Boot, Docker e monitoring con Prometheus/Grafana.

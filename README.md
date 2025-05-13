# 🧬 Helix Log Ingestion Platform

Helix is a backend-first, AI-augmented platform for ingesting, classifying, and managing system logs in real-time. Built using Spring Boot and integrated with a Flask-based classification engine, it enables intelligent alerting and infrastructure observability.

---

## 🚀 Tech Stack

- **Java 17 / Spring Boot**
- **PostgreSQL 13**
- **Docker & Docker Compose**
- **Flask (Python) for AI classification**
- **OpenAI or rule-based fallback model**

---

## 📁 Project Structure

```
helix-platform/
├── helix-log-ingestion/       # Spring Boot backend
│   └── src/main/java/com/helix/
├── flask-ai/                  # Flask classifier (Python)
├── docker-compose.yml         # Full environment orchestration
├── .gitignore
└── README.md
```

---

## ⚙️ How to Run (Dev)

```bash
# Build and start backend + DB
docker-compose up --build
```

Backend runs at: `http://localhost:8080`  
Flask AI runs at: `http://localhost:5000`

---

## 🔌 Key Endpoints

| Endpoint                 | Method | Description                             |
|--------------------------|--------|-----------------------------------------|
| `/classify-alert`        | POST   | Classify a log message via Flask        |
| `/logs`                  | GET    | Retrieve ingested log entries           |
| `/alerts`                | GET    | Get triggered alerts                    |

---

## 🧠 Classification Flow

1. Tries GPT classifier (`/classify`)
2. If fails, routes to rule-based fallback (`/classify-alert`)
3. Response includes:
   - `classifiedAs`
   - `summary`
   - `recommendation`

---

## 📄 Environment Profiles

- `application-dev.properties`
- `application-prod.properties`
- Use `SPRING_PROFILES_ACTIVE=prod` in Docker env

---

## 🔐 Secrets

Real `.env` file not committed. Use `.env.template` to structure your env vars.

---

## ✨ Contributing (Coming Soon)

- Feature branches: `feature/*`
- Main integration via PR to `dev`
- Production merges to `main`

---

## 📌 Author

**Rakesh Singh**  
`MarsAscent Technologies`

---

> 💬 Let's build intelligent infrastructure — one alert at a time.

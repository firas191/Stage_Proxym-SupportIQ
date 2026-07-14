# SupportIQ — Plateforme Agentique d'Analyse & de Résolution des Tickets de Support

Monorepo du projet de stage. Voir `docs/` pour l'architecture et les ADRs.

## Structure
```
frontend/     Angular 18 SPA (dashboard, tickets, chat Insight)
backend/      Spring Boot 3 — API REST, auth JWT, ingestion, orchestration
ai-service/   FastAPI — pipeline NLP hybride + agents LangGraph
infra/        Scripts d'infrastructure (init PostgreSQL, etc.)
eval/         Datasets gelés + harness d'évaluation IA
docs/         Architecture, ADRs, guides
```

## Démarrage rapide (infra + service IA)
```bash
cp .env.example .env        # renseigner les clés API (Groq, Gemini...)
docker compose up -d postgres rabbitmq
docker compose up ai-service
# health check :
curl http://localhost:8001/health
```

## Générer le backend (jour 2)
Voir `backend/README.md` — Spring Initializr avec la liste exacte des dépendances.

## Générer le frontend (jour 4)
Voir `frontend/README.md` — Angular CLI + dépendances.

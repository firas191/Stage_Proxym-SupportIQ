# ADR-0001 — Architecture générale : plan de contrôle / plan de calcul

Date : S1-J1 · Statut : accepté

## Contexte
Stack imposée : Angular, Spring Boot, PostgreSQL, Python/FastAPI. Besoin d'analyse
IA asynchrone sur des volumes d'import importants (10k+ tickets par CSV).

## Décision
- Spring Boot = plan de contrôle : sécurité, transactions, règles métier, orchestration.
- FastAPI = plan de calcul : NLP, agents LangGraph, embeddings.
- Communication asynchrone via RabbitMQ (ticket.created / ticket.analyzed),
  appels synchrones REST réservés aux besoins interactifs (brouillon, insight).
- pgvector dans PostgreSQL plutôt qu'un vector store séparé (ACID, jointures, zéro infra en plus).

## Conséquences
+ Découplage, résilience (retry/DLQ), scalabilité du worker IA
+ Un seul système de stockage à opérer
- Complexité MQ à maîtriser (idempotence, acquittements) — couverte en S2-J3

## ADRs suivants à écrire
0002 état frontend (NgRx vs signals) · 0003 stratégie de fine-tuning vs baseline ·
0004 seuil du routeur de confiance · 0005 retrieval hybride RRF · 0006 guardrails Text-to-SQL

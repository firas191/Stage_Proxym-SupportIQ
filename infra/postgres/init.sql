-- Exécuté au premier démarrage du conteneur PostgreSQL
CREATE EXTENSION IF NOT EXISTS vector;
CREATE EXTENSION IF NOT EXISTS pg_trgm;   -- recherche trigram (fallback)
CREATE EXTENSION IF NOT EXISTS unaccent;  -- normalisation accents FR

-- Rôle read-only dédié à l'agent Insight (Text-to-SQL) — durci en semaine 6
-- CREATE ROLE insight_ro LOGIN PASSWORD '...';
-- GRANT SELECT ON v_ticket_stats, v_category_trends TO insight_ro;

# Harness d'évaluation IA

- `datasets/train.jsonl` — dataset d'entraînement (synthétique + public), versionné hors Git (trop lourd)
- `datasets/test.jsonl` — **test set gelé** : ~300 tickets étiquetés à la main en S2-J5.
  Règle absolue : jamais utilisé en entraînement, jamais modifié après gel.

## Suites (ajoutées au fil des semaines)
1. S3-J5  Classification : precision / recall / F1 par classe — local vs LLM vs hybride
2. S5-J2  Retrieval : recall@5 sur 40 paires question/chunk annotées
3. S5-J5  Brouillons RAG : LLM-as-judge (exactitude, complétude, ton) sur 50 brouillons
4. S6-J2  Text-to-SQL : 30 questions avec SQL de référence, comparaison des résultats

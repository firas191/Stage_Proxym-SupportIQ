#!/usr/bin/env python3
"""Genere un CSV de tickets synthetiques (FR/EN) pour tester l'import 'sans OOM' (S2-J1).

Usage : python scripts/generate_sample_csv.py [n_rows] [out_path]
Defaut : 10000 lignes -> samples/tickets_10k.csv
"""
import csv
import random
import sys
from datetime import datetime, timedelta

SUBJECTS_FR = ["Probleme de connexion", "Facture incorrecte", "Remboursement demande",
               "Application qui plante", "Mot de passe oublie", "Livraison en retard"]
SUBJECTS_EN = ["Login issue", "Wrong invoice", "Refund request",
               "App keeps crashing", "Forgot password", "Late delivery"]
BODIES_FR = ["Bonjour, je n'arrive pas a acceder a mon compte depuis ce matin.",
             "Ma derniere facture ne correspond pas a mon abonnement.",
             "Je souhaite etre rembourse suite a une erreur de commande."]
BODIES_EN = ["Hi, I cannot access my account since this morning.",
             "My last invoice does not match my subscription.",
             "I would like a refund following an order mistake."]


def main() -> None:
    n = int(sys.argv[1]) if len(sys.argv) > 1 else 10000
    out = sys.argv[2] if len(sys.argv) > 2 else "samples/tickets_10k.csv"
    start = datetime(2026, 1, 1)
    with open(out, "w", newline="", encoding="utf-8") as f:
        w = csv.writer(f)
        w.writerow(["external_ref", "customer_email", "subject", "body", "created_at", "language"])
        for i in range(1, n + 1):
            lang = random.choice(["fr", "en"])
            subj = random.choice(SUBJECTS_FR if lang == "fr" else SUBJECTS_EN)
            body = random.choice(BODIES_FR if lang == "fr" else BODIES_EN)
            ts = (start + timedelta(minutes=i)).isoformat()
            w.writerow([f"TCK-{i:06d}", f"client{i}@example.com", subj, body, ts, lang])
    print(f"{n} lignes ecrites -> {out}")


if __name__ == "__main__":
    main()

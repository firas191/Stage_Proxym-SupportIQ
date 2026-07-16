package com.supportiq.backend.imports;

/**
 * Cycle de vie d'un import (rapport §4). Au J1, un fichier structure parse s'arrete a
 * AWAITING_VALIDATION : l'utilisateur validera le mapping (J2) avant l'insertion des tickets.
 */
public enum ImportStatus {
    PENDING,
    EXTRACTING,
    AWAITING_VALIDATION,
    PROCESSING,
    DONE,
    FAILED
}

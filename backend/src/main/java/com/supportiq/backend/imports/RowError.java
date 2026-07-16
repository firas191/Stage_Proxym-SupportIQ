package com.supportiq.backend.imports;

/** Erreur de validation structurelle sur une ligne (numero 1-based). */
public record RowError(int line, String message) {
}

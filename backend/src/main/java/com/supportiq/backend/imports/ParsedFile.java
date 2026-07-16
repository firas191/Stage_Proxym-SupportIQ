package com.supportiq.backend.imports;

import java.util.List;

/**
 * Resultat de parsing d'un fichier structure, produit en streaming :
 * - headers : en-tetes de colonnes ;
 * - previewRows : premieres lignes (bornees) pour l'apercu ;
 * - totalRows : nombre total de lignes de donnees (hors en-tete) ;
 * - errors : erreurs structurelles bornees (ex. nombre de colonnes incoherent).
 */
public record ParsedFile(
        List<String> headers,
        List<List<String>> previewRows,
        int totalRows,
        List<RowError> errors) {

    public static final int PREVIEW_LIMIT = 50;
    public static final int MAX_ERRORS = 100;
}

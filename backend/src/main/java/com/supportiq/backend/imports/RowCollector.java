package com.supportiq.backend.imports;

import java.util.ArrayList;
import java.util.List;

/**
 * Accumulateur commun aux parseurs : compte les lignes, borne l'apercu et le rapport d'erreurs,
 * et signale les lignes dont le nombre de colonnes differe de l'en-tete. Memoire bornee (pas d'OOM).
 */
final class RowCollector {

    private final int expectedColumns;
    private final List<List<String>> preview = new ArrayList<>();
    private final List<RowError> errors = new ArrayList<>();
    private int total = 0;

    RowCollector(int expectedColumns) {
        this.expectedColumns = expectedColumns;
    }

    void add(List<String> row, int lineNumber) {
        total++;
        if (row.size() != expectedColumns && errors.size() < ParsedFile.MAX_ERRORS) {
            errors.add(new RowError(lineNumber,
                    "Nombre de colonnes incoherent (attendu " + expectedColumns
                            + ", obtenu " + row.size() + ")"));
        }
        if (preview.size() < ParsedFile.PREVIEW_LIMIT) {
            preview.add(row);
        }
    }

    ParsedFile toParsedFile(List<String> headers) {
        return new ParsedFile(headers, preview, total, errors);
    }
}

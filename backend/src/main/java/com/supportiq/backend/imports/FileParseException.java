package com.supportiq.backend.imports;

/** Fichier structurellement illisible (CSV/XLSX/JSON corrompu) -> 400. */
public class FileParseException extends RuntimeException {

    public FileParseException(String message, Throwable cause) {
        super(message, cause);
    }
}

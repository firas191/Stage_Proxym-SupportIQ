package com.supportiq.backend.imports;

/** Format de fichier non pris en charge -> 415. */
public class UnsupportedFileTypeException extends RuntimeException {

    public UnsupportedFileTypeException(String filename) {
        super("Format de fichier non supporte : " + filename);
    }
}

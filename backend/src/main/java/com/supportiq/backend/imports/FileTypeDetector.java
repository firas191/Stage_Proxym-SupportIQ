package com.supportiq.backend.imports;

import java.util.Locale;
import org.springframework.stereotype.Component;

/**
 * Detection du type de fichier : signature (magic bytes) d'abord, extension en complement.
 * Approche volontairement legere (pas de dependance Tika) : les formats vises ici sont simples.
 */
@Component
public class FileTypeDetector {

    public FileType detect(String filename, byte[] head) {
        // XLSX et DOCX sont des archives ZIP (signature "PK\x03\x04") : on tranche par extension.
        if (head.length >= 4 && head[0] == 'P' && head[1] == 'K' && head[2] == 0x03 && head[3] == 0x04) {
            return "docx".equals(extension(filename)) ? FileType.DOCX : FileType.XLSX;
        }
        // %PDF signature
        if (head.length >= 4 && head[0] == '%' && head[1] == 'P' && head[2] == 'D' && head[3] == 'F') {
            return FileType.PDF;
        }
        return switch (extension(filename)) {
            case "csv" -> FileType.CSV;
            case "xlsx" -> FileType.XLSX;
            case "json" -> FileType.JSON;
            case "txt" -> FileType.TXT;
            case "pdf" -> FileType.PDF;
            case "docx" -> FileType.DOCX;
            case "eml" -> FileType.EML;
            default -> throw new UnsupportedFileTypeException(filename);
        };
    }

    private String extension(String filename) {
        if (filename == null) {
            return "";
        }
        int dot = filename.lastIndexOf('.');
        return dot < 0 ? "" : filename.substring(dot + 1).toLowerCase(Locale.ROOT);
    }
}

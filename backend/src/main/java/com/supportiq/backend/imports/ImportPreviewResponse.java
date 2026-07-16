package com.supportiq.backend.imports;

import java.nio.charset.Charset;
import java.util.List;

/** Reponse a un upload : metadonnees de l'import + apercu + rapport d'erreurs (sans persister de tickets). */
public record ImportPreviewResponse(
        Long importId,
        String filename,
        FileType fileType,
        String charset,
        String status,
        int totalRows,
        int errorCount,
        List<String> headers,
        List<List<String>> preview,
        List<RowError> errors) {

    public static ImportPreviewResponse from(ImportJob job, Charset charset, ParsedFile parsed) {
        return new ImportPreviewResponse(
                job.getId(),
                job.getFilename(),
                job.getFileType(),
                charset.name(),
                job.getStatus().name(),
                parsed.totalRows(),
                parsed.errors().size(),
                parsed.headers(),
                parsed.previewRows(),
                parsed.errors());
    }
}

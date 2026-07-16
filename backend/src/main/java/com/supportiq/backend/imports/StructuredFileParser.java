package com.supportiq.backend.imports;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/** Contrat commun des parseurs de fichiers structures. Lecture en streaming (pas de chargement complet). */
public interface StructuredFileParser {

    boolean supports(FileType type);

    /** @param charset ignore pour les formats binaires (XLSX). */
    ParsedFile parse(InputStream input, Charset charset) throws IOException;
}

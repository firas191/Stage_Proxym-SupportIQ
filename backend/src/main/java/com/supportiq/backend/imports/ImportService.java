package com.supportiq.backend.imports;

import com.supportiq.backend.auth.User;
import com.supportiq.backend.auth.UserRepository;
import com.supportiq.backend.common.error.ResourceNotFoundException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Orchestration de l'import structure (S2-J1) :
 * detection type + encodage (sur un echantillon), parsing en streaming, validation ligne a ligne,
 * puis persistance d'une ligne `imports` au statut AWAITING_VALIDATION. Les tickets ne sont PAS
 * inseres ici : le mapping de colonnes et l'insertion viennent au J2.
 */
@Service
public class ImportService {

    private static final int HEAD_SIZE = 64 * 1024;

    private final List<StructuredFileParser> parsers;
    private final FileTypeDetector fileTypeDetector;
    private final CharsetDetector charsetDetector;
    private final ImportJobRepository imports;
    private final UserRepository users;

    public ImportService(List<StructuredFileParser> parsers, FileTypeDetector fileTypeDetector,
            CharsetDetector charsetDetector, ImportJobRepository imports, UserRepository users) {
        this.parsers = parsers;
        this.fileTypeDetector = fileTypeDetector;
        this.charsetDetector = charsetDetector;
        this.imports = imports;
        this.users = users;
    }

    @Transactional
    public ImportPreviewResponse importFile(MultipartFile file, String uploaderEmail) {
        User uploader = users.findByEmail(uploaderEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur courant introuvable"));
        String filename = file.getOriginalFilename() != null ? file.getOriginalFilename() : "sans-nom";

        ParsedFile parsed;
        FileType type;
        Charset charset;
        try {
            BufferedInputStream in = new BufferedInputStream(file.getInputStream());
            in.mark(HEAD_SIZE + 1);
            byte[] head = in.readNBytes(HEAD_SIZE);
            in.reset(); // on rembobine : le parseur relit depuis le debut, en streaming

            type = fileTypeDetector.detect(filename, head);
            charset = charsetDetector.detect(head);
            StructuredFileParser parser = parsers.stream()
                    .filter(p -> p.supports(type))
                    .findFirst()
                    .orElseThrow(() -> new UnsupportedFileTypeException(filename));
            parsed = parser.parse(in, charset);
        } catch (IOException e) {
            throw new FileParseException("Fichier illisible : " + e.getMessage(), e);
        }

        ImportJob job = imports.save(ImportJob.builder()
                .filename(filename)
                .fileType(type)
                .uploadedBy(uploader)
                .rowCount(parsed.totalRows())
                .status(ImportStatus.AWAITING_VALIDATION)
                .build());

        return ImportPreviewResponse.from(job, charset, parsed);
    }
}

package com.supportiq.backend.imports;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

/** CSV en streaming via OpenCSV : lecture ligne a ligne, aucun chargement complet en memoire. */
@Component
public class CsvParser implements StructuredFileParser {

    @Override
    public boolean supports(FileType type) {
        return type == FileType.CSV;
    }

    @Override
    public ParsedFile parse(InputStream input, Charset charset) throws IOException {
        try (CSVReader reader = new CSVReader(new InputStreamReader(input, charset))) {
            String[] headerArr = reader.readNext();
            if (headerArr == null) {
                return new ParsedFile(List.of(), List.of(), 0, List.of());
            }
            List<String> headers = Arrays.asList(headerArr);
            RowCollector collector = new RowCollector(headers.size());
            String[] line;
            int lineNumber = 1; // l'en-tete est la ligne 1 ; les donnees commencent a 2
            while ((line = reader.readNext()) != null) {
                lineNumber++;
                collector.add(Arrays.asList(line), lineNumber);
            }
            return collector.toParsedFile(headers);
        } catch (CsvValidationException e) {
            throw new IOException("CSV illisible : " + e.getMessage(), e);
        }
    }
}

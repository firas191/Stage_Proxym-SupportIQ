package com.supportiq.backend.imports;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

/** TXT tabulaire (separateur tabulation), lu ligne a ligne. Lignes vides ignorees. */
@Component
public class TxtParser implements StructuredFileParser {

    @Override
    public boolean supports(FileType type) {
        return type == FileType.TXT;
    }

    @Override
    public ParsedFile parse(InputStream input, Charset charset) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, charset))) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                return new ParsedFile(List.of(), List.of(), 0, List.of());
            }
            List<String> headers = splitTab(headerLine);
            RowCollector collector = new RowCollector(headers.size());
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isEmpty()) {
                    continue;
                }
                collector.add(splitTab(line), lineNumber);
            }
            return collector.toParsedFile(headers);
        }
    }

    private List<String> splitTab(String s) {
        return Arrays.asList(s.split("\t", -1));
    }
}

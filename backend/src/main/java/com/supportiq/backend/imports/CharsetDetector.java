package com.supportiq.backend.imports;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Component;

/**
 * Detection d'encodage : BOM d'abord, puis validation UTF-8 stricte sur un echantillon,
 * fallback ISO-8859-1 (Latin-1) sinon. Suffisant pour les CSV/TXT FR/EN vises ; une
 * detection statistique (Tika/ICU) pourra la remplacer plus tard si besoin.
 */
@Component
public class CharsetDetector {

    public Charset detect(byte[] head) {
        if (head.length >= 3 && (head[0] & 0xFF) == 0xEF && (head[1] & 0xFF) == 0xBB && (head[2] & 0xFF) == 0xBF) {
            return StandardCharsets.UTF_8;
        }
        if (head.length >= 2 && (head[0] & 0xFF) == 0xFF && (head[1] & 0xFF) == 0xFE) {
            return StandardCharsets.UTF_16LE;
        }
        if (head.length >= 2 && (head[0] & 0xFF) == 0xFE && (head[1] & 0xFF) == 0xFF) {
            return StandardCharsets.UTF_16BE;
        }
        return isValidUtf8(head) ? StandardCharsets.UTF_8 : StandardCharsets.ISO_8859_1;
    }

    private boolean isValidUtf8(byte[] bytes) {
        CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder()
                .onMalformedInput(CodingErrorAction.REPORT)
                .onUnmappableCharacter(CodingErrorAction.REPORT);
        try {
            decoder.decode(ByteBuffer.wrap(bytes));
            return true;
        } catch (CharacterCodingException e) {
            return false; // NB : un multi-octets tronque en fin d'echantillon peut fausser (risque mineur assume)
        }
    }
}

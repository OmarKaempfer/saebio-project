package es.ulpgc.digester.data;

import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

import static es.ulpgc.digester.utils.DocumentStructureParser.*;

public class DocumentStructure {
    public static final String CSV_PATH = "res/Ejemplo estadistica.csv";
    public static final String CSV_PATH_TEST = "test/res/test_cases - copia.csv";
    public static final String ANTIBIOTICS_CSV_PATH = "res/antibiotics.csv";
    public static final String[] HEADERS;
    public static final Map<String, Integer> HEADER_TO_INDEX;
    public static final Set<String> ANTIBIOTICS;
    public static Map<String, Integer> ANTIBIOTIC_TO_INDEX;

    static {
        HEADERS = readHeaders(CSV_PATH);
        HEADER_TO_INDEX = mapHeadersToIndexes(readFirstLine(CSV_PATH));
        ANTIBIOTICS = readAntibiotics(ANTIBIOTICS_CSV_PATH);
        ANTIBIOTIC_TO_INDEX = mapAntibioticsToIndexes(HEADERS);
    }

    public static int indexOf(String header) {
        return HEADER_TO_INDEX.get(header);
    }
}

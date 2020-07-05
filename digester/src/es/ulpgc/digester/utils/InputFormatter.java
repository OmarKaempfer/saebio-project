package es.ulpgc.digester.utils;

import java.text.Normalizer;

public class InputFormatter {
    public static String normalizeText(String line) {
        return Normalizer
                .normalize(line, Normalizer.Form.NFKD)
                .replaceAll("[^\\p{ASCII}]|\\s|\\.", "")
                .toLowerCase();
    }

    public static String formatDate(String[] splitDate) {
        StringBuilder sb = new StringBuilder();
        for (int i = splitDate.length - 1; i > 0; i--) {
            sb.append(splitDate[i]).append("-");
        }
        sb.append(splitDate[0]);
        return sb.toString();
    }
}

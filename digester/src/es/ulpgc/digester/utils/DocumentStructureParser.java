package es.ulpgc.digester.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static es.ulpgc.digester.data.DocumentStructure.*;
import static es.ulpgc.digester.data.DocumentStructure.ANTIBIOTICS;
import static es.ulpgc.digester.utils.InputFormatter.normalizeText;

public class DocumentStructureParser {
    public static Map<String, Integer> mapHeadersToIndexes(String headers) {
        Map<String, Integer> fieldsIndexes = new HashMap<>();
        AtomicInteger index = new AtomicInteger();
        index.getAndSet(0);

        Arrays.stream(headers.split(";", -1))
                .forEach(header -> {
                    fieldsIndexes.put(key(index, header), index.intValue());
                    index.getAndIncrement();
                });

        return fieldsIndexes;
    }

    public static Map<String, Integer> mapAntibioticsToIndexes(String[] headers) {
        Map<String, Integer> antibioticsToIndexes = new HashMap<>();

        AtomicInteger index = new AtomicInteger();
        index.getAndSet(0);
        Arrays.stream(headers)
                .forEach(header -> {
                    if(ANTIBIOTICS.contains(header))
                        antibioticsToIndexes.put(header, index.intValue());
                    index.getAndIncrement();
                });
        return antibioticsToIndexes;
    }

    public static String readFirstLine(String path)  {
        String firstLine = "";
        try {
            File inputF = new File(path);
            InputStream inputFS = new FileInputStream(inputF);

            BufferedReader br = new BufferedReader(new InputStreamReader(inputFS, StandardCharsets.UTF_8));

            firstLine = br.lines().findFirst().get();
            br.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return firstLine;
    }

    public static String[] readHeaders(String path) {
        return readFirstLine(path).split(";", -1);
    }

    private static String key(AtomicInteger index, String header) {
        return header.isEmpty() ? normalizeText(String.valueOf(index.intValue()))
                                : normalizeText(header);
    }
    public static Set<String> readAntibiotics(String path) {
        Set<String> antibiotics = new HashSet<>();
        try {
            Files.lines(Paths.get(path), StandardCharsets.UTF_8)
                    .forEach(l -> antibiotics.addAll(Arrays.asList(l.split(";", -1))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(antibiotics.contains("")) {
            antibiotics.remove("");
        }

        return antibiotics;
    }
}

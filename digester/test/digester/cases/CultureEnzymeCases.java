package digester.cases;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CultureEnzymeCases {
    private static String TEST_CASES_PATH = "test\\res\\test_cases.csv";
    private static List<String[]> cases;

    static {
        try {
            setupCases();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void setupCases() throws IOException {
        cases = new ArrayList<>();

        Files.lines(Paths.get(TEST_CASES_PATH), StandardCharsets.UTF_8)
                .map(l -> l.split(";", -1))
                .forEach(l -> cases.add(l));
    }

    public static Object[][] tsCases() {
        return new Object[][] {
                {cases.get(0), "2018-12-14T00:00:00Z"},
                {cases.get(1), "2019-01-01T00:00:00Z"},
                {cases.get(2), "2019-02-22T00:00:00Z"}
        };
    }

    public static Object[][] idCases() {
        return new Object[][] {
                {cases.get(0), "066895"},
                {cases.get(1), "067003"},
                {cases.get(2), "693937"}
        };
    }

    public static Object[][] typeCases() {
        return new Object[][] {
                {cases.get(0), "investigaciondebacteriasmultirresistentes"},
                {cases.get(1), "cultivo"},
                {cases.get(2), "portaifd"},
                {cases.get(4), "cultivoaerobio"},
                {cases.get(5), "cultivoaerobio"},
                {cases.get(6), "torchrn"}
        };
    }

    public static Object[][] codeIsolateCases() {
        return new Object[][] {
                {cases.get(0), "1"},
                {cases.get(1), "1"},
                {cases.get(2), "1"},
                {cases.get(3), "2"},
                {cases.get(4), "1"},
                {cases.get(5), "1"},
                {cases.get(9), "3"}
        };
    }
}

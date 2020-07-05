package digester.cases;

import es.ulpgc.datahub.events.Sample;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SampleEnzymeCases {
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
                {cases.get(0), "exudadorectal"},
                {cases.get(1), "broncoaspirado"},
                {cases.get(2), "esputoinducido"}
        };
    }

    public static Object[][] whereCases() {
        return new Object[][] {
                {cases.get(0), "NEGRIN-INTENSIVA"},
                {cases.get(1), "NEGRIN-INTENSIVA"},
                {cases.get(2), "NEGRIN-INTERNA2"},
                {cases.get(3), "APNORTE-csguanarteme"},
                {cases.get(4), "MATERNO-urgenciaspediatria"},
                {cases.get(5), "csarucas-csarucas"}
        };
    }

    public static Object[][] urgencyCases() {
        return new Object[][] {
                {cases.get(0), false},
                {cases.get(6), true},
                {cases.get(8), false}
        };
    }

    public static Object[][] whoRequestCases() {
        return new Object[][] {
                {cases.get(0), "sindatos"},
                {cases.get(7), "armandoalayon"},
                {cases.get(8), ""}
        };
    }

    public static Object[][] idIndividualCases() {
        return new Object[][] {
                {cases.get(0), "xxx"},
                {cases.get(7), "12341234"}
        };
    }

    public static Object[][] genderIndividualCases() {
        return new Object[][] {
                {cases.get(6), Sample.Individual.Gender.FEMALE},
                {cases.get(7), Sample.Individual.Gender.FEMALE},
                {cases.get(8), Sample.Individual.Gender.MALE}
        };
    }

    public static Object[][] birthdayIndividualCases() {
        return new Object[][] {
                {cases.get(3), "1962-11-07T00:00:00Z"},
                {cases.get(7), "1984-08-02T00:00:00Z"},
                {cases.get(8), "1971-05-07T00:00:00Z"}
        };
    }
}

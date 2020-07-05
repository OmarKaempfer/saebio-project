package digester.cases;

import es.ulpgc.datahub.events.Antibiogram;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static es.ulpgc.datahub.events.Antibiogram.AntibioticResponse.Response.RESISTENTE;
import static es.ulpgc.datahub.events.Antibiogram.AntibioticResponse.Response.SENSIBLE;

public class AntiobiogramEnzymeCases {
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

    public static Object[] tsCases() {
        return new Object[][]{
                {cases.get(0), "2018-12-14T00:00:00Z"},
                {cases.get(1), "2019-01-01T00:00:00Z"},
                {cases.get(2), "2019-02-22T00:00:00Z"}
        };
    }

    public static Object[][] idCases() {
        return new Object[][] {
                {cases.get(0), "066895"},
                {cases.get(1), "067003"},
                {cases.get(2), "693937"},
                {cases.get(3), "841842"},
                {cases.get(4), "510361"},
                {cases.get(5), "808595"},
                {cases.get(9), "000000"}
        };
    }

    public static Object[] isolateCases() {
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

    public static class AntibioticResponse_ {
        public static Object[] antibioticCases() {
            return new Object[][] {
                    {cases.get(0), new String[] {"AN", "CAZ", "CIP", "CLv", "GM", "IPM", "LVX", "MEM", "PIT", "TN"}},
                    {cases.get(1), new String[] {"ATM", "CAZ", "CIP", "CLv", "DOR", "FEP", "FO", "GM", "IPM", "LVX", "MEM", "PIP", "PIT", "TN"}},
                    {cases.get(2), new String[] {"ATM", "CAZ", "CIP", "CLv", "FEP", "FO", "GM", "IPM", "LVX", "MEM", "MINO", "PIP", "PIT", "SAM", "SXT", "TN"}}
            };
        }

        public static Object[] responseCases() {
            Map<String, Antibiogram.AntibioticResponse.Response> expected0 = new HashMap<>();
            expected0.put("AN", RESISTENTE);
            expected0.put("CAZ", SENSIBLE);
            expected0.put("CIP", RESISTENTE);
            expected0.put("CLv", SENSIBLE);
            expected0.put("GM", RESISTENTE);
            expected0.put("IPM", SENSIBLE);
            expected0.put("LVX", RESISTENTE);
            expected0.put("MEM", RESISTENTE);
            expected0.put("PIT", SENSIBLE);
            expected0.put("TN", RESISTENTE);

            return new Object[][] {
                    {cases.get(0), expected0}
            };
        }

        public static Object[] CMICases() {
            Map<String, String> expected0 = new HashMap<>();
            expected0.put("AN", "");
            expected0.put("CAZ", "");
            expected0.put("CIP", "");
            expected0.put("CLv", "1");
            expected0.put("GM", "");
            expected0.put("IPM", "");
            expected0.put("LVX", "");
            expected0.put("MEM", "");
            expected0.put("PIT", "");
            expected0.put("TN", "");

            return new Object[][] {
                    {cases.get(0), expected0}
            };
        }
    }

    public static class Identification_ {
        public static Object[] organismCases() {
            return new Object[][] {
                    {cases.get(0), "pseudomonasaeruginosa"},
                    {cases.get(10), "pseudomonasputida"},
                    {cases.get(11), "pseudomonasfluorescens"}
            };
        }
    }
}

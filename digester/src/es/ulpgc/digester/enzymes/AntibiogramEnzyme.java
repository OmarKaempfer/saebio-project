package es.ulpgc.digester.enzymes;

import es.ulpgc.datahub.events.Antibiogram;
import io.intino.alexandria.event.Event;
import io.intino.alexandria.message.MessageBuilder;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static es.ulpgc.datahub.events.Antibiogram.AntibioticResponse.*;
import static es.ulpgc.digester.data.DocumentStructure.*;
import static es.ulpgc.digester.utils.InputFormatter.formatDate;
import static es.ulpgc.digester.utils.InputFormatter.normalizeText;

public class AntibiogramEnzyme {
    private static final String TS_HEADER = "fechapeticion";
    private static final String ID_HEADER = "peticion";
    private static final String ISOLATE_HEADER = "numeroaislamiento";
    private static final String ORGANISM_HEADER = "microorganismo";

    public static Event digestRow(String[] row) {
        Antibiogram antibiogram = new Antibiogram();

        fillGeneralAntibiogram(row, antibiogram);
        fillAntibiogramIdentification(row, antibiogram);
        fillAntibiogramAntibioticResponse(row, antibiogram);

        return new Event(MessageBuilder.toMessage(antibiogram));
    }

    private static void fillAntibiogramAntibioticResponse(String[] row, Antibiogram antibiogram) {
        Map<String, Response> antibioticResponseMap = AntibioticResponse.responsesOf(row);
        Map<String, String> antibioticCmiMap = AntibioticResponse.CMIsOf(row);

        antibioticResponseMap.entrySet().stream()
                .forEach(l -> antibiogram.antibioticResponseList().add(
                        new Antibiogram.AntibioticResponse()
                        .antibiotic(l.getKey())
                        .response(l.getValue())
                        .cmi(antibioticCmiMap.get(l.getKey()))));
    }

    private static void fillAntibiogramIdentification(String[] row, Antibiogram antibiogram) {
        Antibiogram.Identification identification = new Antibiogram.Identification();
        identification.organism(Identification.organismOf(row));

        antibiogram.identificationList().add(identification);
    }

    private static void fillGeneralAntibiogram(String[] row, Antibiogram antibiogram) {
        antibiogram.ts(tsOf(row));
        antibiogram.id(idOf(row));
        antibiogram.isolate(isolateOf(row));
    }

    public static Instant tsOf(String[] columns) {
        String date = columns[indexOf(TS_HEADER)];
        String[] splitDate = date.split("/");

        String formattedDate = formatDate(splitDate);
        LocalDate localDate = LocalDate.parse(formattedDate);

        return localDate.atStartOfDay(ZoneId.of("UTC")).truncatedTo(ChronoUnit.DAYS).toInstant();
    }

    public static String idOf(String[] columns) {
        return normalizeText(columns[indexOf(ID_HEADER)]);
    }

    public static String isolateOf(String[] columns) {
        return normalizeText(columns[indexOf(ISOLATE_HEADER)]);
    }

    public static class Identification {
        public static String technique(String[] columns) {
            //TODO
            return null;
        }

        public static boolean result() {
            //TODO Currently no data of the partial identifications is recorded
            return true;
        }

        public static String organismOf(String[] columns) {
            return normalizeText(columns[indexOf(ORGANISM_HEADER)]);
        }
    }

    public static class AntibioticResponse {
        public static Map<String, Response> responsesOf(String[] columns) {
            Map<String, Response> antibioticToResponse = new HashMap<>();
            ANTIBIOTIC_TO_INDEX.values().stream()
                    .sorted()
                    .filter(i -> !columns[i].trim().isEmpty())
                    .forEach(i -> {
                        Response response = columns[i].equals("Resistente") ? Response.RESISTENTE :
                                            columns[i].equals("Sensible")   ? Response.SENSIBLE   :
                                                                              Response.INTERMEDIO;
                        antibioticToResponse.put(HEADERS[i], response);
                    });

            return antibioticToResponse;
        }

        public static String[] antibioticsOf(String[] columns) {
            List<String> antibiotics = new ArrayList<>();

            ANTIBIOTIC_TO_INDEX.values().stream()
                    .sorted()
                    .filter(i -> !columns[i].trim().isEmpty())
                    .forEach(i -> antibiotics.add(HEADERS[i]));

            return antibiotics.toArray(new String[antibiotics.size()]);
        }

        public static Map<String, String> CMIsOf(String[] columns) {
            Map<String, String> antibioticToCMI = new HashMap<>();
            ANTIBIOTIC_TO_INDEX.values().stream()
                    .sorted()
                    .filter(i -> !columns[i].trim().isEmpty())
                    .forEach(i -> antibioticToCMI.put(HEADERS[i], columns[i + 1].trim()));

            return antibioticToCMI;
        }
    }
}

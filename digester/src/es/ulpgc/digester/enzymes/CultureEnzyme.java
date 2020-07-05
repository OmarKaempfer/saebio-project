package es.ulpgc.digester.enzymes;

import es.ulpgc.datahub.events.Culture;
import io.intino.alexandria.event.Event;
import io.intino.alexandria.message.MessageBuilder;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static es.ulpgc.digester.data.DocumentStructure.indexOf;
import static es.ulpgc.digester.utils.InputFormatter.formatDate;
import static es.ulpgc.digester.utils.InputFormatter.normalizeText;

public class CultureEnzyme {
    private static final String TS_HEADER = "fechapeticion";
    private static final String ID_HEADER = "peticion";
    private static final String TYPE_HEADER = "cultivo";
    private static final String CODE_ISOLATE_HEADER = "numeroaislamiento";

    private static Map<String, Culture> cultureMap;

    static {
        cultureMap = new HashMap<>();
    }

    public static void digestRow(String[] row) {
        String id = idOf(row);
        if(cultureMap.containsKey(id)) {
            addNewIsolate(row, cultureMap.get(id));
        } else {
            addNewCulture(row);
        }
    }

    public static Stream<Event> collectAllMessages() {
        return cultureMap.values().stream()
                .map(culture -> new Event(MessageBuilder.toMessage(culture)));
    }

    private static void addNewIsolate(String[] row, Culture culture) {
        Culture.Isolate isolate = new Culture.Isolate();
        isolate.code(Isolate.codeOf(row));

        Boolean isolateIsPresent = culture.isolateList().stream()
                .anyMatch(l -> l.code().equals(isolate.code()));

        if(!isolateIsPresent) {
            culture.isolateList().add(isolate);
        }
    }

    private static void addNewCulture(String[] row) {
        Culture culture = new Culture();
        culture.id(idOf(row));
        culture.ts(tsOf(row));
        culture.type(typeOf(row));

        addNewIsolate(row, culture);

        cultureMap.put(culture.id(), culture);
    }

    public static Instant tsOf(String[] row) {
        String date = row[indexOf(TS_HEADER)];
        String[] splitDate = date.split("/");

        String formattedDate = formatDate(splitDate);
        LocalDate localDate = LocalDate.parse(formattedDate);

        return localDate.atStartOfDay(ZoneId.of("UTC")).truncatedTo(ChronoUnit.DAYS).toInstant();
    }

    public static String idOf(String[] row) {
        return normalizeText(row[indexOf(ID_HEADER)]);
    }

    public static String typeOf(String[] row) {
        return normalizeText(row[indexOf(TYPE_HEADER)]);
    }

    public static class Isolate {

        public static String codeOf(String[] row) {
            return normalizeText(row[indexOf(CODE_ISOLATE_HEADER)]);
        }

        public static String morphologyOf(String[] row) {
            //TODO No morphology field, it is meant to be introduced in the future
            return null;
        }

        public static String gramOf(String[] row) {
            //TODO No gram field, it is meant to be introduced in the future
            return null;
        }
    }
}

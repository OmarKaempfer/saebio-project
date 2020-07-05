package es.ulpgc.digester.enzymes;

import es.ulpgc.datahub.events.Sample;
import io.intino.alexandria.event.Event;
import io.intino.alexandria.message.MessageBuilder;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import static es.ulpgc.datahub.events.Sample.Individual.*;
import static es.ulpgc.digester.data.DocumentStructure.indexOf;
import static es.ulpgc.digester.thesauri.Thesauri.*;
import static es.ulpgc.digester.utils.InputFormatter.formatDate;
import static es.ulpgc.digester.utils.InputFormatter.normalizeText;

public class SampleEnzyme {
    private static final String DEFAULT_DATE = "1900-01-01";

    private static final String TS_HEADER = "fechapeticion";
    private static final String ID_HEADER = "peticion";
    private static final String TYPE_HEADER = "tipodemuestra";
    private static final String AREA_HEADER = "0";
    private static final String SERVICE_HEADER = "servicio";
    private static final String WHO_REQUESTED_HEADER = "medico";
    private static final String URGENCY_HEADER = "urgente";
    private static final String INDIVIDUAL_ID_HEADER = "nhc";
    private static final String INDIVIDUAL_GENDER_HEADER = "sexo";
    private static final String INDIVIDUAL_BIRTHDAY_HEADER = "fechanacimiento";

    public static Event digestRow(String[] row) {
        Sample sample = new Sample();
        fillSampleValues(row, sample);
        fillSampleRequestValues(row, sample);
        fillSampleIndividualValues(row, sample);
        fillSampleEnvironmentalValues(row, sample);

        return new Event(MessageBuilder.toMessage(sample));
    }

    public static void fillSampleValues(String[] row, Sample sample) {
        sample.ts(tsOf(row));
        sample.id(idOf(row));
        sample.type(typeOf(row));
        sample.where(whereOf(row));
    }

    public static void fillSampleRequestValues(String[] row, Sample sample) {
        Sample.Request request = new Sample.Request();
        request.urgency(Request.urgencyOf(row));
        request.who(Request.who(row));
        sample.request(request);
    }

    public static void fillSampleIndividualValues(String[] row, Sample sample) {
        Sample.Individual individual = new Sample.Individual();

        individual.id(Individual.idOf(row));
        individual.birthday(Individual.birthdayOf(row));
        individual.gender(Individual.genderOf(row));

        sample.individual(individual);
    }

    public static void fillSampleEnvironmentalValues(String[] row, Sample sample) {
        // TODO
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

    public static String whereOf(String[] row) {
        String area = areaOf(row);
        String service = serviceOf(row);

        return new StringBuilder().append(area).append("-").append(service).toString();
    }

    private static String serviceOf(String[] row) {
        String service = normalizeText(row[indexOf(SERVICE_HEADER)]);
        service = SERVICE_THESAURUS.containsKey(service) ? SERVICE_THESAURUS.get(service) : service;
        return service;
    }

    private static String areaOf(String[] row) {
        String area = normalizeText(row[indexOf(AREA_HEADER)]);
        area = AREA_THESAURUS.containsKey(area) ? AREA_THESAURUS.get(area) : area;
        return area;
    }

    public static class Request {
        public static boolean urgencyOf(String[] row) {
            String urgency = row[indexOf(URGENCY_HEADER)];
            return urgency.equals("S") ? true : false;
        }

        public static String who(String[] row) {
            return normalizeText(row[indexOf(WHO_REQUESTED_HEADER)]);
        }
    }

    public static class Environmental {
        // TODO
    }

    public static class Individual {
        public static String idOf(String[] row) {
            return normalizeText(String.valueOf(row[indexOf(INDIVIDUAL_ID_HEADER)]));
        }

        public static Gender genderOf(String[] row) {
            String gender = normalizeText(row[indexOf(INDIVIDUAL_GENDER_HEADER)]);
            return gender.equals("m") ? Gender.MALE : Gender.FEMALE;
        }

        public static Instant birthdayOf(String[] row) {
            String date = row[indexOf(INDIVIDUAL_BIRTHDAY_HEADER)];
            String[] splitDate = date.split("/");

            String formattedDate = formatDate(splitDate);
            LocalDate localDate;
            try {
                localDate = LocalDate.parse(formattedDate);
            } catch (DateTimeParseException e) {
                localDate = LocalDate.parse(DEFAULT_DATE);
            }

            return localDate.atStartOfDay(ZoneId.of("UTC")).truncatedTo(ChronoUnit.DAYS).toInstant();
        }
    }
}

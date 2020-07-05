package es.ulpgc.digester;

import es.ulpgc.datahub.events.Antibiogram;
import es.ulpgc.datahub.events.Culture;
import es.ulpgc.datahub.events.Sample;
import es.ulpgc.digester.box.DigesterBox;
import es.ulpgc.digester.data.DocumentStructure;
import es.ulpgc.digester.enzymes.AntibiogramEnzyme;
import es.ulpgc.digester.enzymes.CultureEnzyme;
import es.ulpgc.digester.enzymes.SampleEnzyme;
import io.intino.alexandria.Scale;
import io.intino.alexandria.Timetag;
import io.intino.alexandria.event.Event;
import io.intino.alexandria.ingestion.EventSession;
import io.intino.alexandria.ingestion.SessionHandler;
import io.intino.alexandria.logger.Logger;
import io.intino.alexandria.message.Message;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;

public class MicrobiologyDigester {
    private static HashSet<String> digestedSamples;
    private static HashSet<String> digestedAntibiograms;

    public static void digest(DigesterBox box, String csvPath) {
        digestedSamples = new HashSet<>();
        digestedAntibiograms = new HashSet<>();

        try {
            Files.lines(Paths.get(csvPath), StandardCharsets.UTF_8)
                    .skip(1)
                    .map(row -> row.split(";", -1))
                    .forEach(row -> {
                        CultureEnzyme.digestRow(row);
                        digestAntibiogramDone(box, row);
                        digestIndividualSampled(box, row);
                    });

            CultureEnzyme.collectAllMessages()
                    .forEach(m -> box.terminal().publish((Culture)m));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Logger.info("Digestion completed");
    }

    private static boolean digestIndividualSampled(DigesterBox box, String[] row) {
        Event sampleMessage = SampleEnzyme.digestRow(row);

        if(!digestedSamples.contains(sampleMessage.toMessage().get("id").toString())) {
            digestedSamples.add(sampleMessage.toMessage().get("id").toString());
            box.terminal().publish((Sample)sampleMessage);
            return true;
        }

        return false;
    }

    private static boolean digestAntibiogramDone(DigesterBox box, String[] row) {
        Event antibiogram = AntibiogramEnzyme.digestRow(row);

        if(!digestedAntibiograms.contains(antibiogram.toMessage().get("id").toString())) {
            digestedAntibiograms.add(antibiogram.toMessage().get("id").toString());
            box.terminal().publish((Antibiogram)antibiogram);
            return true;
        }

        return false;
    }

    private static Timetag timetagOf(Message m) {
        Instant ts = m.get("ts").as(Instant.class);
        return Timetag.of(LocalDateTime.ofInstant(ts, ZoneOffset.UTC), Scale.Hour);
    }
}

package digester;

import digester.cases.AntiobiogramEnzymeCases;
import es.ulpgc.datahub.events.Antibiogram;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import static es.ulpgc.digester.enzymes.AntibiogramEnzyme.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class AntibiogramEnzyme_ {

    @Test
    @Parameters(source = AntiobiogramEnzymeCases.class, method = "tsCases")
    public void ts(String[] row, String expected) {
        assertThat(tsOf(row)).isEqualTo(expected);
    }

    @Test
    @Parameters(source = AntiobiogramEnzymeCases.class, method = "idCases")
    public void id(String[] row, String expected) {
        assertThat(idOf(row)).isEqualTo(expected);
    }

    @Test
    @Parameters(source = AntiobiogramEnzymeCases.class, method = "isolateCases")
    public void isolate(String[] row, String expected) {
        assertThat(isolateOf(row)).isEqualTo(expected);
    }

    @Test
    @Parameters(source = AntiobiogramEnzymeCases.Identification_.class, method = "organismCases")
    public void organism(String[] row, String expected) {
        assertThat(Identification.organismOf(row)).isEqualTo(expected);
    }

    @Test
    @Parameters(source = AntiobiogramEnzymeCases.AntibioticResponse_.class, method = "antibioticCases")
    public void antibiotic(String[] row, String[] expected) {
        assertThat(AntibioticResponse.antibioticsOf(row)).isEqualTo(expected);
    }

    @Test
    @Parameters(source = AntiobiogramEnzymeCases.AntibioticResponse_.class, method = "responseCases")
    public void antibioticResponse(String[] row, Map<String, Antibiogram.AntibioticResponse.Response> expected) {
        Map<String, Antibiogram.AntibioticResponse.Response> antibioticResponses = AntibioticResponse.responsesOf(row);
        assertThat(antibioticResponses).containsAllEntriesOf(expected);
    }

    @Test
    @Parameters(source = AntiobiogramEnzymeCases.AntibioticResponse_.class, method = "CMICases")
    public void antibioticCMI(String[] row, Map<String, String> expected) {
        Map<String, String> antibioticToCMI = AntibioticResponse.CMIsOf(row);
        assertThat(antibioticToCMI).containsAllEntriesOf(expected);
    }
}

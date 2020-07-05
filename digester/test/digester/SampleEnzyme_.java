package digester;

import digester.cases.SampleEnzymeCases;
import es.ulpgc.datahub.events.Sample;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static es.ulpgc.digester.enzymes.SampleEnzyme.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class SampleEnzyme_ {

    @Test
    @Parameters(source = SampleEnzymeCases.class, method = "tsCases")
    public void ts(String[] row, String expected) {
        assertThat(String.valueOf(tsOf(row))).isEqualTo(expected);
    }

    @Test
    @Parameters(source = SampleEnzymeCases.class, method = "idCases")
    public void id(String[] row, String expected) {
        assertThat(idOf(row)).isEqualTo(expected);
    }

    @Test
    @Parameters(source = SampleEnzymeCases.class, method = "typeCases")
    public void type(String[] row, String expected) {
        assertThat(typeOf(row)).isEqualTo(expected);
    }

    @Test
    @Parameters(source = SampleEnzymeCases.class, method = "whereCases")
    public void where(String[] row, String expected) {
        assertThat(whereOf(row)).isEqualTo(expected);
    }

    @Test
    @Parameters(source = SampleEnzymeCases.class, method = "urgencyCases")
    public void urgency(String[] row, boolean expected) {
        assertThat(Request.urgencyOf(row)).isEqualTo(expected);
    }

    @Test
    @Parameters(source = SampleEnzymeCases.class, method = "whoRequestCases")
    public void whoRequest(String[] row, String expected) {
        assertThat(Request.who(row)).isEqualTo(expected);
    }

    @Test
    @Parameters(source = SampleEnzymeCases.class, method = "idIndividualCases")
    public void idIndividual(String[] row, String expected) {
        assertThat(Individual.idOf(row)).isEqualTo(expected);
    }

    @Test
    @Parameters(source = SampleEnzymeCases.class, method = "genderIndividualCases")
    public void genderIndividual(String[] row, Sample.Individual.Gender expected) {
        assertThat(Individual.genderOf(row)).isEqualTo(expected);
    }

    @Test
    @Parameters(source = SampleEnzymeCases.class, method = "birthdayIndividualCases")
    public void birthdayIndividual(String[] row, String expected) {
        assertThat(String.valueOf(Individual.birthdayOf(row))).isEqualTo(expected);
    }

}

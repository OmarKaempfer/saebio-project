package digester;

import digester.cases.CultureEnzymeCases;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static es.ulpgc.digester.enzymes.CultureEnzyme.*;

@RunWith(JUnitParamsRunner.class)
public class CultureEnzyme_ {

    @Test
    @Parameters(source = CultureEnzymeCases.class, method = "tsCases")
    public void ts(String[] row, String expected) {
        assertThat(tsOf(row)).isEqualTo(expected);
    }

    @Test
    @Parameters(source = CultureEnzymeCases.class, method = "idCases")
    public void id(String[] row, String expected) {
        assertThat(idOf(row)).isEqualTo(expected);
    }

    @Test
    @Parameters(source = CultureEnzymeCases.class, method = "typeCases")
    public void type(String[] row, String expected) {
        assertThat(typeOf(row)).isEqualTo(expected);
    }

    @Test
    @Parameters(source = CultureEnzymeCases.class, method = "codeIsolateCases")
    public void codeIsolate(String[] row, String expected) {
        assertThat(Isolate.codeOf(row)).isEqualTo(expected);
    }
}

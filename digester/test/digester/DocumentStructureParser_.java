package digester;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.assertj.core.api.Assertions.assertThat;
import static es.ulpgc.digester.utils.DocumentStructureParser.mapHeadersToIndexes;
import static es.ulpgc.digester.data.DocumentStructure.indexOf;

@RunWith(JUnitParamsRunner.class)
public class DocumentStructureParser_ {
    public static Object[][] parametersForHeaders() {
        String headersLine = ";Servicio;Destino;Medico;Area;TipoDeMuestra;Cultivo;Microorganismo;ResMicroorganismo;Peticion;FechaPeticion;NHC;Paciente;FechaNacimiento;Sexo;Urgente;Mes;Año;IdTipoMuestra;IdMicroorganismo;IdArea;NumeroAislamiento;AMC;AMCcmi;AMP;AMPcmi;AN;ANcmi;ATM;ATMcmi;AZM;AZMcmi;BLEEm;BLEEmcmi;BLEEv;BLEEvcmi;CAZ;CAZcmi;CDN;CDNcmi;CF;CFcmi;CIP;CIPcmi;CL;CLcmi;CLv;CLvcmi;Cpnm;Cpnmcmi;CRO;CROcmi;CTX;CTXcmi;CXMA;CXMAcmi;CXMS;CXMScmi;CZ;CZcmi;CZA;CZAcmi;CZT;CZTcmi;DO;DOcmi;DOR;DORcmi;EPM;EPMcmi;FEP;FEPcmi;FM;FMcmi;FO;FOcmi;FOX;FOXcmi;GM;GMcmi;IPM;IPMcmi;LVX;LVXcmi;MEM;MEMcmi;MINO;MINOcmi;NA;NAcmi;NET;NETcmi;NOR;NORcmi;P;Pcmi;PIP;PIPcmi;PIT;PITcmi;SAM;SAMcmi;SXT;SXTcmi;TGC;TGCcmi;TN;TNcmi;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;";
        return new Object[][] {
                {headersLine, "0", 0},
                {headersLine, "servicio", 1},
                {headersLine, "destino", 2},
                {headersLine, "medico", 3},
                {headersLine, "tncmi", 107}
        };
    }

    @Test
    @Parameters(method = "parametersForHeaders")
    public void headers(String headersLine, String header, int expected) {
        mapHeadersToIndexes(headersLine);
        assertThat(indexOf(header)).isEqualTo(expected);
    }
}
package es.ulpgc.digester.thesauri;

import java.util.HashMap;
import java.util.Map;

public class Thesauri {
    public static final Map<String, String> SERVICE_THESAURUS;
    public static final Map<String, String> AREA_THESAURUS;

    static {
        SERVICE_THESAURUS = new HashMap<>();
        AREA_THESAURUS = new HashMap<>();
        fillServiceThesaurus();
        fillAreaThesaurus();
    }

    private static void fillServiceThesaurus() {
        SERVICE_THESAURUS.put("medicinaintensiva", "INTENSIVA");
        SERVICE_THESAURUS.put("neumologia", "NEUMO");
        SERVICE_THESAURUS.put("urgencias", "URG");
        SERVICE_THESAURUS.put("anestesiayreanimacion", "ANREAN");
        SERVICE_THESAURUS.put("nefrologia", "NEFRO");
        SERVICE_THESAURUS.put("cirugiageneralapdigestivo", "CGAPDIGEST");
        SERVICE_THESAURUS.put("medicinainterna", "INTERNA");
        SERVICE_THESAURUS.put("medicinainterna2", "INTERNA2");
        SERVICE_THESAURUS.put("cirugia2", "CIRUGIA2");
        SERVICE_THESAURUS.put("radiodiagnostico", "RADIODIAG");
        SERVICE_THESAURUS.put("hematologia", "HEMATO");
        SERVICE_THESAURUS.put("unidaddetransplantesdemedu", "UTMEDULA");
        SERVICE_THESAURUS.put("unidaddetrasplantesdemedu", "UTMEDULA");
    }

    private static void fillAreaThesaurus() {
        AREA_THESAURUS.put("hospitalunivgcdrnegrin", "NEGRIN");
        AREA_THESAURUS.put("atencionprimariaareanorte", "APNORTE");
        AREA_THESAURUS.put("hospitalmaternoinfantil", "MATERNO");
        AREA_THESAURUS.put("atencionprimariaareasur", "APSUR");
    }
}

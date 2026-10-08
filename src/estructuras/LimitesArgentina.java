package estructuras;

import java.util.HashSet;
import java.util.Set;

public class LimitesArgentina {
    private static final Set<String> limitesValidos = new HashSet<>();

    static {
        agregarLimite("buenos aires", "cordoba");
        agregarLimite("buenos aires", "santa fe");
        agregarLimite("buenos aires", "la pampa");
        agregarLimite("buenos aires", "rio negro");
        agregarLimite("buenos aires", "entre rios");

        agregarLimite("cordoba", "santa fe");
        agregarLimite("cordoba", "mendoza");
        agregarLimite("cordoba", "la pampa");
        agregarLimite("cordoba", "san luis");
        agregarLimite("cordoba", "santiago del estero");
        agregarLimite("cordoba", "catamarca");
        agregarLimite("cordoba", "la rioja");

        agregarLimite("mendoza", "san juan");
        agregarLimite("mendoza", "san luis");
        agregarLimite("mendoza", "neuquen");
        agregarLimite("mendoza", "la pampa");

        agregarLimite("tierra del fuego", "santa cruz");
        
        agregarLimite("chubut", "santa cruz");
        agregarLimite("chubut", "rio negro");
        
        agregarLimite("rio negro", "neuquen");
        agregarLimite("rio negro", "la pampa");
        
        agregarLimite("la rioja", "san juan");
        agregarLimite("la rioja", "catamarca");
        
        agregarLimite("tucuman", "catamarca");
        agregarLimite("tucuman", "santiago del estero");
        agregarLimite("tucuman", "salta");
        
        agregarLimite("salta", "jujuy");
        agregarLimite("salta", "chaco");
        agregarLimite("salta", "formosa");
        agregarLimite("salta", "santiago del estero");
        agregarLimite("salta", "catamarca");
        
        
        agregarLimite("corrientes", "entre rios");
        agregarLimite("corrientes", "misiones");
        agregarLimite("corrientes", "santa fe");
        agregarLimite("corrientes", "chaco");
        
        agregarLimite("santa fe", "chaco");
        agregarLimite("santa fe", "santiago del estero");
        agregarLimite("santa fe", "entre rios");
        
        agregarLimite("chaco", "formosa");
        agregarLimite("chaco", "santiago del estero");
        
        agregarLimite("catamarca", "santiago del estero");
        
        agregarLimite("san luis", "san juan");
        agregarLimite("san luis", "la pampa");



        
    }

    private static void agregarLimite(String p1, String p2) {
        limitesValidos.add(p1.toLowerCase().trim() + "-" + p2.toLowerCase().trim());
        limitesValidos.add(p2.toLowerCase().trim() + "-" + p1.toLowerCase().trim());
    }

    public static boolean sonLimitrofes(String p1, String p2) {
        String clave = p1.toLowerCase().trim() + "-" + p2.toLowerCase().trim();
        return limitesValidos.contains(clave);
    }
}
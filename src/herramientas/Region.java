package herramientas;

import java.util.HashSet;
import java.util.Set;

public class Region {

    private Set<Provincia> provincias;

    public Region() {
        provincias = new HashSet<>();
    }

    public void agregarProvincia(Provincia provincia) {
        provincias.add(provincia);
    }

    public Set<Provincia> getProvincias() {
        return provincias;
    }
}
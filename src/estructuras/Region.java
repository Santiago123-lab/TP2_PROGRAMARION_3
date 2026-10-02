package estructuras;

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
    	return new HashSet<>(provincias); 
    	//NOTA: Se retorna una copia de lo almacenado en provincias para 
    	//evitar por ejemplo region.getProvincias().clear()
    }
}
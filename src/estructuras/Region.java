package estructuras;

import java.util.HashSet;
import java.util.Set;

public class Region {

    private Set<Vertice> provincias;

    public Region() {
        provincias = new HashSet<>();
    }

    public void agregarVertice(Vertice provincia) {
        provincias.add(provincia);
    }

    public Set<Vertice> getVertices() {
    	return new HashSet<>(provincias); 
    	//NOTA: Se retorna una copia de lo almacenado en provincias para 
    	//evitar por ejemplo region.getProvincias().clear()
    }
}
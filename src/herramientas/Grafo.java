package herramientas;

import java.util.HashSet;
import java.util.Set;

public class Grafo {
	
	//aca despues vamos agregando cosas mas puntuales, solamente puse lo basiquisimo
	
    private Set<Provincia> provincias;
    private Set<Arista> aristas;

    public Grafo() {
        provincias = new HashSet<>();
        aristas = new HashSet<>();
    }

    public void agregarProvincia(Provincia provincia) {
        provincias.add(provincia);
    }

    public void agregarArista(Arista arista) {
        aristas.add(arista);
    }
}
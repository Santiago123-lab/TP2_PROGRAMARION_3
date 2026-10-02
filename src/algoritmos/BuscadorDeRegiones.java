package algoritmos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import estructuras.Provincia;
import estructuras.Region;
import grafo.Arista;
import grafo.Grafo;
import grafo.Nodo;

public class BuscadorDeRegiones {

    public List<Region> agrupar(Grafo<Provincia> mst, int k) {
    	
    	if (k < 1 || k > mst.getTodosLosNodos().size()) {
    	    throw new IllegalArgumentException("La cantidad de regiones debe mayor a 1 y menor a la cantidad de provincias");
    	}
    	
        List<Arista<Provincia>> aristas = mst.getTodasLasAristas();
        aristas.sort(Comparator.comparingDouble(Arista::getPeso));

        int aristasAConservar = Math.max(0, aristas.size() - (k - 1));

        DSU<Provincia> dsu = new DSU<>();
        dsu.inicializar(mst.getTodosLosNodos());

        conectarProvincias(dsu, aristas, aristasAConservar);
        
        return extraerRegiones(mst.getTodosLosNodos(), dsu);
    }

    private void conectarProvincias(DSU<Provincia> dsu, List<Arista<Provincia>> aristas, int limite) {
        for (int i = 0; i < limite; i++) {
            Arista<Provincia> arista = aristas.get(i);
            dsu.union(arista.getOrigen().getValor(), arista.getDestino().getValor());
        }
    }

    private List<Region> extraerRegiones(List<Nodo<Provincia>> nodos, DSU<Provincia> dsu) {
        Map<Provincia, Region> mapaRegiones = new HashMap<>();
        
        for (Nodo<Provincia> nodo : nodos) {
            Provincia provincia = nodo.getValor();
            Provincia representante = dsu.find(provincia);

            mapaRegiones.putIfAbsent(representante, new Region());
            mapaRegiones.get(representante).agregarProvincia(provincia);
        }
        
        return new ArrayList<>(mapaRegiones.values());
    }
}
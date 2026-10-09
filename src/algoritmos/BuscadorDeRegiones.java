package algoritmos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import estructuras.Vertice;
import estructuras.Region;
import grafo.Arista;
import grafo.Grafo;
import grafo.Nodo;

public class BuscadorDeRegiones {

    public List<Region> agrupar(Grafo<Vertice> mst, int k) {
    	
    	if (k < 1 || k > mst.getTodosLosNodos().size()) {
    	    throw new IllegalArgumentException("La cantidad de regiones debe mayor a 1 y menor a la cantidad de provincias");
    	}
    	
        List<Arista<Vertice>> aristas = mst.getTodasLasAristas();
        aristas.sort(Comparator.comparingDouble(Arista::getPeso));

        int aristasAConservar = Math.max(0, aristas.size() - (k - 1));

        DSU<Vertice> dsu = new DSU<>();
        dsu.inicializar(mst.getTodosLosNodos());

        conectarProvincias(dsu, aristas, aristasAConservar);
        
        return extraerRegiones(mst.getTodosLosNodos(), dsu);
    }

    private void conectarProvincias(DSU<Vertice> dsu, List<Arista<Vertice>> aristas, int limite) {
        for (int i = 0; i < limite; i++) {
            Arista<Vertice> arista = aristas.get(i);
            dsu.union(arista.getOrigen().getValor(), arista.getDestino().getValor());
        }
    }

    private List<Region> extraerRegiones(List<Nodo<Vertice>> nodos, DSU<Vertice> dsu) {
        Map<Vertice, Region> mapaRegiones = new HashMap<>();
        
        for (Nodo<Vertice> nodo : nodos) {
            Vertice provincia = nodo.getValor();
            Vertice representante = dsu.find(provincia);

            mapaRegiones.putIfAbsent(representante, new Region());
            mapaRegiones.get(representante).agregarVertice(provincia);
        }
        
        return new ArrayList<>(mapaRegiones.values());
    }
}
package algoritmos;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import grafo.Arista;
import grafo.Grafo;
import grafo.Nodo;

public class Kruskal {

    public static <T> Grafo<T> calcularMST(Grafo<T> grafo) {
        DSU<T> dsu = new DSU<>();
        dsu.inicializar(grafo.getTodosLosNodos());

        Grafo<T> mst = new Grafo<>();
        for (Nodo<T> nodo : grafo.getTodosLosNodos()) {
            mst.agregarNodo(nodo.getValor());
        }

        List<Arista<T>> aristas = grafo.getTodasLasAristas();
        Collections.sort(aristas, Comparator.comparingDouble(Arista::getPeso));

        for (Arista<T> arista : aristas) {
            if (!dsu.connected(arista.getOrigen().getValor(), arista.getDestino().getValor())) {
                dsu.union(arista.getOrigen().getValor(), arista.getDestino().getValor());
                mst.agregarArista(arista.getOrigen(), arista.getDestino(), arista.getPeso());
            }
        }

        return mst;
    }

}

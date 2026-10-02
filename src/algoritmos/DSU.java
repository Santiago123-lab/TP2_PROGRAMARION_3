package algoritmos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import grafo.Nodo;

public class DSU<T> {
    private Map<T, T> padre;
    private Map<T, Integer> rango;

    public DSU() {
        this.padre = new HashMap<>();
        this.rango = new HashMap<>();
    }

    public void inicializar(List<Nodo<T>> nodos) {
        for (Nodo<T> nodo : nodos) {
            padre.put(nodo.getValor(), nodo.getValor());
            rango.put(nodo.getValor(), 0);
        }
    }

    // Une los conjuntos que contienen a x e y.
    public void union(T x, T y) {
        T raizX = find(x);
        T raizY = find(y);

        if (raizX.equals(raizY)) {
            return;
        }

        int rankX = rango.get(raizX);
        int rankY = rango.get(raizY);

        if (rankX < rankY) {
            padre.put(raizX, raizY);
        } else if (rankX > rankY) {
            padre.put(raizY, raizX);
        } else {
            padre.put(raizY, raizX);
            rango.put(raizX, rankX + 1);
        }
    }

    // Devuelve el representante (raíz) del conjunto al que pertenece x.
    public T find(T x) {
        // Si x no está en el mapa, es su propio padre.
        if (!padre.containsKey(x)) {
            padre.put(x, x);
            rango.put(x, 0);
            return x;
        }

        // Path compression
        if (!padre.get(x).equals(x)) {
            padre.put(x, find(padre.get(x)));
        }
        return padre.get(x);
    }

    // Verifica si x e y están en el mismo conjunto.
    public boolean connected(T x, T y) {
        return find(x).equals(find(y));
    }
}

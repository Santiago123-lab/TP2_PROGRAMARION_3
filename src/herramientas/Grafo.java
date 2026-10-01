package herramientas;

import java.util.*;

public class Grafo<T> {

    private Map<Nodo<T>, List<Adyacencia<T>>> adyacencias;
    private List<Arista<T>> listaDeAristasGlobal;

    public Grafo() {
        this.adyacencias = new HashMap<>();
        this.listaDeAristasGlobal = new ArrayList<>();
    }

    public Nodo<T> agregarNodo(T valor) {
        Nodo<T> nuevoNodo = new Nodo<>(valor);
        adyacencias.putIfAbsent(nuevoNodo, new ArrayList<>());
        return nuevoNodo;
    }

    public void agregarArista(Nodo<T> origen, Nodo<T> destino, double peso) {
        if (origen.equals(destino)) {
            throw new IllegalArgumentException("No se permiten bucles (relación de un nodo consigo mismo).");
        }

        if (!adyacencias.containsKey(origen) || !adyacencias.containsKey(destino)) {
            throw new IllegalArgumentException("Ambos nodos deben existir en el grafo para agregar una arista.");
        }

        boolean existe = adyacencias.get(origen).stream()
                .anyMatch(a -> a.getDestino().equals(destino));

        if (existe) {
            throw new IllegalArgumentException("La arista entre estos nodos ya existe.");
        }

        adyacencias.get(origen).add(new Adyacencia<>(destino, peso));
        adyacencias.get(destino).add(new Adyacencia<>(origen, peso));
        listaDeAristasGlobal.add(new Arista<>(origen, destino, peso));
    }

    public List<Adyacencia<T>> getVecinos(Nodo<T> nodo) {
        return adyacencias.getOrDefault(nodo, Collections.emptyList());
    }

    public List<Nodo<T>> getTodosLosNodos() {
        return new ArrayList<>(adyacencias.keySet());
    }

    public List<Arista<T>> getTodasLasAristas() {
        return listaDeAristasGlobal;
    }

    public void imprimirGrafo() {
        for (Map.Entry<Nodo<T>, List<Adyacencia<T>>> entry : adyacencias.entrySet()) {
            System.out.println(
                    "Nodo " + entry.getKey() +
                            " -> Vecinos: " + entry.getValue());
        }
    }
}
package herramientas;

import java.util.*;

public class Grafo<T> {

    private Map<Nodo<T>, List<Arista<T>>> adyacencias;

    public Grafo() {
        this.adyacencias = new HashMap<>();
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

        adyacencias.get(origen).add(new Arista<>(origen, destino, peso));
        adyacencias.get(destino).add(new Arista<>(destino, origen, peso));
    }

    public List<Arista<T>> getVecinos(Nodo<T> nodo) {
        return adyacencias.getOrDefault(nodo, Collections.emptyList());
    }

    public void imprimirGrafo() {
        for (Map.Entry<Nodo<T>, List<Arista<T>>> entry : adyacencias.entrySet()) {
            System.out.println(
                    "Nodo " + entry.getKey() +
                            " -> Vecinos: " + entry.getValue());
        }
    }
    
    public List<Arista<T>> getAristas() {
    	
        Set<Arista<T>> aristasUnicas = new HashSet<>();

        for (List<Arista<T>> lista : adyacencias.values()) {
        	
            aristasUnicas.addAll(lista);
        }

        return new ArrayList<>(aristasUnicas);
    }
    
}
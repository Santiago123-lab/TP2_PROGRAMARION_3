package herramientas;

public class Arista<T> {
    private Nodo<T> destino;
    private double peso;
    private Nodo<T> origen;

    public Arista(Nodo<T> origen, Nodo<T> destino, double peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }

    public Nodo<T> getDestino() {
        return destino;
    }

    public double getPeso() {
        return peso;
    }
    
    public Nodo<T> getOrigen() {
        return origen;
    }

    @Override
    public String toString() {
        return destino.toString() + " (peso: " + peso + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arista<?> arista = (Arista<?>) o;
        return Double.compare(arista.peso, peso) == 0
                && ((origen.equals(arista.origen) && destino.equals(arista.destino))
                || (origen.equals(arista.destino) && destino.equals(arista.origen)));
    }

    @Override
    public int hashCode() {
        return origen.hashCode() + destino.hashCode() + Double.hashCode(peso);
    }
}

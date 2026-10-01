package herramientas;

public class Adyacencia<T> {
    private Nodo<T> destino;
    private double peso;

    public Adyacencia(Nodo<T> destino, double peso) {
        this.destino = destino;
        this.peso = peso;
    }

    public Nodo<T> getDestino() {
        return destino;
    }

    public double getPeso() {
        return peso;
    }

    @Override
    public String toString() {
        return "Adyacencia{destino=" + (destino != null ? destino.getValor() : "null") + ", peso=" + peso + "}";
    }
}

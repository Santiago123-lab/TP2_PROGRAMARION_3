package estructuras;

import java.util.Objects;

public class Vertice {

    private String nombre;

    public Vertice(String nombre) {
        

        if (nombre == null) {
            throw new NullPointerException("Por favor, ingrese un vértice.");
        }
       
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Vertice vertice = (Vertice) o;
        return nombre.toLowerCase().equals(vertice.nombre.toLowerCase()); //ambos en minuscula
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre.toLowerCase());
    }

    @Override
    public String toString() {
        // Capitaliza la primera letra para mostrarla más prolija si querés,
        // o simplemente devolver el nombre normalizado.
        return nombre;
    }
}

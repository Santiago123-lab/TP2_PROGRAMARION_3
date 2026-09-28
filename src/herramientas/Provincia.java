package herramientas;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Provincia {

    // Usamos una lista estática para no recrearla con cada instancia
    private static final List<String> PROVINCIAS_VALIDAS = Arrays.asList(
            "buenos aires", "catamarca", "chaco", "chubut", "cordoba",
            "corrientes", "entre rios", "formosa", "jujuy", "la pampa",
            "la rioja", "mendoza", "misiones", "neuquen", "rio negro",
            "salta", "san juan", "san luis", "santa cruz", "santa fe",
            "santiago del estero", "tierra del fuego", "tucuman");

    private String nombre;

    public Provincia(String nombre) {
        String nombreNormalizado = nombre.toLowerCase();

        if (!PROVINCIAS_VALIDAS.contains(nombreNormalizado)) {
            throw new IllegalArgumentException("La provincia ingresada no es valida.");
        }

        this.nombre = nombreNormalizado;
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
        Provincia provincia = (Provincia) o;
        // Como ya normalizamos en el constructor, la comparación es directa
        return Objects.equals(nombre, provincia.nombre);
    }

    @Override
    public int hashCode() {
        // Al estar normalizado, el hashCode funciona correctamente
        return Objects.hash(nombre);
    }

    @Override
    public String toString() {
        // Capitaliza la primera letra para mostrarla más prolija si querés,
        // o simplemente devolver el nombre normalizado.
        return nombre;
    }
}

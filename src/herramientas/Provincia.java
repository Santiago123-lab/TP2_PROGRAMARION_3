package herramientas;

public class Provincia {

    private String nombre;

    public Provincia(String nombre) {
    	
    	if(!esProvincia(nombre.toLowerCase())) {
    		
    		throw new IllegalArgumentException ("La provincia ingresada no es valida.");
    	}
    	
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre.toLowerCase();
    }
    
    private boolean esProvincia(String nombre) {
        return nombre.equals("buenos aires")
            || nombre.equals("catamarca")
            || nombre.equals("chaco")
            || nombre.equals("chubut")
            || nombre.equals("cordoba")
            || nombre.equals("corrientes")
            || nombre.equals("entre rios")
            || nombre.equals("formosa")
            || nombre.equals("jujuy")
            || nombre.equals("la pampa")
            || nombre.equals("la rioja")
            || nombre.equals("mendoza")
            || nombre.equals("misiones")
            || nombre.equals("neuquen")
            || nombre.equals("rio negro")
            || nombre.equals("salta")
            || nombre.equals("san juan")
            || nombre.equals("san luis")
            || nombre.equals("santa cruz")
            || nombre.equals("santa fe")
            || nombre.equals("santiago del estero")
            || nombre.equals("tierra del fuego")
            || nombre.equals("tucuman");
            
    }
    
    @Override
    public boolean equals(Object provincia) {
    	
        if (this == provincia) {
            return true;
        }

        if (provincia == null || getClass() != provincia.getClass()) {
            return false;
        }

        Provincia otra = (Provincia) provincia;

        return nombre.toLowerCase().equals(otra.nombre.toLowerCase());
    }
    
    @Override
    public int hashCode() {
        return nombre.hashCode();
    }
}
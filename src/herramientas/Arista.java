package herramientas;

public class Arista {

    private Provincia provincia1;
    private Provincia provincia2;
    private double similaridad;

    public Arista(Provincia provincia1,Provincia provincia2,double similaridad) {

        this.provincia1 = provincia1;
        this.provincia2 = provincia2;
        this.similaridad = similaridad;
    }

    public Provincia getProvincia1() {
        return provincia1;
    }

    public Provincia getProvincia2() {
        return provincia2;
    }

    public double getSimilaridad() {
        return similaridad;
    }
}

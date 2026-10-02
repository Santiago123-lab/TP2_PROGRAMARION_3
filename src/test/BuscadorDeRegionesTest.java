package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import herramientas.BuscadorDeRegiones;
import herramientas.Grafo;
import herramientas.Nodo;
import herramientas.Provincia;
import herramientas.Region;
import java.util.List;

public class BuscadorDeRegionesTest {

    private Grafo<Provincia> mst;
    private BuscadorDeRegiones buscador;

    @Before
    public void setUp() {
        mst = new Grafo<>();
        buscador = new BuscadorDeRegiones();
        
        Nodo<Provincia> bsas = mst.agregarNodo(new Provincia("Buenos Aires"));
        Nodo<Provincia> cordoba = mst.agregarNodo(new Provincia("Cordoba"));
        Nodo<Provincia> santaFe = mst.agregarNodo(new Provincia("Santa Fe"));

        
        mst.agregarArista(bsas, cordoba, 10.0);
        mst.agregarArista(cordoba, santaFe, 20.0);
    }

    @Test
    public void testAgruparEnDosRegiones() {
        List<Region> regiones = buscador.agrupar(mst, 2);
        
        assertEquals("Deberían haberse formado exactamente 2 regiones", 2, regiones.size());
    }
    
    @Test
    public void testAgruparEnUnaRegionDevuelveTodoJunto() {
        List<Region> regiones = buscador.agrupar(mst, 1);
        
        assertEquals("Debería haber quedado 1 sola región", 1, regiones.size());
        assertEquals("La región debe contener las 3 provincias", 3, regiones.get(0).getProvincias().size());
    }
}
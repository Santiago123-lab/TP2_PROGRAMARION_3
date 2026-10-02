package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import algoritmos.BuscadorDeRegiones;
import estructuras.Provincia;
import estructuras.Region;
import grafo.Grafo;
import grafo.Nodo;

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
    
    @Test
    public void testAgruparEnTresRegiones() {
    	
        List<Region> regiones = buscador.agrupar(mst, 3);

        assertEquals(3, regiones.size());

        for (Region region : regiones) {
        	
            assertEquals(1, region.getProvincias().size());
        }
    }
    
    @Test
    public void testDosProvinciasJuntas() {
    	
        List<Region> regiones = buscador.agrupar(mst, 2);

        boolean estanJuntas = false;

        for (Region region : regiones) {
        	
            if (region.getProvincias().contains(new Provincia("Buenos Aires"))
               && region.getProvincias().contains(new Provincia("Cordoba"))) {
            	
                estanJuntas = true;
            }
        }

        assertTrue(estanJuntas);
    }
    
    @Test
    public void testDosProvinciasSeparadas() {
    	
        List<Region> regiones = buscador.agrupar(mst, 2);

        boolean santaFeSeparada = false;

        for (Region region : regiones) {
        	
            if (region.getProvincias().contains(new Provincia("Santa Fe"))
               && !region.getProvincias().contains(new Provincia("Cordoba"))) {
            	
                santaFeSeparada = true;
            }
        }

        assertTrue(santaFeSeparada);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCantidadDeRegionesCero() {
    	
        buscador.agrupar(mst, 0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCantidadDeRegionesMayorACantidadDeProvincias() {
    	
        buscador.agrupar(mst, 4);
    }
}
















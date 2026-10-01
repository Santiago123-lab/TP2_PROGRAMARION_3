package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import herramientas.Grafo;
import herramientas.Nodo;
import herramientas.Arista;
import herramientas.Adyacencia;

import java.util.List;

public class GrafoTest {

    private Grafo<String> grafo;
    private Nodo<String> nodoA;
    private Nodo<String> nodoB;
    private Nodo<String> nodoC;

    @Before
    public void setUp() {
        grafo = new Grafo<>();
        nodoA = grafo.agregarNodo("A");
        nodoB = grafo.agregarNodo("B");
        nodoC = grafo.agregarNodo("C");
    }

    @Test
    public void testAgregarNodoYVerificarInexistenciaDeVecinos() {
        Nodo<String> nodoD = grafo.agregarNodo("D");
        assertNotNull(nodoD);
        assertEquals("D", nodoD.getValor());
        
        List<Adyacencia<String>> vecinos = grafo.getVecinos(nodoD);
        assertTrue(vecinos.isEmpty());
    }

    @Test
    public void testAgregarAristaYObtenerVecinos() {
        grafo.agregarArista(nodoA, nodoB, 10.5);
        grafo.agregarArista(nodoA, nodoC, 5.0);
        
        List<Adyacencia<String>> vecinosA = grafo.getVecinos(nodoA);
        List<Adyacencia<String>> vecinosB = grafo.getVecinos(nodoB);
        
        assertEquals(2, vecinosA.size());
        
        boolean contieneB = false;
        boolean contieneC = false;
        
        for (Adyacencia<String> adyacencia : vecinosA) {
            if (adyacencia.getDestino().equals(nodoB) && adyacencia.getPeso() == 10.5) {
                contieneB = true;
            }
            if (adyacencia.getDestino().equals(nodoC) && adyacencia.getPeso() == 5.0) {
                contieneC = true;
            }
        }
        
        assertTrue(contieneB);
        assertTrue(contieneC);
        
        assertEquals(1, vecinosB.size());
        assertEquals(nodoA, vecinosB.get(0).getDestino());
        assertEquals(10.5, vecinosB.get(0).getPeso(), 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarAristaQueYaExisteLanzaExcepcion() {
        grafo.agregarArista(nodoA, nodoB, 5.0);
        
        grafo.agregarArista(nodoA, nodoB, 10.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarAristaAlMismoNodoLanzaExcepcion() {
        grafo.agregarArista(nodoA, nodoA, 1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarAristaConNodosInexistentesLanzaExcepcion() {
        Nodo<String> nodoExterno1 = new Nodo<>("X");
        Nodo<String> nodoExterno2 = new Nodo<>("Y");
        
        grafo.agregarArista(nodoExterno1, nodoExterno2, 3.0);
    }
    
    @Test
    public void testObtenerAristasUnicas() {
    	
        grafo.agregarArista(nodoA, nodoB, 10.0);
        grafo.agregarArista(nodoA, nodoC, 5.0);

        List<Arista<String>> aristas = grafo.getTodasLasAristas();

        assertEquals(2, aristas.size());

        boolean existeAB = false;
        boolean existeAC = false;

        for (Arista<String> arista : aristas) {
        	
            if (arista.getOrigen().equals(nodoA)
            		
                    && arista.getDestino().equals(nodoB)
                    
                    && arista.getPeso() == 10.0) {
            	
                existeAB = true;
            }

            if (arista.getOrigen().equals(nodoA)
            		
                    && arista.getDestino().equals(nodoC)
                    
                    && arista.getPeso() == 5.0) {
            	
                existeAC = true;
            }
        }

        assertTrue(existeAB);
        assertTrue(existeAC);
    }
}

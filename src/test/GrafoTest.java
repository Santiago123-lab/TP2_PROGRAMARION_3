package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import herramientas.Grafo;
import herramientas.Nodo;
import herramientas.Arista;

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
        
        // Un nodo recién agregado no debería tener vecinos
        List<Arista<String>> vecinos = grafo.getVecinos(nodoD);
        assertTrue(vecinos.isEmpty());
    }

    @Test
    public void testAgregarAristaYObtenerVecinos() {
        grafo.agregarArista(nodoA, nodoB, 10.5);
        grafo.agregarArista(nodoA, nodoC, 5.0);
        
        List<Arista<String>> vecinosA = grafo.getVecinos(nodoA);
        List<Arista<String>> vecinosB = grafo.getVecinos(nodoB);
        
        // A debería tener 2 vecinos (B y C)
        assertEquals(2, vecinosA.size());
        
        boolean contieneB = false;
        boolean contieneC = false;
        
        for (Arista<String> arista : vecinosA) {
            if (arista.getDestino().equals(nodoB) && arista.getPeso() == 10.5) {
                contieneB = true;
            }
            if (arista.getDestino().equals(nodoC) && arista.getPeso() == 5.0) {
                contieneC = true;
            }
        }
        
        assertTrue(contieneB);
        assertTrue(contieneC);
        
        // B debería tener 1 vecino (A)
        assertEquals(1, vecinosB.size());
        assertEquals(nodoA, vecinosB.get(0).getDestino());
        assertEquals(10.5, vecinosB.get(0).getPeso(), 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarAristaQueYaExisteLanzaExcepcion() {
        grafo.agregarArista(nodoA, nodoB, 5.0);
        
        // Intentar agregar la misma arista nuevamente (relación A-B)
        // debería lanzar excepción ya que el grafo no admite aristas duplicadas
        grafo.agregarArista(nodoA, nodoB, 10.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarAristaAlMismoNodoLanzaExcepcion() {
        // Relación de un nodo consigo mismo (bucle)
        grafo.agregarArista(nodoA, nodoA, 1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarAristaConNodosInexistentesLanzaExcepcion() {
        Nodo<String> nodoExterno1 = new Nodo<>("X");
        Nodo<String> nodoExterno2 = new Nodo<>("Y");
        
        // Estos nodos no fueron agregados al grafo mediante grafo.agregarNodo(), 
        // por lo tanto no se les puede crear una arista
        grafo.agregarArista(nodoExterno1, nodoExterno2, 3.0);
    }
}

package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import algoritmos.DSU;
import grafo.Nodo;

import java.util.Arrays;

public class DSUTest {

    private DSU<String> dsu;
    private Nodo<String> nodoA, nodoB, nodoC, nodoD, nodoE;

    @Before
    public void setUp() {
        dsu = new DSU<>();
        nodoA = new Nodo<>("A");
        nodoB = new Nodo<>("B");
        nodoC = new Nodo<>("C");
        nodoD = new Nodo<>("D");
        nodoE = new Nodo<>("E");

        dsu.inicializar(Arrays.asList(nodoA, nodoB, nodoC, nodoD, nodoE));
    }

    @Test
    public void testInicializacion() {
        assertFalse(dsu.connected("A", "B"));
        assertEquals("A", dsu.find("A"));
        assertEquals("B", dsu.find("B"));
    }

    @Test
    public void testUnionYConnected() {
        dsu.union("A", "B");
        dsu.union("C", "D");
        
        assertTrue(dsu.connected("A", "B"));
        assertTrue(dsu.connected("C", "D"));
        assertFalse(dsu.connected("A", "C"));
        
        dsu.union("B", "D");
        
        assertTrue(dsu.connected("A", "C"));
        assertTrue(dsu.connected("B", "C"));
        assertTrue(dsu.connected("A", "D"));
    }

    @Test
    public void testFindYRepresentantes() {
        dsu.union("A", "B");
        dsu.union("C", "D");
        dsu.union("A", "C");
        
        String raizComun = dsu.find("D");
        
        assertEquals(raizComun, dsu.find("A"));
        assertEquals(raizComun, dsu.find("B"));
        assertEquals(raizComun, dsu.find("C"));
        
        assertNotEquals(raizComun, dsu.find("E"));
    }
}

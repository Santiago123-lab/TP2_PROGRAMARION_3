package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import herramientas.Grafo;
import herramientas.Nodo;
import herramientas.Arista;
import herramientas.Kruskal;

import java.util.List;

public class KruskalTest {
	
	private Grafo<String> grafo;
    private Nodo<String> nodoA;
    private Nodo<String> nodoB;
    private Nodo<String> nodoC;
    private Nodo<String> nodoD;

    @Before
    public void setUp() {
        grafo = new Grafo<>();
        nodoA = grafo.agregarNodo("A");
        nodoB = grafo.agregarNodo("B");
        nodoC = grafo.agregarNodo("C");
        nodoD = grafo.agregarNodo("D");
    }

    @Test
    public void testKruskalConCiclo() {
        grafo.agregarArista(nodoA, nodoB, 1.0);
        grafo.agregarArista(nodoB, nodoC, 2.0);
        grafo.agregarArista(nodoC, nodoD, 3.0);
        grafo.agregarArista(nodoD, nodoA, 4.0);

        Grafo<String> mst = Kruskal.calcularMST(grafo);

        assertEquals(4, mst.getTodosLosNodos().size());

        List<Arista<String>> aristasMst = mst.getTodasLasAristas();
        assertEquals(3, aristasMst.size());

        double pesoTotal = aristasMst.stream().mapToDouble(Arista::getPeso).sum();
        assertEquals(6.0, pesoTotal, 0.001);

        boolean contieneDA = aristasMst.stream()
                .anyMatch(a -> (a.getOrigen().getValor().equals("A") && a.getDestino().getValor().equals("D")) ||
                               (a.getOrigen().getValor().equals("D") && a.getDestino().getValor().equals("A")));
        assertFalse(contieneDA);
    }

    @Test
    public void testKruskalGrafoDesconectado() {
        grafo.agregarArista(nodoA, nodoB, 1.0);
        grafo.agregarArista(nodoC, nodoD, 2.0);

        Grafo<String> mst = Kruskal.calcularMST(grafo);

        assertEquals(4, mst.getTodosLosNodos().size());
        
        List<Arista<String>> aristasMst = mst.getTodasLasAristas();
        assertEquals(2, aristasMst.size());
        
        double pesoTotal = aristasMst.stream().mapToDouble(Arista::getPeso).sum();
        assertEquals(3.0, pesoTotal, 0.001);
    }

    @Test
    public void testKruskalGrafoSinAristas() {
        Grafo<String> mst = Kruskal.calcularMST(grafo);
        
        assertEquals(4, mst.getTodosLosNodos().size());
        assertTrue(mst.getTodasLasAristas().isEmpty());
    }

}

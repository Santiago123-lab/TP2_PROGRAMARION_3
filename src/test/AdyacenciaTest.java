package test;

import static org.junit.Assert.*;

import org.junit.Test;

import herramientas.Adyacencia;
import herramientas.Nodo;

public class AdyacenciaTest {

    @Test
    public void testCrearAdyacencia() {
    	
        Nodo<String> nodo = new Nodo<>("B");
        Adyacencia<String> adyacencia = new Adyacencia<>(nodo, 10.5);

        assertEquals(nodo, adyacencia.getDestino());
        assertEquals(10.5, adyacencia.getPeso(), 0.001);
    }

    @Test
    public void testToString() {
    	
        Nodo<String> nodo = new Nodo<>("B");
        Adyacencia<String> adyacencia = new Adyacencia<>(nodo, 10.5);

        assertEquals("Adyacencia{destino=B, peso=10.5}", adyacencia.toString());
    }
}

package test;

import static org.junit.Assert.*;

import org.junit.Test;

import estructuras.Vertice;

public class VerticeTest {
	
	@Test
	public void verticeValido () {
		
		Vertice p = new Vertice ("Mochila");
		
		assertEquals("Mochila", p.getNombre());
	}
	
	@Test (expected = NullPointerException.class)
	
	public void verticeInvalido() {
		
		Vertice p = new Vertice (null);
		
		}
	
	
	@Test
	
	public void verticesIguales() {
		
		Vertice p1 = new Vertice ("CORDOBA");
		Vertice p2 = new Vertice ("cordoba");
		
		assertTrue(p1.equals(p2));
	}
	
	@Test
	
	public void verticesDistintos() {
		
		Vertice p1 = new Vertice ("Chiqui Tapia");
		Vertice p2 = new Vertice ("No trates de entenderla");
		
		assertFalse(p1.equals(p2));
		
	}
	
	
}













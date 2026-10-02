package test;

import static org.junit.Assert.*;

import org.junit.Test;

import estructuras.Provincia;

public class ProvinciaTest {
	
	@Test
	public void provinciaValida () {
		
		Provincia p = new Provincia ("buenos aires");
		
		assertEquals("buenos aires", p.getNombre());
	}
	
	@Test (expected = IllegalArgumentException.class)
	
	public void provinciaInvalida() {
		
		Provincia p = new Provincia ("River plate");
		
		}
	
	@Test 
	
	public void provinciaEscritaMayuscula() {
		
		Provincia p = new Provincia ("BUENOS AIRES");
		
		assertEquals ("buenos aires", p.getNombre());
		
	}
	
	@Test
	
	public void provinciasIguales() {
		
		Provincia p1 = new Provincia ("Cordoba");
		Provincia p2 = new Provincia ("Cordoba");
		
		assertTrue(p1.equals(p2));
	}
	
	@Test
	
	public void provinciasDistintas() {
		
		Provincia p1 = new Provincia ("Cordoba");
		Provincia p2 = new Provincia ("Santa fe");
		
		assertFalse(p1.equals(p2));
		
	}
	
	
}













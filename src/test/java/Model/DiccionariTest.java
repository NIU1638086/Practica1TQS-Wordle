package Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DiccionariTest {

	@Test
	void testDiccionari() {
		Diccionari dic = new Diccionari("palabrasTest.txt");
		assertEquals(4, dic.getNombreParaules());
		
		assertThrows(IllegalStateException.class, () -> {
	        new Diccionari("no_existeix.txt");
	    });
		
	}

	@Test
	void testExisteix() {
		Diccionari dic = new Diccionari("palabrasTest.txt");
		assertTrue(dic.existeix("perro")); //correcte
		assertTrue(dic.existeix("PERRO")); //correcte
		assertFalse(dic.existeix("gatos")); //no esta al .txt
		assertFalse(dic.existeix("zzzzz")); //no esta al .txt
		assertFalse(dic.existeix("12345")); //son numeros no lletres
		assertFalse(dic.existeix("gat")); //menys de 5 lletres
		assertFalse(dic.existeix("ordenador")); //mes de 5 lletres
		assertFalse(dic.existeix(null));
	}

	@Test
	void testGetRandomWord() {
		Diccionari dic = new Diccionari("palabrasTest.txt");
		String p = dic.getRandomWord();
		assertEquals(5, p.length());
		assertTrue(dic.existeix(p));
	}

	@Test
	void testGetNombreParaules() {
		Diccionari dic = new Diccionari("palabrasTest.txt");
	    assertEquals(4, dic.getNombreParaules());
	}

}

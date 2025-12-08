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

	@Test
	void testLoopTesting_FitxerBuit_0Iteracions() {
		// El fitxer buit fa que el bucle while no s'executi cap vegada
		// Això viola l'invariant del Diccionari → ha de llançar excepció
		assertThrows(IllegalStateException.class, () -> {
			new Diccionari("buit.txt");
		}, "Diccionari amb fitxer buit ha de llançar IllegalStateException");
	}
	
	@Test
	void testLoopTesting_UnaParaula_1Iteracio() {
		Diccionari dic = new Diccionari("una_paraula.txt");
		
		// Verificar que s'ha carregat exactament 1 paraula
		assertEquals(1, dic.getNombreParaules());
		
		// Verificar que la paraula és correcta
		assertTrue(dic.existeix("PERRO"));
		
		// Verificar que podem obtenir la paraula
		String paraula = dic.getRandomWord();
		assertEquals("PERRO", paraula);
		assertEquals(5, paraula.length());
	}
	
	@Test
	void testLoopTesting_MultiplesParaules_NIteracions() {
		Diccionari dic = new Diccionari("palabrasTest.txt");
		
		// Verificar que s'han carregat totes les paraules (4 iteracions del bucle)
		assertEquals(4, dic.getNombreParaules());
		
		// Verificar que totes les paraules esperades existeixen
		assertTrue(dic.existeix("PERRO"), "PERRO hauria d'existir");
		assertTrue(dic.existeix("COCHE"), "COCHE hauria d'existir");
		
		// Verificar que podem obtenir paraula aleatòria
		String paraula = dic.getRandomWord();
		assertNotNull(paraula);
		assertEquals(5, paraula.length());
		assertTrue(dic.existeix(paraula));
	}
	
}

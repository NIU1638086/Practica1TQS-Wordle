package Model;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;


class ParaulaSecretaTest {
	
/*

	@Test
	void testParaulaSecretaString() {
		ParaulaSecreta ps = new ParaulaSecreta("PERRO");
	    assertEquals("PERRO", ps.getParaula()); //correcta
	    
	    ParaulaSecreta ps1 = new ParaulaSecreta("perro");
        assertEquals("PERRO", ps1.getParaula()); //correcta minuscula
        
        assertThrows(IllegalArgumentException.class, () -> {
            new ParaulaSecreta((String) null); //paraula null
        });

          
        assertThrows(IllegalArgumentException.class, () -> {
            new ParaulaSecreta("GAT"); //paraula menys de 5 lletres
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new ParaulaSecreta("ORDENADOR"); //paraula mes de 5 lletres
        });
        
        
        assertThrows(IllegalArgumentException.class, () -> {
            new ParaulaSecreta("");
        }); //paraula buida
        
	}
	

	@Test
	void testParaulaSecretaDiccionari() {
		Diccionari mockDicc = mock(Diccionari.class);
        when(mockDicc.getRandomWord()).thenReturn("PERRO");
        
        ParaulaSecreta ps = new ParaulaSecreta(mockDicc);
        
        assertEquals("PERRO", ps.getParaula());
        verify(mockDicc).getRandomWord();
	}

	@Test
	void testComparar() {
		ParaulaSecreta ps = new ParaulaSecreta("PERRO");
        List<EstatLletra> resultat = ps.comparar("PERRO");
        
        assertEquals(5, resultat.size());
        for (EstatLletra estat : resultat) {
            assertEquals(EstatLletra.CORRECTA, estat); //correcta
        }
        
        List<EstatLletra> resultat1 = ps.comparar("FATAL");
        
        assertEquals(5, resultat1.size());
        for (EstatLletra estat : resultat1) {
            assertEquals(EstatLletra.INCORRECTA, estat); //cap lletra correcta
        }
        
        List<EstatLletra> resultat2 = ps.comparar("ROPER");
        assertEquals(EstatLletra.PRESENT, resultat2.get(0)); // R està a posició 0 però hauria d'estar a 2 o 4 -> PRESENT
        assertEquals(EstatLletra.PRESENT, resultat2.get(1)); // O està a posició 1 però hauria d'estar a 4 -> PRESENT
        assertEquals(EstatLletra.PRESENT, resultat2.get(2)); // P està a posició 2 però hauria d'estar a 0 -> PRESENT
        assertEquals(EstatLletra.PRESENT, resultat2.get(3)); // E està a posició 3 però hauria d'estar a 1 -> PRESENT
        assertEquals(EstatLletra.CORRECTA, resultat2.get(4)); // R està a posició 4 i és correcta -> CORRECTA
        
        List<EstatLletra> resultat3 = ps.comparar("PEDRO");
        assertEquals(EstatLletra.CORRECTA, resultat3.get(0)); // P: posició correcta -> CORRECTA
        assertEquals(EstatLletra.CORRECTA, resultat3.get(1)); // E: posició correcta -> CORRECTA
        assertEquals(EstatLletra.INCORRECTA, resultat3.get(2)); // D: no està a la paraula -> INCORRECTA
        assertEquals(EstatLletra.PRESENT, resultat3.get(3)); // R: està present però posició incorrecta -> PRESENT
        assertEquals(EstatLletra.CORRECTA, resultat3.get(4)); // O: posició correcta -> CORRECTA
	
        List<EstatLletra> resultat4 = ps.comparar("RRRRA");
        assertEquals(EstatLletra.PRESENT, resultat4.get(0)); // R posició 0: no està aquí però està a la paraula -> PRESENT (es marca a posició 2)
        assertEquals(EstatLletra.PRESENT, resultat4.get(1)); // R posició 1: no està aquí però està a la paraula -> PRESENT (es marca a posició 4)
        assertEquals(EstatLletra.CORRECTA, resultat4.get(2));  // R posició 2: posició correcta -> CORRECTA
        assertEquals(EstatLletra.INCORRECTA, resultat4.get(3)); // R posició 3: ja no hi ha més R disponibles -> INCORRECTA
        assertEquals(EstatLletra.INCORRECTA, resultat4.get(4)); // A posició 4: no està a la paraula -> INCORRECTA
	
        assertThrows(IllegalArgumentException.class, () -> {
            ps.comparar(null);//intent null
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            ps.comparar("GAT"); //intent menys 5 lletres
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            ps.comparar("ORDENADOR"); //intent mes de 5 lletres
        });
	}

	@Test
	void testEsIgual() {
		ParaulaSecreta ps = new ParaulaSecreta("PERRO");
        assertTrue(ps.esIgual("PERRO")); //paraules iguals
        assertTrue(ps.esIgual("perro"));
        assertTrue(ps.esIgual("PeRrO"));
        assertTrue(ps.esIgual("PERRO"));
        
        assertFalse(ps.esIgual("GATOS")); //paraules diferents
        assertFalse(ps.esIgual("LAGOS")); 
        
        assertFalse(ps.esIgual("PERRI")); //paraules similars pero no correctes
        assertFalse(ps.esIgual("PERRA")); 
        
        
	}
	
	*/

}

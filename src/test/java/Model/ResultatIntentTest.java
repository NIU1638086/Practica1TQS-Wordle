package Model;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class ResultatIntentTest {
	
	@Test
	void testResultatIntent() {
		// Correcte
		List<EstatLletra> estats = Arrays.asList(
                EstatLletra.CORRECTA,
                EstatLletra.INCORRECTA,
                EstatLletra.PRESENT,
                EstatLletra.INCORRECTA,
                EstatLletra.CORRECTA
        );
		ResultatIntent r = new ResultatIntent("PERRO", estats); 
		assertEquals("PERRO", r.getIntent());
		assertEquals(estats, r.getEstats());
		
		// Modifiquem estats fora de la classe
        estats.set(0, EstatLletra.INCORRECTA);
        // El resultat guardat no hauria de canviar
        assertEquals(EstatLletra.CORRECTA, r.getEstats().get(0));
		
		// paraula null
        assertThrows(IllegalArgumentException.class, () -> {
            new ResultatIntent(null, estats);
        });
        
        //estat null
        assertThrows(IllegalArgumentException.class, () -> {
            new ResultatIntent("CASAS", null);
        });
        
        // num lletres incorrcta
        List<EstatLletra> estats2 = Arrays.asList(EstatLletra.CORRECTA);
        assertThrows(IllegalArgumentException.class, () -> {
            new ResultatIntent("CASAS", estats2);
        });
		
	}
	
	@Test
	void  testGetIntent() {
		
		List<EstatLletra> estats = Arrays.asList(
	            EstatLletra.CORRECTA,
	            EstatLletra.CORRECTA,
	            EstatLletra.CORRECTA,
	            EstatLletra.CORRECTA,
	            EstatLletra.CORRECTA
	    );

	    ResultatIntent r = new ResultatIntent("PERRO", estats);

	    assertEquals("PERRO", r.getIntent());
	    
    }
    
	@Test
    void testGetEstats() {
		
		List<EstatLletra> estats = Arrays.asList(
	            EstatLletra.INCORRECTA,
	            EstatLletra.PRESENT,
	            EstatLletra.CORRECTA,
	            EstatLletra.PRESENT,
	            EstatLletra.INCORRECTA
	    );

	    ResultatIntent r = new ResultatIntent("GATOS", estats);

	    assertEquals(estats, r.getEstats());

	    // comprovem que la llista retornada és immutable
	    assertThrows(UnsupportedOperationException.class, () -> {
	        r.getEstats().add(EstatLletra.CORRECTA);
	    });
    }

	@Test
	void testEsCorrecte() {
		// correcta
		List<EstatLletra> estatsCorrectes = Arrays.asList(
                EstatLletra.CORRECTA,
                EstatLletra.CORRECTA,
                EstatLletra.CORRECTA,
                EstatLletra.CORRECTA,
                EstatLletra.CORRECTA
        );

        ResultatIntent r = new ResultatIntent("PERRO", estatsCorrectes);
        assertTrue(r.esCorrecte());
        
        //incorrecta
        List<EstatLletra> estatsIncorrectes = Arrays.asList(
                EstatLletra.CORRECTA,
                EstatLletra.PRESENT,
                EstatLletra.CORRECTA,
                EstatLletra.CORRECTA,
                EstatLletra.CORRECTA
        );

        ResultatIntent r1 = new ResultatIntent("PERRO", estatsIncorrectes);
        assertFalse(r1.esCorrecte());
	}

	@Test
	void testToString() {
		
		List<EstatLletra> estats = Arrays.asList(
                EstatLletra.CORRECTA, 
                EstatLletra.INCORRECTA, 
                EstatLletra.PRESENT,
                EstatLletra.INCORRECTA, 
                EstatLletra.CORRECTA
        );
        ResultatIntent r = new ResultatIntent("PERRO", estats);
        
     // Comprovem que el toString no és null i conté la paraula i informació dels estats
        String resultatString = r.toString();
        assertNotNull(resultatString);
        assertTrue(resultatString.contains("PERRO"));
        assertTrue(resultatString.contains("CORRECTA"));
        
	}

	@Test
	void testEqualsObject() {
		
		List<EstatLletra> estats = Arrays.asList(
                EstatLletra.CORRECTA, 
                EstatLletra.CORRECTA, 
                EstatLletra.CORRECTA,
                EstatLletra.CORRECTA, 
                EstatLletra.CORRECTA
        );
        
        // Dos objectes iguals
        ResultatIntent r1 = new ResultatIntent("PERRO", estats);
        ResultatIntent r2 = new ResultatIntent("PERRO", estats);
        
        assertEquals(r1, r2);
        
        // Objectes diferents
        ResultatIntent r3 = new ResultatIntent("GATOS", estats);
        assertNotEquals(r1, r3);
        
        assertNotEquals(r1, null);
        assertNotEquals(r1, "Una String qualsevol");
        
        // estats son diferents
        List<EstatLletra> estats1 = Arrays.asList(
                EstatLletra.INCORRECTA, 
                EstatLletra.CORRECTA, 
                EstatLletra.CORRECTA,
                EstatLletra.CORRECTA, 
                EstatLletra.CORRECTA
        );

            ResultatIntent r4 = new ResultatIntent("PERRO", estats);
            ResultatIntent r5 = new ResultatIntent("PERRO", estats1);

            assertNotEquals(r4, r5);
	}
	
	@Test
	void testHashCode() {
		List<EstatLletra> estats = Arrays.asList(
                EstatLletra.CORRECTA, 
                EstatLletra.CORRECTA, 
                EstatLletra.CORRECTA,
                EstatLletra.CORRECTA, 
                EstatLletra.CORRECTA
        );
        
        ResultatIntent r1 = new ResultatIntent("PERRO", estats);
        ResultatIntent r2 = new ResultatIntent("PERRO", estats);
        
        // Objectes iguals == mateix hashCode
        assertEquals(r1.hashCode(), r2.hashCode());
        
        // Objectes diferents != hashCode diferent
        ResultatIntent r3 = new ResultatIntent("GATOS", estats);
        assertNotEquals(r1.hashCode(), r3.hashCode());
	}
	
}

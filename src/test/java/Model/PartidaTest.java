package Model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class PartidaTest {

	private Partida partida;
	private ParaulaSecreta mockParaulaSecreta;
	private Diccionari mockDiccionari;
	
	@BeforeEach
	void setUp() {
		mockDiccionari = mock(Diccionari.class);
		mockParaulaSecreta = mock(ParaulaSecreta.class);
		
		when(mockParaulaSecreta.getParaula()).thenReturn("TESTS");
		when(mockDiccionari.getRandomWord()).thenReturn("RANDM");
	}
	
	@Test
	void testPartida() {
		Partida p = new Partida(mockParaulaSecreta, mockDiccionari);
        assertNotNull(p);
        assertEquals(0, p.getIntents().size());
        assertEquals(6, p.getIntentsRestants()); // Assumim 6 intents per defecte
        assertEquals("TESTS", p.getParaulaSecreta());
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Partida(null, mockDiccionari);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Partida(mockParaulaSecreta, null);
        });

	}

	@Test
	void testPartidaDiccionari() {
		Partida p = new Partida(mockDiccionari);
        assertNotNull(p);
        assertNotNull(p.getParaulaSecreta()); // Ha d'haver creat una paraula
        assertEquals(0, p.getIntents().size());
        // Verifiquem que ha cridat al diccionari per agafar una paraula random
        verify(mockDiccionari, atLeastOnce()).getRandomWord();
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Partida(null);
        });
	}

	@ParameterizedTest(name = "Intent: ''{0}'' -> Hauria de ser vàlid: {1}")
	@CsvSource({
	    "TESTS, true",
	    "HELLO, true",
	    "ABC, false",
	    "123456, false",
	    "ZZZZZ, false",
	    "12345, false"
	})
	void testValidarInput(String intent, boolean esperat) {

	    // Configurem el mock
	    when(mockDiccionari.existeix("TESTS")).thenReturn(true);
	    when(mockDiccionari.existeix("HELLO")).thenReturn(true);

	    boolean resultat = partida.validarInput(intent);
	    assertEquals(esperat, resultat, "Error validant l'input: " + intent);
	}

	@Test
	void testAfegirIntent() {
		// cas guanyador
        when(mockDiccionari.existeix("TESTS")).thenReturn(true);
        List<EstatLletra> estatsGuanyadors = 
        		List.of(EstatLletra.CORRECTA, 
        				EstatLletra.CORRECTA, 
        				EstatLletra.CORRECTA, 
        				EstatLletra.CORRECTA, 
        				EstatLletra.CORRECTA);
        when(mockParaulaSecreta.comparar("TESTS")).thenReturn(estatsGuanyadors);
        when(mockParaulaSecreta.esIgual("TESTS")).thenReturn(true);
        ResultatIntent resultat = partida.afegirIntent("TESTS");
        assertNotNull(resultat);
        assertTrue(resultat.esCorrecte());
        assertTrue(partida.isWon());
        assertTrue(partida.isGameOver());
        assertEquals(5, partida.getIntentsRestants());
        
        // cas perdedor
        when(mockDiccionari.existeix(anyString())).thenReturn(true);
        when(mockParaulaSecreta.esIgual(anyString())).thenReturn(false);
        when(mockParaulaSecreta.comparar(anyString())).thenReturn(List.of(
        		EstatLletra.INCORRECTA, 
        		EstatLletra.INCORRECTA, 
        		EstatLletra.INCORRECTA, 
        		EstatLletra.INCORRECTA, 
        		EstatLletra.INCORRECTA));
        for (int i = 0; i < 5; i++) {
            partida.afegirIntent("ERROR");
            assertFalse(partida.isGameOver());
        }
        assertEquals(1, partida.getIntentsRestants());
        partida.afegirIntent("FINAL");
        assertEquals(0, partida.getIntentsRestants());
        assertTrue(partida.isGameOver());
        assertFalse(partida.isWon());
	}

	@Test
	void testIsWon() {
		// 1. Encara ningú ha jugat → NO guanyat
	    assertFalse(partida.isWon());

	    // 2. Simulem un intent incorrecte
        when(mockDiccionari.existeix("XXXXX")).thenReturn(true);
        // Retornem algo que no sigui tot verd
        List<EstatLletra> incorrecte = Arrays.asList(
	            EstatLletra.CORRECTA, 
	            EstatLletra.INCORRECTA, 
	            EstatLletra.PRESENT, 
	            EstatLletra.INCORRECTA, 
	            EstatLletra.CORRECTA
	    );
        when(mockParaulaSecreta.comparar("XXXXX")).thenReturn(incorrecte);
        when(mockParaulaSecreta.esIgual("XXXXX")).thenReturn(false);

	    partida.afegirIntent("XXXXX");
	    assertFalse(partida.isWon());

        when(mockDiccionari.existeix("TESTS")).thenReturn(true);
	    List<EstatLletra> correcte = Arrays.asList(
	            EstatLletra.CORRECTA, 
	            EstatLletra.CORRECTA, 
	            EstatLletra.CORRECTA, 
	            EstatLletra.CORRECTA, 
	            EstatLletra.CORRECTA
	    );
        when(mockParaulaSecreta.comparar("TESTS")).thenReturn(correcte);
        when(mockParaulaSecreta.esIgual("TESTS")).thenReturn(true); 

	    partida.afegirIntent("TESTS");
	    assertTrue(partida.isWon());
	}

	@Test
	void testIsGameOver() {
		// 1. No ha guanyat i encara queden intents
	    assertFalse(partida.isGameOver());

	    // 2. Quan guanya → game over
	    List<EstatLletra> correcte = Arrays.asList(
	            EstatLletra.CORRECTA, 
	            EstatLletra.CORRECTA,
	            EstatLletra.CORRECTA,
	            EstatLletra.CORRECTA, 
	            EstatLletra.CORRECTA
	    );

	    when(mockParaulaSecreta.comparar("TESTS")).thenReturn(correcte);

	    partida.afegirIntent("TESTS"); // Guanya

	    assertTrue(partida.isGameOver());

	    // 3. Quan s'acaben els intents
	    setUp(); // reset partida

	    when(mockDiccionari.existeix(anyString())).thenReturn(true);

	    for (int i = 0; i < 6; i++) {
	        // retornem incorrecte sempre
	        when(mockParaulaSecreta.comparar(anyString()))
	                .thenReturn(Arrays.asList(
	                        EstatLletra.INCORRECTA, 
	                        EstatLletra.INCORRECTA,
	                        EstatLletra.INCORRECTA, 
	                        EstatLletra.INCORRECTA,
	                        EstatLletra.INCORRECTA
	                ));
	        partida.afegirIntent("XXXXX");
	    }

	    assertTrue(partida.isGameOver());
	}

	@Test
	void testGetIntents() {
        when(mockDiccionari.existeix("TESTS")).thenReturn(true);
        when(mockParaulaSecreta.comparar(anyString())).thenReturn(List.of());
        partida.afegirIntent("TESTS");
        List<ResultatIntent> llista = partida.getIntents();
        assertEquals(1, llista.size());
        try {
            llista.clear(); 
        } catch (UnsupportedOperationException e) { }
        assertEquals(1, partida.getIntents().size());
	}

	@Test
	void testGetIntentsRestants() {
		assertEquals(6, partida.getIntentsRestants());

	    when(mockDiccionari.existeix(anyString())).thenReturn(true);

	    when(mockParaulaSecreta.comparar(anyString()))
	            .thenReturn(Arrays.asList(
	                    EstatLletra.INCORRECTA,
	                    EstatLletra.INCORRECTA,
	                    EstatLletra.INCORRECTA, 
	                    EstatLletra.INCORRECTA,
	                    EstatLletra.INCORRECTA
	            ));

	    partida.afegirIntent("XXXXX");
	    assertEquals(5, partida.getIntentsRestants());

	    partida.afegirIntent("XXXXX");
	    assertEquals(4, partida.getIntentsRestants());
	}

}


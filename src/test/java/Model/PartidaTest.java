package Model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PartidaTest {

	private Partida partida;
	private ParaulaSecreta mockParaulaSecreta;
	private Diccionari mockDiccionari;
	
	private final List<EstatLletra> ESTATS_GUANYADORS = Arrays.asList(
            EstatLletra.CORRECTA, 
            EstatLletra.CORRECTA, 
            EstatLletra.CORRECTA, 
            EstatLletra.CORRECTA, 
            EstatLletra.CORRECTA
    );
    
    private final List<EstatLletra> ESTATS_FALLATS = Arrays.asList(
            EstatLletra.INCORRECTA, 
            EstatLletra.INCORRECTA, 
            EstatLletra.INCORRECTA, 
            EstatLletra.INCORRECTA, 
            EstatLletra.INCORRECTA
    );
	
	@BeforeEach
	void setUp() {
		mockDiccionari = mock(Diccionari.class);
		mockParaulaSecreta = mock(ParaulaSecreta.class);
		
		when(mockParaulaSecreta.getParaula()).thenReturn("TESTS");
		when(mockDiccionari.getRandomWord()).thenReturn("RANDM");
		
		partida = new Partida(mockParaulaSecreta, mockDiccionari);
		
	}
	
	@Test
	void testPartida() {
		// estat inicial correcte
		Partida p = new Partida(mockParaulaSecreta, mockDiccionari);
        assertNotNull(p);
        assertEquals(0, p.getIntents().size());
        assertEquals(6, p.getIntentsRestants());
        assertEquals("TESTS", p.getParaulaSecreta());
        
        // valors nulls                                                                        
        assertThrows(IllegalArgumentException.class, () -> {
            new Partida(null, mockDiccionari);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Partida(mockParaulaSecreta, null);
        });

	}

	@Test
	void testPartidaDiccionari() {
		// estat inicial correcte
		Partida p = new Partida(mockDiccionari);
        assertNotNull(p);
        assertNotNull(p.getParaulaSecreta());
        assertEquals(0, p.getIntents().size());
        verify(mockDiccionari, atLeastOnce()).getRandomWord();
        
     // valor null
        assertThrows(IllegalArgumentException.class, () -> {
            new Partida(null);
        });
	}
	
	// test parametritzat per validar l'entrada de l'usuari
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
		// configurem el mock perquè només algunes paraules existeixin
	    when(mockDiccionari.existeix("TESTS")).thenReturn(true);
	    when(mockDiccionari.existeix("HELLO")).thenReturn(true);

	    boolean resultat = partida.validarInput(intent);
	    assertEquals(esperat, resultat);
	}

	@Test
	void testAfegirIntent() {
		// cas guanyador
        when(mockDiccionari.existeix("TESTS")).thenReturn(true);
        when(mockParaulaSecreta.comparar("TESTS")).thenReturn(ESTATS_GUANYADORS);
        when(mockParaulaSecreta.esIgual("TESTS")).thenReturn(true);
        ResultatIntent resultat = partida.afegirIntent("TESTS");
        assertNotNull(resultat);
        assertTrue(resultat.esCorrecte());
        assertTrue(partida.isWon());
        assertTrue(partida.isGameOver());
        assertEquals(5, partida.getIntentsRestants());
        
        setUp();
        
        // cas perdedor
        when(mockDiccionari.existeix(anyString())).thenReturn(true);
        when(mockParaulaSecreta.esIgual(anyString())).thenReturn(false);
        when(mockParaulaSecreta.comparar(anyString())).thenReturn(ESTATS_FALLATS);
        
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
		
	    assertFalse(partida.isWon());

	    // intent fallit
        when(mockDiccionari.existeix("XXXXX")).thenReturn(true);
        when(mockParaulaSecreta.comparar("XXXXX")).thenReturn(ESTATS_FALLATS);
        when(mockParaulaSecreta.esIgual("XXXXX")).thenReturn(false);

	    partida.afegirIntent("XXXXX");
	    assertFalse(partida.isWon());

	    // intent correcte
        when(mockDiccionari.existeix("TESTS")).thenReturn(true);
        when(mockParaulaSecreta.comparar("TESTS")).thenReturn(ESTATS_GUANYADORS);
        when(mockParaulaSecreta.esIgual("TESTS")).thenReturn(true); 

	    partida.afegirIntent("TESTS");
	    assertTrue(partida.isWon());
	}

	@Test
	void testIsGameOver() {
		
	    assertFalse(partida.isGameOver());
	    
	    // gameover per guanyar
	    when(mockDiccionari.existeix("TESTS")).thenReturn(true);
        when(mockParaulaSecreta.comparar("TESTS")).thenReturn(ESTATS_GUANYADORS);
        when(mockParaulaSecreta.esIgual("TESTS")).thenReturn(true);

	    partida.afegirIntent("TESTS");
	    assertTrue(partida.isGameOver());

	    setUp();

	    // gameover per intents = 0
	    when(mockDiccionari.existeix(anyString())).thenReturn(true);
        when(mockParaulaSecreta.comparar(anyString())).thenReturn(ESTATS_FALLATS);
        when(mockParaulaSecreta.esIgual(anyString())).thenReturn(false);

        for (int i = 0; i < 6; i++) {
            partida.afegirIntent("XXXXX");
        }

        assertTrue(partida.isGameOver());
	}

	@Test
	void testGetIntents() {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
        when(mockDiccionari.existeix("TESTS")).thenReturn(true);
        when(mockParaulaSecreta.comparar(anyString())).thenReturn(ESTATS_FALLATS);
        
        partida.afegirIntent("TESTS");
        
        List<ResultatIntent> llista = partida.getIntents();
        assertEquals(1, llista.size());
        assertThrows(UnsupportedOperationException.class, () -> llista.clear());
	}

	@Test
	void testGetIntentsRestants() {
		assertEquals(6, partida.getIntentsRestants());

	    when(mockDiccionari.existeix(anyString())).thenReturn(true);
	    when(mockParaulaSecreta.comparar(anyString())).thenReturn(ESTATS_FALLATS);

	    partida.afegirIntent("XXXXX");
	    assertEquals(5, partida.getIntentsRestants());

	    partida.afegirIntent("XXXXX");
	    assertEquals(4, partida.getIntentsRestants());
	}

}


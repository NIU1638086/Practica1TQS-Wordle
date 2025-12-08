package View;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import Model.Partida;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

class VistaWordleTest {

    private VistaWordle vista;
    private Partida mockPartida;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        vista = new VistaWordle();
        mockPartida = mock(Partida.class);
        
        // Capturar System.out
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        // Restaurar System.out
        System.setOut(originalOut);
    }

    
    @Test
    void testConstructor() {
        VistaWordle v = new VistaWordle();
        
        assertNotNull(v);
        assertDoesNotThrow(() -> new VistaWordle());
    }

    
    @Test
    void testMostrarBenvinguda() {
        assertDoesNotThrow(() -> vista.mostrarBenvinguda());
        
        vista.mostrarBenvinguda();
        String output = outputStream.toString();
        
        assertFalse(output.isEmpty());
        assertTrue(output.length() > 0);
    }

    
    @Test
    void testMostrarTauler() {
        // Configurar mock
        when(mockPartida.getIntents()).thenReturn(new ArrayList<>());
        when(mockPartida.getMaxIntents()).thenReturn(6);
        
        assertDoesNotThrow(() -> vista.mostrarTauler(mockPartida));
        
        vista.mostrarTauler(mockPartida);
        String output = outputStream.toString();
        
        assertFalse(output.isEmpty());
        verify(mockPartida, atLeastOnce()).getIntents();
    }

    
    @Test
    void testMostrarSeparador() {
        assertDoesNotThrow(() -> vista.mostrarSeparador());
        
        vista.mostrarSeparador();
        String output = outputStream.toString();
        
        assertFalse(output.isEmpty());
        assertTrue(output.length() > 0);
    }

    
    @Test
    void testDemanarIntent() {
        assertDoesNotThrow(() -> vista.demanarIntent(6));
        assertDoesNotThrow(() -> vista.demanarIntent(5));
        assertDoesNotThrow(() -> vista.demanarIntent(1));
        assertDoesNotThrow(() -> vista.demanarIntent(0));
        
        vista.demanarIntent(6);
        String output = outputStream.toString();
        
        assertFalse(output.isEmpty());
        assertTrue(output.length() > 0);
    }

    @Test
    void testMostrarMissatgeVictoria() {
        assertDoesNotThrow(() -> vista.mostrarMissatgeVictoria());
        
        vista.mostrarMissatgeVictoria();
        String output = outputStream.toString();
        
        assertFalse(output.isEmpty());
        assertTrue(output.length() > 0);
    }

    @Test
    void testMostrarMissatgeDerrota() {
        assertDoesNotThrow(() -> vista.mostrarMissatgeDerrota("PERRO"));
        assertDoesNotThrow(() -> vista.mostrarMissatgeDerrota("GATOS"));
        assertDoesNotThrow(() -> vista.mostrarMissatgeDerrota("LAGOS"));
        
        vista.mostrarMissatgeDerrota("PERRO");
        String output = outputStream.toString();
        
        assertFalse(output.isEmpty());
        assertTrue(output.length() > 0);
        assertTrue(output.contains("PERRO") || output.length() > 10);
    }

    
    @Test
    void testMostrarErrorParaulaInvalida() {
        assertDoesNotThrow(() -> vista.mostrarErrorParaulaInvalida());
        
        vista.mostrarErrorParaulaInvalida();
        String output = outputStream.toString();
        
        assertFalse(output.isEmpty());
        assertTrue(output.length() > 0);
    }

    
    @Test
    void testMostrarAjuda() {
        assertDoesNotThrow(() -> vista.mostrarAjuda());
        
        vista.mostrarAjuda();
        String output = outputStream.toString();
        
        assertFalse(output.isEmpty());
        assertTrue(output.length() > 0);
    }
}
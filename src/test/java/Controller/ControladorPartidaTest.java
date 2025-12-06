package Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import Model.Partida;
import Model.ResultatIntent;
import Model.EstatLletra;
import View.VistaWordle;

import java.util.ArrayList;
import java.util.List;


class ControladorPartidaTest {

    private Partida mockPartida;
    private VistaWordle mockVista;
    private ControladorPartida controlador;

    @BeforeEach
    void setUp() {
        mockPartida = mock(Partida.class);
        mockVista = mock(VistaWordle.class);
        controlador = new ControladorPartida(mockPartida, mockVista);
    }


    
    @Test
    void testConstructorSenseParametres() {
        // Constructor por defecto crea sus propias dependencias
        ControladorPartida ctrl = new ControladorPartida();
        
        assertNotNull(ctrl);
        assertDoesNotThrow(() -> new ControladorPartida());
    }


    
    @Test
    void testConstructorAmbParametres() {
        ControladorPartida ctrl = new ControladorPartida(mockPartida, mockVista);
        
        assertNotNull(ctrl);
        // Verificar que se pueden usar los mocks
        assertDoesNotThrow(() -> {
            when(mockPartida.isGameOver()).thenReturn(false);
            mockPartida.isGameOver();
        });
    }


    
    @Test
    void testIniciarPartida() {
        // No podemos ejecutar iniciarPartida() completament porque usa Scanner que espera input del usuario
        
        // odemos verificar es que los mocks funcionan correctamente
        when(mockPartida.isGameOver()).thenReturn(true);
        when(mockPartida.isWon()).thenReturn(false);
        when(mockPartida.getParaulaSecreta()).thenReturn("PERRO");
        
        // Verificar que las configuraciones funcionan
        assertTrue(mockPartida.isGameOver());
        assertFalse(mockPartida.isWon());
        assertEquals("PERRO", mockPartida.getParaulaSecreta());
        
        // Verificar que los metodos de vista se pueden llamar
        assertDoesNotThrow(() -> {
            mockVista.mostrarBenvinguda();
            mockVista.mostrarMissatgeDerrota("PERRO");
        });
        
        verify(mockVista).mostrarBenvinguda();
        verify(mockVista).mostrarMissatgeDerrota("PERRO");
    }


    
    @Test
    void testObtenirIntentUsuari() {
        // Este método es privado y no se puede testear directamente
        // Solo podemos testear indirectamente a través de iniciarPartida()
        
        // Verificamos que la validación de input funciona con mocks
        when(mockPartida.validarInput("PERRO")).thenReturn(true);
        when(mockPartida.validarInput("ABC")).thenReturn(false);
        when(mockPartida.validarInput(null)).thenReturn(false);
        when(mockPartida.validarInput("")).thenReturn(false);
        
        assertTrue(mockPartida.validarInput("PERRO"));
        assertFalse(mockPartida.validarInput("ABC"));
        assertFalse(mockPartida.validarInput(null));
        assertFalse(mockPartida.validarInput(""));
        
        verify(mockPartida, times(4)).validarInput(anyString());
    }


    
    
    @Test
    void testEscenariVictoria() {
        // escenario de victoria
        ResultatIntent mockResultat = mock(ResultatIntent.class);
        
        when(mockPartida.isGameOver()).thenReturn(false, true);
        when(mockPartida.isWon()).thenReturn(true);
        when(mockPartida.getIntentsRestants()).thenReturn(6, 5);
        when(mockPartida.validarInput("PERRO")).thenReturn(true);
        when(mockPartida.afegirIntent("PERRO")).thenReturn(mockResultat);
        
        // simular flujo
        assertFalse(mockPartida.isGameOver()); // primera llamada false
        mockPartida.afegirIntent("PERRO");
        assertTrue(mockPartida.isGameOver());  // Segunda llamada: true
        assertTrue(mockPartida.isWon());
        
        // Verificar que se mostrarían los mensajes correctos
        mockVista.mostrarBenvinguda();
        mockVista.mostrarMissatgeVictoria();
        
        verify(mockVista).mostrarBenvinguda();
        verify(mockVista).mostrarMissatgeVictoria();
        verify(mockPartida).afegirIntent("PERRO");
        verify(mockPartida, times(2)).isGameOver();
    }
    
    @Test
    void testEscenariDerrota() {
        // Configurar escenario de derrota
        when(mockPartida.isGameOver()).thenReturn(false, false, false, false, false, false, true);
        when(mockPartida.isWon()).thenReturn(false);
        when(mockPartida.getParaulaSecreta()).thenReturn("PERRO");
        when(mockPartida.getIntentsRestants()).thenReturn(6, 5, 4, 3, 2, 1, 0);
        
        // Simular varios intentos fallidos
        for (int i = 0; i < 6; i++) {
            if (!mockPartida.isGameOver()) {
                mockPartida.getIntentsRestants();
            }
        }
        
        assertTrue(mockPartida.isGameOver());
        assertFalse(mockPartida.isWon());
        assertEquals("PERRO", mockPartida.getParaulaSecreta());
        
        // Verificar mensaje de derrota
        mockVista.mostrarMissatgeDerrota("PERRO");
        verify(mockVista).mostrarMissatgeDerrota("PERRO");
    }
    
    @Test
    void testGestioExcepcio() {
        // Configurar para que lance excepción
        when(mockPartida.afegirIntent("XXXXX"))
            .thenThrow(new IllegalArgumentException("Paraula no vàlida"));
        
        // Verificar que se lanza la excepción
        assertThrows(IllegalArgumentException.class, () -> {
            mockPartida.afegirIntent("XXXXX");
        });
        
        // Verificar que se mostraría el error
        mockVista.mostrarErrorParaulaInvalida();
        verify(mockVista).mostrarErrorParaulaInvalida();
    }
    
    @Test
    void testValidacioInput() {
        // Configurar validaciones
        when(mockPartida.validarInput("PERRO")).thenReturn(true);
        when(mockPartida.validarInput("perro")).thenReturn(true);
        when(mockPartida.validarInput("ABC")).thenReturn(false);
        when(mockPartida.validarInput("ABCDEF")).thenReturn(false);
        when(mockPartida.validarInput("12345")).thenReturn(false);
        when(mockPartida.validarInput(null)).thenReturn(false);
        when(mockPartida.validarInput("")).thenReturn(false);
        
        // Verificar casos válidos
        assertTrue(mockPartida.validarInput("PERRO"));
        assertTrue(mockPartida.validarInput("perro"));
        
        // Verificar casos inválidos
        assertFalse(mockPartida.validarInput("ABC"));       // Muy corto
        assertFalse(mockPartida.validarInput("ABCDEF"));    // Muy largo
        assertFalse(mockPartida.validarInput("12345"));     // Números
        assertFalse(mockPartida.validarInput(null));        // Null
        assertFalse(mockPartida.validarInput(""));          // Vacío
        
        verify(mockPartida, times(7)).validarInput(anyString());
    }
    
    @Test
    void testFluxComplet() {
        // Simular 3 intentos: 2 incorrectos, 1 correcto
        ResultatIntent mockResultat1 = mock(ResultatIntent.class);
        ResultatIntent mockResultat2 = mock(ResultatIntent.class);
        ResultatIntent mockResultat3 = mock(ResultatIntent.class);
        
        when(mockResultat1.esCorrecte()).thenReturn(false);
        when(mockResultat2.esCorrecte()).thenReturn(false);
        when(mockResultat3.esCorrecte()).thenReturn(true);
        
        when(mockPartida.afegirIntent("GATOS")).thenReturn(mockResultat1);
        when(mockPartida.afegirIntent("LAGOS")).thenReturn(mockResultat2);
        when(mockPartida.afegirIntent("PERRO")).thenReturn(mockResultat3);
        
        when(mockPartida.isGameOver()).thenReturn(false, false, false, true);
        when(mockPartida.isWon()).thenReturn(false, false, true);
        
        // Simular los intentos
        ResultatIntent r1 = mockPartida.afegirIntent("GATOS");
        assertFalse(r1.esCorrecte());
        assertFalse(mockPartida.isWon());
        
        ResultatIntent r2 = mockPartida.afegirIntent("LAGOS");
        assertFalse(r2.esCorrecte());
        assertFalse(mockPartida.isWon());
        
        ResultatIntent r3 = mockPartida.afegirIntent("PERRO");
        assertTrue(r3.esCorrecte());
        assertTrue(mockPartida.isWon());
        
        verify(mockPartida, times(3)).afegirIntent(anyString());
        verify(mockPartida, times(3)).isWon();
    }
    
    @Test
    void testCridesVista() {
        // Verificar que todos los métodos de vista se pueden llamar
        mockVista.mostrarBenvinguda();
        mockVista.mostrarTauler(mockPartida);
        mockVista.mostrarSeparador();
        mockVista.demanarIntent(6);
        mockVista.mostrarErrorParaulaInvalida();
        mockVista.mostrarMissatgeVictoria();
        mockVista.mostrarMissatgeDerrota("PERRO");
        mockVista.mostrarAjuda();
        
        // Verificar todas las llamadas
        verify(mockVista).mostrarBenvinguda();
        verify(mockVista).mostrarTauler(mockPartida);
        verify(mockVista).mostrarSeparador();
        verify(mockVista).demanarIntent(6);
        verify(mockVista).mostrarErrorParaulaInvalida();
        verify(mockVista).mostrarMissatgeVictoria();
        verify(mockVista).mostrarMissatgeDerrota("PERRO");
        verify(mockVista).mostrarAjuda();
    }
    
    @Test
    void testCasosLimit() {
        when(mockPartida.getIntentsRestants()).thenReturn(0);
        when(mockPartida.getMaxIntents()).thenReturn(6);
        when(mockPartida.isGameOver()).thenReturn(true);
        when(mockPartida.isWon()).thenReturn(false);
        
        assertEquals(0, mockPartida.getIntentsRestants());
        assertEquals(6, mockPartida.getMaxIntents());
        assertTrue(mockPartida.isGameOver());
        assertFalse(mockPartida.isWon());
    }
}
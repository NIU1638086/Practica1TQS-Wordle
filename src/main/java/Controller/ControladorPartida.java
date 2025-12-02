package Controller;

import Model.Partida;
import Model.Diccionari;
import Model.ResultatIntent;
import View.VistaWordle;
import java.util.Scanner;

public class ControladorPartida {
    private Partida partida;
    private VistaWordle vista;
    private Scanner scanner;
    
    public ControladorPartida() {
        try {
            this.vista = new VistaWordle();
            this.scanner = new Scanner(System.in);
            this.partida = new Partida(new Diccionari());
        } catch (Exception e) {
            System.err.println("Error inicialitzant el joc: " + e.getMessage());
            System.exit(1);
        }
    }
    
    // Constructor per testing
    public ControladorPartida(Partida partida, VistaWordle vista) {
        this.partida = partida;
        this.vista = vista;
        this.scanner = new Scanner(System.in);
    }
    
    public void iniciarPartida() {
        vista.mostrarBenvinguda();
        
        while (!partida.isGameOver()) {
            vista.mostrarTauler(partida);
            vista.mostrarSeparador();
            
            String intent = obtenirIntentUsuari();
            
            if (intent == null) {
                System.out.println("👋 Sortint del joc...");
                return;
            }
            
            try {
                ResultatIntent resultat = partida.afegirIntent(intent);
                
                if (partida.isWon()) {
                    vista.mostrarTauler(partida);
                    vista.mostrarMissatgeVictoria();
                    break;
                }
            } catch (IllegalArgumentException e) {
                vista.mostrarErrorParaulaInvalida();
            }
        }
        
        if (!partida.isWon()) {
            vista.mostrarMissatgeDerrota(partida.getParaulaSecreta());
        }
        
        scanner.close();
    }
    
    private String obtenirIntentUsuari() {
        while (true) {
            vista.demanarIntent(partida.getIntentsRestants());
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("sortir")) {
                return null;
            }
            
            if (input.equalsIgnoreCase("help") || input.equalsIgnoreCase("ajuda")) {
                vista.mostrarAjuda();
                continue;
            }
            
            if (partida.validarInput(input)) {
                return input;
            } else {
                vista.mostrarErrorParaulaInvalida();
            }
        }
    }
    
    // Mètode principal per executar el joc
    public static void main(String[] args) {
        try {
            ControladorPartida controlador = new ControladorPartida();
            controlador.iniciarPartida();
        } catch (Exception e) {
            System.err.println("💥 Error crític: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
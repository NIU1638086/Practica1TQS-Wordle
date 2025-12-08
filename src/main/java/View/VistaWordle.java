package View;

import Model.Partida;
import Model.ResultatIntent;
import java.util.List;

public class VistaWordle {
    
    public VistaWordle() {}
    
    private String repetirCaracter(char caracter, int vegades) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vegades; i++) {
            sb.append(caracter);
        }
        return sb.toString();
    }
    
    public void mostrarBenvinguda() {
        System.out.println("=== BENVINGUT AL WORDLE! ===");
        System.out.println("Endevina la paraula de 5 lletres en 6 intents.");
        System.out.println("VERD = Correcte | GROC = Present | GRIS = Incorrecte");
        System.out.println();
    }
    
    public void mostrarTauler(Partida partida) {
        System.out.println();
        System.out.println(repetirCaracter('=', 30));
        System.out.println("           WORDLE");
        System.out.println(repetirCaracter('=', 30));
        
        List<ResultatIntent> intents = partida.getIntents();
        
        // Mostrar intents realitzats
        for (ResultatIntent intent : intents) {
            System.out.println("  " + intent.toString());
        }
        
        // Mostrar línies buides per als intents restants
        for (int i = 0; i < partida.getIntentsRestants(); i++) {
            System.out.println("  [ ] [ ] [ ] [ ] [ ]");
        }
        
        System.out.println(repetirCaracter('=', 30));
    }
    
    public void mostrarMissatgeVictoria() {
        System.out.println();
        System.out.println("*** FELICITATS! HAS ENCERTAT LA PARAULA! ***");
        System.out.println();
    }
    
    public void mostrarMissatgeDerrota(String paraulaSecreta) {
        System.out.println();
        System.out.println("*** GAME OVER! La paraula era: " + paraulaSecreta + " ***");
        System.out.println();
    }
    
    public void demanarIntent(int intentsRestants) {
        System.out.print(">>> Introdueix la teva paraula (" + intentsRestants + " intents restants): ");
    }
    
    public void mostrarErrorParaulaInvalida() {
        System.out.println("ERROR: Paraula invalida. Ha de tenir 5 lletres i existir al diccionari.");
    }
    
    public void mostrarSeparador() {
        System.out.println(repetirCaracter('-', 40));
    }
    
    public void mostrarAjuda() {
        System.out.println("\n--- AJUDA ---");
        System.out.println("* Introdueix paraules de 5 lletres");
        System.out.println("* Les paraules han d'existir al diccionari");
        System.out.println("* VERD = Lletra correcta en posicio correcta");
        System.out.println("* GROC = Lletra present pero en posicio incorrecta");
        System.out.println("* GRIS = Lletra no present");
        System.out.println("* Escriu 'exit' per sortir");
        System.out.println();
    }
}
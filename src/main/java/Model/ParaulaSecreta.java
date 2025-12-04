package Model;
import java.util.List;
import java.util.ArrayList;

public class ParaulaSecreta {
    private String paraula;

    public ParaulaSecreta(String paraula) {
        if (paraula == null || paraula.length() != 5) {
            throw new IllegalArgumentException("La paraula ha de tenir exactament 5 lletres");
        }
        this.paraula = paraula.toUpperCase();
    }
    
    public ParaulaSecreta(Diccionari dicc) {
        this(dicc.getRandomWord());
    }

    public String getParaula() {
        return paraula;
    }

    public List<EstatLletra> comparar(String intent) {
        if (intent == null || intent.length() != 5) {
            throw new IllegalArgumentException("L'intent ha de tenir exactament 5 lletres");
        }
        
        String intentUpper = intent.toUpperCase();
        List<EstatLletra> resultat = new ArrayList<>();
        char[] secretaArray = paraula.toCharArray();
        char[] intentArray = intentUpper.toCharArray();
        boolean[] usada = new boolean[5]; // Marcar letras ya usadas de la palabra secreta
        
        // PRIMERA PASADA: Marcar letras CORRECTAS (posición exacta)
        for (int i = 0; i < 5; i++) {
            if (intentArray[i] == secretaArray[i]) {
                resultat.add(EstatLletra.CORRECTA);
                usada[i] = true; // Marcar esta letra como usada
            } else {
                resultat.add(null); // Placeholder temporal
            }
        }
        
        // SEGUNDA PASADA: Marcar letras PRESENT (existe pero posición incorrecta)
        for (int i = 0; i < 5; i++) {
            if (resultat.get(i) == null) { // Solo procesar las que no son CORRECTA
                boolean trobada = false;
                
                // Buscar si esta letra existe en otra posición
                for (int j = 0; j < 5; j++) {
                    if (!usada[j] && intentArray[i] == secretaArray[j]) {
                        resultat.set(i, EstatLletra.PRESENT);
                        usada[j] = true; // Marcar como usada
                        trobada = true;
                        break;
                    }
                }
                
                // Si no se encontró, es INCORRECTA
                if (!trobada) {
                    resultat.set(i, EstatLletra.INCORRECTA);
                }
            }
        }
        
        return resultat;
    }
    
    public boolean esIgual(String altreParaula) {
        return this.paraula.equals(altreParaula.toUpperCase());
    }
}
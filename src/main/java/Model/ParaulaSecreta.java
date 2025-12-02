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
        boolean[] procesada = new boolean[5];
        
        // Primera passada: marcar les lletres correctes
        for (int i = 0; i < 5; i++) {
            if (intentUpper.charAt(i) == secretaArray[i]) {
                resultat.add(EstatLletra.CORRECTA);
                procesada[i] = true;
            } else {
                resultat.add(null); // Placeholder
            }
        }
        
        // Segona passada: marcar les lletres presents
        for (int i = 0; i < 5; i++) {
            if (resultat.get(i) == null) {
                boolean trobada = false;
                for (int j = 0; j < 5; j++) {
                    if (!procesada[j] && intentUpper.charAt(i) == secretaArray[j]) {
                        resultat.set(i, EstatLletra.PRESENT);
                        procesada[j] = true;
                        trobada = true;
                        break;
                    }
                }
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
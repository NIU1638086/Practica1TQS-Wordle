package Model;
import java.util.ArrayList;
import java.util.List;

public class Partida {
    private ParaulaSecreta paraula;
    private List<ResultatIntent> intents;
    private int intentsRestants;
    private static final int MAX_INTENTS = 6;
    private Diccionari diccionari;
    private boolean acabada;

    public Partida(ParaulaSecreta paraula) {
        if (paraula == null) {
            throw new IllegalArgumentException("La paraula secreta no pot ser null");
        }
        this.paraula = paraula;
        this.intents = new ArrayList<>();
        this.intentsRestants = MAX_INTENTS;
        this.diccionari = new Diccionari();
        this.acabada = false;
    }
    
    public Partida(Diccionari dicc) {
        this(new ParaulaSecreta(dicc));
    }

    public boolean validarInput(String intent) {
        if (intent == null || intent.length() != 5) {
            return false;
        }
        
        // Comprovar que només conté lletres
        if (!intent.matches("[A-Za-zÀ-Úà-úÑñ]{5}")) {
            return false;
        }
        
        // Comprovar que existeix al diccionari
        return diccionari.existeix(intent);
    }

    public ResultatIntent afegirIntent(String intent) {
        if (!validarInput(intent)) {
            throw new IllegalArgumentException("Intent no vàlid: " + intent);
        }
        if (acabada) {
            throw new IllegalStateException("La partida ja ha acabat");
        }
        if (intentsRestants <= 0) {
            throw new IllegalStateException("No queden intents");
        }
        
        String intentUpper = intent.toUpperCase();
        List<EstatLletra> estats = paraula.comparar(intentUpper);
        ResultatIntent resultat = new ResultatIntent(intentUpper, estats);
        intents.add(resultat);
        intentsRestants--;
        
        // Comprovar si s'ha guanyat
        if (paraula.esIgual(intentUpper)) {
            acabada = true;
        } else if (intentsRestants == 0) {
            acabada = true;
        }
        
        return resultat;
    }

    public boolean isWon() {
        if (intents.isEmpty()) return false;
        return paraula.esIgual(intents.get(intents.size() - 1).getIntent());
    }
    
    public boolean isGameOver() {
        return acabada;
    }

    public List<ResultatIntent> getIntents() {
        return new ArrayList<>(intents); // Retorna una còpia per evitar modificacions externes
    }
    
    public int getIntentsRestants() {
        return intentsRestants;
    }
    
    public String getParaulaSecreta() {
        return paraula.getParaula();
    }
    
    public int getMaxIntents() {
        return MAX_INTENTS;
    }
}
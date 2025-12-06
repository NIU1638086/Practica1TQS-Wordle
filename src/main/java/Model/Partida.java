package Model;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;


public class Partida {
	private ParaulaSecreta paraula;
	private List<ResultatIntent> intents;
	private Diccionari diccionari;
	private int intentsRestants;
	private static final int MAX_INTENTS = 6;
	
    public Partida(ParaulaSecreta paraula, Diccionari dicc) {
	    	if (paraula == null || dicc == null)
	            throw new IllegalArgumentException("Arguments no poden ser null");
	
	        this.paraula = paraula;
	        this.diccionari = dicc;
	        this.intents = new ArrayList<>();
	        this.intentsRestants = MAX_INTENTS;
    }
    
    public Partida(Diccionari dicc) {
	    	if (dicc == null)
	            throw new IllegalArgumentException("Diccionari no pot ser null");
	
	        this.diccionari = dicc;
	        this.paraula = new ParaulaSecreta(dicc);
	        this.intents = new ArrayList<>();
	        this.intentsRestants = MAX_INTENTS;
    }

    public boolean validarInput(String intent) {
    		return intent != null && intent.length() == 5 && diccionari.existeix(intent);
    }

    public ResultatIntent afegirIntent(String intent) {
	    	if (!validarInput(intent))
	            throw new IllegalArgumentException("Intent no vàlid");
	    	
	    	List<EstatLletra> estats = paraula.comparar(intent);
	    ResultatIntent res = new ResultatIntent(intent, estats);
	    intents.add(res);
	    if (intentsRestants > 0) 
	    		intentsRestants--;
	    
	    return res;
    }

    public boolean isWon() {
	    	if (intents.isEmpty()) 
	    		return false;
	    	
	    return intents.get(intents.size() - 1).esCorrecte();
    }
    
    public boolean isGameOver() {
    		return isWon() || intentsRestants <= 0;	
    }

    public List<ResultatIntent> getIntents() {
    		return Collections.unmodifiableList(intents);
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
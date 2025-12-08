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

	private boolean invariant() {
        if (paraula == null) 
        		return false;
        
        if (diccionari == null) 
        		return false;
        
        if (intents == null) 
        		return false;
        
        if (intentsRestants < 0 || intentsRestants > MAX_INTENTS) 
        		return false;
        
        if (intents.size() + intentsRestants != MAX_INTENTS) 
        		return false;

        return true;
    }
	
    public Partida(ParaulaSecreta paraula, Diccionari dicc) {
    		//PRECONDITION
	    	if (paraula == null || dicc == null)
	            throw new IllegalArgumentException("Arguments no poden ser null");
	
	    this.paraula = paraula;
	    this.diccionari = dicc;
	    this.intents = new ArrayList<>();
	    this.intentsRestants = MAX_INTENTS;
	    
	    assert invariant();
    }
    
    public Partida(Diccionari dicc) {
    		//PRECONDITION
	    	if (dicc == null)
	            throw new IllegalArgumentException("Diccionari no pot ser null");
	
	    this.diccionari = dicc;
	    this.paraula = new ParaulaSecreta(dicc);
	    this.intents = new ArrayList<>();
	    this.intentsRestants = MAX_INTENTS;
	    
	    assert invariant();
    }

    public boolean validarInput(String intent) {
    		assert invariant();
    		return intent != null && intent.length() == 5 && diccionari.existeix(intent);
    }

    public ResultatIntent afegirIntent(String intent) {
    		assert invariant();
    		
    		//PRECONDITION
    		assert !isGameOver();
    		assert validarInput(intent);
    		
    		
	    	if (!validarInput(intent))
	            throw new IllegalArgumentException("Intent no vàlid");
	    	
	    	int intentsAbans = intents.size();
	    int restantsAbans = intentsRestants;
	    	
	    List<EstatLletra> estats = paraula.comparar(intent);
        ResultatIntent res = new ResultatIntent(intent, estats);
        intents.add(res);
        
        if (intentsRestants > 0) 
            intentsRestants--;
	    
	    //POSTCONDITION
        assert intents.size() == intentsAbans + 1;
        assert intentsRestants == restantsAbans - 1;
        assert res != null;
        
        assert invariant();
        
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
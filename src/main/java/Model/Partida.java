package Model;
import java.util.ArrayList;
import java.util.List;

public class Partida {

    public Partida(ParaulaSecreta paraula, Diccionari dicc) {
    }
    
    public Partida(Diccionari dicc) {
    }

    public boolean validarInput(String intent) {
    		return false;
    }

    public ResultatIntent afegirIntent(String intent) {
	    	ResultatIntent r = new ResultatIntent(intent, null);
	    	return r;
    }

    public boolean isWon() {
    		return false;
    }
    
    public boolean isGameOver() {
    		return false;
    }

    public List<ResultatIntent> getIntents() {
    		List<ResultatIntent> l = new ArrayList<>();
    		return l;
    }
    
    public int getIntentsRestants() {
    		return -1;
    }
    
    public String getParaulaSecreta() {
    		return "h";
    }
    
    public int getMaxIntents() {
    		return -1;
    }
}
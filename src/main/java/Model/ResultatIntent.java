package Model;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Collections;

public class ResultatIntent {
    private String paraulaIntent;
    private List<EstatLletra> estatLletres;

    public ResultatIntent(String intent, List<EstatLletra> estat) {
    	
    		if (intent == null)
            throw new IllegalArgumentException("La paraula no pot ser null.");
        
        if (estat == null) 
            throw new IllegalArgumentException("La llista d'estats no pot ser null.");
        
        if (estat.size() != 5) 
            throw new IllegalArgumentException("La llista d'estats ha de tenir 5 elements.");
        
        this.paraulaIntent = intent;
        this.estatLletres = new ArrayList<>(estat);
    	
    }

    public String getIntent() {
        return paraulaIntent;
    }
    
    public List<EstatLletra> getEstats() {
        return Collections.unmodifiableList(estatLletres);
    }
    
    public boolean esCorrecte() {
        for (EstatLletra estat : estatLletres) {
        		if (estat != EstatLletra.CORRECTA)
        			return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return " ResultatIntent [intent = " + paraulaIntent + ", estats = " + estatLletres + "]";
    }
    
    @Override
    public boolean equals(Object obj) {
    		if (this == obj)
    			return true;
    		if (obj == null || getClass() != obj.getClass())
    			return false;
    		
    		ResultatIntent other = (ResultatIntent) obj;
    		return Objects.equals(paraulaIntent, other.paraulaIntent) && Objects.equals(estatLletres, other.estatLletres);
    }
    
    @Override
    public int hashCode() {
		return Objects.hash(paraulaIntent, estatLletres);
    	
    }
}
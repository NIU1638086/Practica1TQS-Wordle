package Model;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class ResultatIntent {
    private String paraulaIntent;
    private List<EstatLletra> estatLletres;

    public ResultatIntent(String intent, List<EstatLletra> estat) {
    }

    public String getIntent() {
        return paraulaIntent;
    }
    
    public List<EstatLletra> getEstats() {
        return estatLletres;
    }
    
    public boolean esCorrecte() {
        return true;
    }

    @Override
    public String toString() {
        return "a";
    }
    
    @Override
    public boolean equals(Object obj) {
    		return false;
    }
    
    @Override
    public int hashCode() {
		return 0;
    	
    }
}
package Model;
import java.util.List;


public class ResultatIntent {
	private String paraulaIntent;
    private List<EstatLletra> estatLletres;

    public ResultatIntent(String intent, List<EstatLletra> estat)
    {
    	
    }

    public String getIntent()
    {
    		return paraulaIntent;
    }
    public List<EstatLletra> getEstats()
    {
    		return estatLletres;
    }

    @Override
    public String toString()
    {
    		return paraulaIntent;
    }
}

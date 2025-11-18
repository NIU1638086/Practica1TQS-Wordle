package Model;
import java.util.ArrayList;
import java.util.List;


public class Partida {
	private ParaulaSecreta paraula;
    private List<ResultatIntent> intents;
    private int intentsRestants;

    public Partida(ParaulaSecreta paraula)
    {
    	
    }

    public boolean validarInput(String intent)
    {
    		return false;
    }

    public ResultatIntent afegirIntent(String intent)
    {
		return null;
    	
    }

    public boolean isWon()
    {
    		return false;
    }
    public boolean isGameOver()
    {
    		return false;
    }

    public List<ResultatIntent> getIntents()
    {
    	List<ResultatIntent> hola = new ArrayList<>();
		return hola;
    }
}

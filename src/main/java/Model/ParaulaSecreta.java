package Model;
import java.util.List;
import java.util.ArrayList;

public class ParaulaSecreta {
    private String paraula;

    public ParaulaSecreta(String paraula) {
    }
    
    public ParaulaSecreta(Diccionari dicc) {
    }

    public String getParaula() {
        return paraula;
    }

    public List<EstatLletra> comparar(String intent) {
    		List<EstatLletra> l = new ArrayList<>();
    		return l;
    }
    
    public boolean esIgual(String altreParaula) {
        return false;
    }
}
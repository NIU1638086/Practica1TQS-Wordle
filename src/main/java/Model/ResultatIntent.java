package Model;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class ResultatIntent {
    private String paraulaIntent;
    private List<EstatLletra> estatLletres;

    public ResultatIntent(String intent, List<EstatLletra> estat) {
        if (intent == null || intent.length() != 5) {
            throw new IllegalArgumentException("L'intent ha de tenir 5 lletres");
        }
        if (estat == null || estat.size() != 5) {
            throw new IllegalArgumentException("Els estats han de ser per 5 lletres");
        }
        
        this.paraulaIntent = intent;
        this.estatLletres = Collections.unmodifiableList(new ArrayList<>(estat));
    }

    public String getIntent() {
        return paraulaIntent;
    }
    
    public List<EstatLletra> getEstats() {
        return estatLletres;
    }
    
    public boolean esCorrecte() {
        for (EstatLletra estat : estatLletres) {
            if (estat != EstatLletra.CORRECTA) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        // Primero mostrar la palabra
        sb.append(paraulaIntent).append("  ");
        
        // Luego mostrar los estados con caracteres ASCII
        for (EstatLletra estat : estatLletres) {
            switch (estat) {
                case CORRECTA:
                    sb.append("[V]");  // Verde = Correcto
                    break;
                case PRESENT:
                    sb.append("[G]");  // Gualda/Amarillo = Presente
                    break;
                case INCORRECTA:
                    sb.append("[X]");  // Gris/Equis = Incorrecto
                    break;
            }
            sb.append(" ");
        }
        
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ResultatIntent other = (ResultatIntent) obj;
        return paraulaIntent.equals(other.paraulaIntent) && 
               estatLletres.equals(other.estatLletres);
    }
}
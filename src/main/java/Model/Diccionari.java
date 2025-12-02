package Model;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class Diccionari {
    private List<String> paraules;
    
    public Diccionari(String nomFitxer) {
    }
    
    private List<String> carregarParaulesDeRecursos(String nomFitxer) {
    		return paraules;
    }

    public boolean existeix(String paraula) {
    		return false;
    }

    public String getRandomWord() {
    		return "a";
    }
    
    // Mètodes per testing
    public List<String> getParaules() {
        return new ArrayList<>(paraules);
    }
    
    public int getNombreParaules() {
        return paraules.size();
    }
}
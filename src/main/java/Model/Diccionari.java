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
    		this.paraules = carregarParaulesDeRecursos(nomFitxer);
        
        if (this.paraules.isEmpty()) {
            throw new IllegalStateException("No s'han pogut carregar paraules del diccionari");
        }
        
        System.out.println("S'han carregat " + this.paraules.size() + " paraules del diccionari.");
    }
    
    private List<String> carregarParaulesDeRecursos(String nomFitxer) {

        List<String> paraulesCarregades = new ArrayList<>();
        	
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(nomFitxer);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
            
            if (inputStream == null) {
                System.err.println("No s'ha trobat el fitxer: " + nomFitxer);
                return paraulesCarregades;
            }
            
            String linia;
            while ((linia = reader.readLine()) != null) {
                linia = linia.trim().toUpperCase();
                if (linia.length() == 5 && linia.matches("[A-ZÀ-ÚÑ]+")) {
                    paraulesCarregades.add(linia);
                }
            }
            
        } catch (IOException e) {
            System.err.println("Error llegint el fitxer de paraules: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperat: " + e.getMessage());
        }
        
        return paraulesCarregades;
    }

    public boolean existeix(String paraula) {
        if (paraula == null || paraula.length() != 5) {
            return false;
        }
        return paraules.contains(paraula.toUpperCase());
    }

    public String getRandomWord() {
        if (paraules.isEmpty()) {
            throw new IllegalStateException("El diccionari està buit");
        }
        Random rand = new Random();
        return paraules.get(rand.nextInt(paraules.size()));
    }
    
    public List<String> getParaules() {
    		return new ArrayList<>(paraules);
    }
    
    public int getNombreParaules() {
        return paraules.size();
    }
}
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
    
    private boolean invariant() {
        if (paraules == null) 
        		return false;
        
        if (paraules.isEmpty()) 
        		return false;
        
        return true;
    }
    
    public Diccionari(String nomFitxer) {
    		//PRECONDITION
        assert nomFitxer != null;
        assert !nomFitxer.trim().isEmpty();
        
    		this.paraules = carregarParaulesDeRecursos(nomFitxer);
        
        if (this.paraules.isEmpty()) {
            throw new IllegalStateException("No s'han pogut carregar paraules del diccionari");
        }
        
        System.out.println("S'han carregat " + this.paraules.size() + " paraules del diccionari.");
        
        //POSTCONDITION
        assert this.paraules != null;
        assert !this.paraules.isEmpty();
        
        assert invariant();
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
        
        assert paraulesCarregades != null;
        
        return paraulesCarregades;
    }

    public boolean existeix(String paraula) {
    		assert invariant();
    		
    		//PRECONDITION
        // la funcio ja gestiona nulls tornant false
    	
        if (paraula == null || paraula.length() != 5)
            return false;
        
        //POSTCONDITION
        // No hi ha canvi d'estat
        
        assert invariant();
        
        return paraules.contains(paraula.toUpperCase());
    }

    public String getRandomWord() {
    		assert invariant();	
    		
    		//PRECONDITION
        assert !paraules.isEmpty();
    		
        if (paraules.isEmpty())
            throw new IllegalStateException("El diccionari està buit");
        
        Random rand = new Random();
        
        String resultat = paraules.get(rand.nextInt(paraules.size()));
        
        //POSTCONDITION
        assert resultat != null;
        assert resultat.length() == 5;
        assert paraules.contains(resultat);
        assert rand != null;
        
        assert invariant();
        
        return resultat;
    }
    
    public List<String> getParaules() {
    		return new ArrayList<>(paraules);
    }
    
    public int getNombreParaules() {
        return paraules.size();
    }
}
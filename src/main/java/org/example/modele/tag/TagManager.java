package org.example.modele.tag;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagManager {

    private static final String FICHIER_JSON = "bibliotheque.json";
    private Map<String, DonneesImage> donnees;
    private ObjectMapper mapper;

    public TagManager() {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        donnees = new HashMap<>();
        chargerDepuisJson();
    }

    public void ajouterTag(String cheminImage, String tag) {
        if (!donnees.containsKey(cheminImage)) {
            donnees.put(cheminImage, new DonneesImage());
        }
        if (!donnees.get(cheminImage).getTags().contains(tag)) {
            donnees.get(cheminImage).getTags().add(tag);
        }
        sauvegarderJson();
    }

    public void supprimerTag(String cheminImage, String tag) {
        if (donnees.containsKey(cheminImage)) {
            donnees.get(cheminImage).getTags().remove(tag);
            sauvegarderJson();
        }
    }

    public List<String> getTags(String cheminImage) {
        if (donnees.containsKey(cheminImage)) {
            return donnees.get(cheminImage).getTags();
        }
        return new ArrayList<>();
    }

    public List<String> rechercherParTag(String tag) {
        List<String> resultats = new ArrayList<>();
        for (Map.Entry<String, DonneesImage> entry : donnees.entrySet()) {
            if (entry.getValue().getTags().contains(tag)) {
                resultats.add(entry.getKey());
            }
        }
        return resultats;
    }

    public void ajouterFiltreApplique(String cheminImage, String nomFiltre) {
        if (!donnees.containsKey(cheminImage)) {
            donnees.put(cheminImage, new DonneesImage());
        }
        donnees.get(cheminImage).getFiltresAppliques().add(nomFiltre);
        sauvegarderJson();
    }

    private void sauvegarderJson() {
        try {
            mapper.writeValue(new File(FICHIER_JSON), donnees);
        } catch (IOException e) {
            System.out.println("Erreur sauvegarde JSON : " + e.getMessage());
        }
    }

    private void chargerDepuisJson() {
        File fichier = new File(FICHIER_JSON);
        if (fichier.exists()) {
            try {
                donnees = mapper.readValue(fichier,
                        mapper.getTypeFactory().constructMapType(HashMap.class, String.class, DonneesImage.class));
            } catch (IOException e) {
                System.out.println("Erreur chargement JSON : " + e.getMessage());
                donnees = new HashMap<>();
            }
        }
    }
}
package org.example.modele.entite.service;

import org.example.modele.tag.TagManager;
import java.util.List;

public class TagService {

    private final TagManager tagManager = new TagManager();

    public void ajouterTag(String chemin, String tag) {
        tagManager.ajouterTag(chemin, tag);
    }

    public void supprimerTag(String chemin, String tag) {
        tagManager.supprimerTag(chemin, tag);
    }

    public List<String> getTags(String chemin) {
        return tagManager.getTags(chemin);
    }

    public List<String> rechercherParTag(String tag) {
        return tagManager.rechercherParTag(tag);
    }

    public void enregistrerFiltre(String chemin, String nomFiltre) {
        tagManager.ajouterFiltreApplique(chemin, nomFiltre);
    }
}
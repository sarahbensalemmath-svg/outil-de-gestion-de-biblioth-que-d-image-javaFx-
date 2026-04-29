package org.example.modele.tag;

import java.util.ArrayList;
import java.util.List;

public class DonneesImage {

    private List<String> tags;
    private List<String> filtresAppliques;

    // constructeur vide obligatoire pour Jackson
    public DonneesImage() {
        tags = new ArrayList<>();
        filtresAppliques = new ArrayList<>();
    }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public List<String> getFiltresAppliques() { return filtresAppliques; }
    public void setFiltresAppliques(List<String> f) { this.filtresAppliques = f; }
}
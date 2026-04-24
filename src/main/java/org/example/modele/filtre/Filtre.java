package org.example.modele.filtre;

import javafx.scene.image.Image;


public interface Filtre{
    //point commun entre les filtre: ils prennent une image et rendent un e image transformée
    Image apply(Image image);
    //retourne le nom du filtre
    String getName();
}

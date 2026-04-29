package org.example.modele.filtre;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

//implemente l'interface filtre
public abstract class AbstractFiltre implements Filtre {

    // Méthode utilitaire commune à tous les filtres
    // Elle crée une nouvelle image vide de la même taille que l'originale
    protected WritableImage createOutputImage(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        return new WritableImage(width, height);
    }

    // Méthode utilitaire pour lire les pixels d'une image
    protected PixelReader getReader(Image image) {
        return image.getPixelReader();
    }

    // Méthode utilitaire pour écrire les pixels d'une image
    protected PixelWriter getWriter(WritableImage image) {
        return image.getPixelWriter();
    }
}
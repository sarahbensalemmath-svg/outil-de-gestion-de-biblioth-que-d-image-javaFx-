package org.example.modele.filtre;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

// implemente l'interface filtre
public abstract class AbstractFiltre implements Filtre {

    // Variables communes accessibles dans tous les filtres
    protected int width;
    protected int height;

    protected WritableImage result;
    protected PixelReader reader;
    protected PixelWriter writer;

    // Méthode utilitaire commune à tous les filtres
    // elle crée une nouvelle image vide de la même taille que l'originale
    protected WritableImage createOutputImage(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        return new WritableImage(width, height);
    }

    // Méthode pour lire les pixels d'une image
    protected PixelReader getReader(Image image) {
        return image.getPixelReader();
    }

    // méthode pour écrire les pixels d'une image
    protected PixelWriter getWriter(WritableImage image) {
        return image.getPixelWriter();
    }

    // Méthode d'initialisation commune à tous les filtres
    // elle prépare les dimensions et les outils de lecture/écriture
    protected void init(Image image) {

        // récupération de la largeur de l'image
        width = (int) image.getWidth();

        // récupération de la hauteur de l'image
        height = (int) image.getHeight();

        // création de l'image résultat
        result = createOutputImage(image);

        // lecteur de pixels de l'image originale
        reader = getReader(image);

        // ça ecrit les pixels pour l'image résultat
        writer = getWriter(result);
    }
}
package org.example.modele.entite;

import javafx.scene.image.Image;
import java.io.File;

public class ImageModele {
    private Image imageOriginale;
    private Image imageCourante;
    private File fichier;

    public void chargerImage(File fichier) {
        this.fichier = fichier;
        this.imageOriginale = new Image(fichier.toURI().toString());
        this.imageCourante = this.imageOriginale;
    }

    public Image getImageOriginale() {
        return imageOriginale;
    }

    public Image getImageCourante() {
        return imageCourante;
    }

    public void setImageCourante(Image image) {
        this.imageCourante = image;
    }

    public void reinitialiser() {
        this.imageCourante = this.imageOriginale;
    }

    public boolean aUneImage() {
        return imageOriginale != null;
    }

    public File getFichier() {
        return fichier;
    }

    public void setFichier(File fichier) {
        this.fichier = fichier;
    }
}

package org.example.modele.entite.service;

import javafx.scene.image.Image;
import org.example.modele.entite.ImageModele;
import org.example.modele.filtre.Filtre;
import org.example.modele.securite.CrypterImage;
import org.example.modele.transformation.Transformation;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageService {

    private final ImageModele modele = new ImageModele();

    public void charger(File fichier) {
        modele.chargerImage(fichier);
    }

    public Image appliquerFiltre(Filtre filtre) {
        Image result = filtre.apply(modele.getImageOriginale());
        modele.setImageCourante(result);
        return result;
    }

    public Image appliquerTransformation(Transformation t) {
        Image result = t.apply(modele.getImageCourante());
        modele.setImageCourante(result);
        return result;
    }

    public Image chiffrer(String motDePasse) throws Exception {
        CrypterImage crypter = new CrypterImage();
        Image result = crypter.chiffrer(modele.getImageCourante(), motDePasse);
        modele.setImageCourante(result);
        return result;
    }

    public Image dechiffrer(String motDePasse) throws Exception {
        CrypterImage crypter = new CrypterImage();
        Image result = crypter.dechiffrer(modele.getImageCourante(), motDePasse);
        modele.setImageCourante(result);
        return result;
    }

    public void reinitialiser() {
        modele.reinitialiser();
    }

    public void enregistrer(File fichier) throws Exception {
        BufferedImage bImage = SwingFXUtils.fromFXImage(modele.getImageCourante(), null);
        ImageIO.write(bImage, "png", fichier);
    }

    public boolean aUneImage() { return modele.aUneImage(); }
    public Image getImageCourante() { return modele.getImageCourante(); }
    public File getFichier() { return modele.getFichier(); }
}
package org.example.modele.securite;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class CrypterImage {
    // transforme le mot de passe en tableau d'octets via SHA-256
    // SHA-256 produit une empreinte unique et sécurisée du mot de passe

    private byte[] motDePasseEnBytes(String motDePasse) throws NoSuchAlgorithmException {

        // création d'un objet de hachage SHA-256
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(motDePasse.getBytes());//conversion en tableau de bytes puis génération du hash
    }

    // on génère un ordre aléatoire des pixels basé sur le mot de passe(identique )
    private int[] genererOrdre(int taille, String motDePasse) throws NoSuchAlgorithmException {
        //générateur pseudo-aléatoire sécurisé initialisé avec le mot de passe
        SecureRandom random = new SecureRandom(motDePasseEnBytes(motDePasse));
        int[] ordre = new int[taille];//tableau qui contient l'ordre
        for (int i = 0; i < taille; i++) {//initialisation
            ordre[i] = i;
        }
        // Mélange de Fisher-Yates
        for (int i = taille - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = ordre[i];
            ordre[i] = ordre[j];
            ordre[j] = temp;
        }
        return ordre;
    }

    // Chiffre l'image en mélangeant les pixels
    public Image chiffrer(Image image, String motDePasse) throws NoSuchAlgorithmException {
        //on récupère les dimensions
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        int taille = width * height;//compte le  nombre totale de pixels

        PixelReader reader = image.getPixelReader();//lecture de l'image originale
        WritableImage result = new WritableImage(width, height);//création d el'image chiffrée
        PixelWriter writer = result.getPixelWriter();//écriture des pixels de la nouvelle image


        //génère le meme ordre que le chiffremennt
        int[] ordre = genererOrdre(taille, motDePasse);

        //parcours de tous les pixels pour les déplacer selon l'ordre détérminé
        for (int i = 0; i < taille; i++) {
            int xSource = i % width;
            int ySource = i / width;
            int xDest = ordre[i] % width;
            int yDest = ordre[i] / width;
            Color color = reader.getColor(xSource, ySource);//récupère la couleur du  pixel à la position d'origine
            writer.setColor(xDest, yDest, color);//déplace le pixel à son nouvel emplacement
        }
        return result;
    }



}

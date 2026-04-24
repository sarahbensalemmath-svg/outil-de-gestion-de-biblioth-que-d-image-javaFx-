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
    // Transforme le mot de passe en tableau d'octets via SHA-256
    private byte[] motDePasseEnBytes(String motDePasse) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(motDePasse.getBytes());
    }

    // Génère un ordre aléatoire des pixels basé sur le mot de passe
    private int[] genererOrdre(int taille, String motDePasse) throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom(motDePasseEnBytes(motDePasse));
        int[] ordre = new int[taille];
        for (int i = 0; i < taille; i++) {
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
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        int taille = width * height;

        PixelReader reader = image.getPixelReader();
        WritableImage result = new WritableImage(width, height);
        PixelWriter writer = result.getPixelWriter();

        int[] ordre = genererOrdre(taille, motDePasse);

        for (int i = 0; i < taille; i++) {
            int xSource = i % width;
            int ySource = i / width;
            int xDest = ordre[i] % width;
            int yDest = ordre[i] / width;
            Color color = reader.getColor(xSource, ySource);
            writer.setColor(xDest, yDest, color);
        }
        return result;
    }

    // Déchiffre l'image en remettant les pixels dans l'ordre original
    public Image dechiffrer(Image image, String motDePasse) throws NoSuchAlgorithmException {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        int taille = width * height;

        PixelReader reader = image.getPixelReader();
        WritableImage result = new WritableImage(width, height);
        PixelWriter writer = result.getPixelWriter();

        int[] ordre = genererOrdre(taille, motDePasse);

        for (int i = 0; i < taille; i++) {
            int xSource = ordre[i] % width;
            int ySource = ordre[i] / width;
            int xDest = i % width;
            int yDest = i / width;
            Color color = reader.getColor(xSource, ySource);
            writer.setColor(xDest, yDest, color);
        }
        return result;
    }

}

package org.example.modele.filtre;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

//la classe hérite de AbstractFiltre
public class FiltrePrewitt extends AbstractFiltre {

    @Override
    //méthode principale qui applique le filtre sur l'image
    public Image apply(Image image) {

        // initialise width, height, result, reader et writer
        init(image);

        // Masques de Prewitt pour détecter les contours (variation de la luminosité sur  l'axe X puis Y)
        int[][] kernelX = {
                {-1, 0, 1},
                {-1, 0, 1},
                {-1, 0, 1}
        };
        int[][] kernelY = {
                {-1, -1, -1},
                { 0,  0,  0},
                { 1,  1,  1}
        };

        // parcours de l'image
        // On commence à 1 et on finit à width-1 / height-1(coin en bas à droite)
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                double gx = 0, gy = 0;//gradient horizentale et verticale

                //parcours du voisinage au tour du pixel courant
                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {
                        Color c = reader.getColor(x + kx, y + ky);//lecture de la couleur actuelle
                        // On convertit en niveau de gris
                        double gray = (c.getRed() + c.getGreen() + c.getBlue()) / 3.0;
                        gx += gray * kernelX[ky + 1][kx + 1];//application du masque horizental
                        gy += gray * kernelY[ky + 1][kx + 1];//application du masque vertical
                    }
                }
                // Calcul de l'intensité du contour
                // formule : sqrt(gx² + gy²)
                double magnitude = Math.min(Math.sqrt(gx * gx + gy * gy), 1.0);
                writer.setColor(x, y, new Color(magnitude, magnitude, magnitude, 1.0));//ecrire le pixel résultat en niveau de gris
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return "Prewitt (contours)";
    }
}
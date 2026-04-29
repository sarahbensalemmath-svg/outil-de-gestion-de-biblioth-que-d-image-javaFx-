package org.example.modele.transformation;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;


 // Transformation : rotation 90° horaire de l'image
//la classe hérite de AbstractTransformation
public class Rotation extends AbstractTransformation {


     // La rotation va  échanger largeur et hauteur
    @Override
    protected int getNewWidth(int width, int height) {
        return height;
    }

    @Override
    protected int getNewHeight(int width, int height) {
        return width;
    }

    @Override
    protected void process(PixelReader reader, PixelWriter writer,
                           int width, int height) {

        // Parcours de chaque pixel de l'image initiale
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                // Couleur du pixel actuel
                Color color = reader.getColor(x, y);

                // (x, y) devient (height - 1 - y, x)
                writer.setColor(height - 1 - y, x, color);
            }
        }
    }

    @Override
    public String getName() {
        return "Rotation 90°";
    }
}
package org.example.modele.transformation;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Rotation implements Transformation {

    @Override
    public Image apply(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        // Après rotation 90° : largeur et hauteur sont inversées
        WritableImage result = new WritableImage(height, width);
        PixelReader reader = image.getPixelReader();
        PixelWriter writer = result.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = reader.getColor(x, y);
                // Rotation 90° dans le sens horaire
                writer.setColor(height - 1 - y, x, color);
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return "Rotation 90°";
    }
}
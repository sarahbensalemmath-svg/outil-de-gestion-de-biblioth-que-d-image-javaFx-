package org.example.transformation;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Symetrie implements Transformation {

    private final boolean horizontal;

    public Symetrie(boolean horizontal) {
        this.horizontal = horizontal;
    }

    @Override
    public Image apply(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        WritableImage result = new WritableImage(width, height);
        PixelReader reader = image.getPixelReader();
        PixelWriter writer = result.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = reader.getColor(x, y);
                if (horizontal) {
                    // Symétrie horizontale : on inverse les colonnes
                    writer.setColor(width - 1 - x, y, color);
                } else {
                    // Symétrie verticale : on inverse les lignes
                    writer.setColor(x, height - 1 - y, color);
                }
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return horizontal ? "Symétrie horizontale" : "Symétrie verticale";
    }
}
package org.example.modele.transformation;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

//Transformation : symétrie horizontale ou verticale
public class Symetrie extends AbstractTransformation {

    // true = symétrie gauche/droite
    // false = symétrie haut/bas
    private final boolean horizontal;

    public Symetrie(boolean horizontal) {
        this.horizontal = horizontal;
    }

    @Override
    protected void process(PixelReader reader, PixelWriter writer,
                           int width, int height) {

        // Parcours de tous les pixels de l'image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                // Récupération de la couleur du pixel source
                Color color = reader.getColor(x, y);

                if (horizontal) {
                    // symetrie horizontale
                    writer.setColor(width - 1 - x, y, color);
                } else {
                    // symétrie verticale
                    writer.setColor(x, height - 1 - y, color);
                }
            }
        }
    }

    @Override
    public String getName() {
        return horizontal ? "Symétrie horizontale" : "Symétrie verticale";
    }
}
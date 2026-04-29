package org.example.modele.filtre;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class FiltreNoirEtBlanc extends AbstractFiltre {

    @Override
    public Image apply(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        WritableImage result = createOutputImage(image);
        PixelReader reader = getReader(image);
        PixelWriter writer = getWriter(result);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = reader.getColor(x, y);
                // Moyenne des 3 composantes
                double gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
                writer.setColor(x, y, new Color(gray, gray, gray, color.getOpacity()));
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return "Noir & Blanc";
    }
}
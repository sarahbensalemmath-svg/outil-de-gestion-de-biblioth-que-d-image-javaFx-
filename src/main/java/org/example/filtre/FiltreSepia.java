package org.example.filtre;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class FiltreSepia extends AbstractFiltre {

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

                double r = color.getRed();
                double g = color.getGreen();
                double b = color.getBlue();

                // Formule sépia standard
                double newR = Math.min(r * 0.393 + g * 0.769 + b * 0.189, 1.0);
                double newG = Math.min(r * 0.349 + g * 0.686 + b * 0.168, 1.0);
                double newB = Math.min(r * 0.272 + g * 0.534 + b * 0.131, 1.0);

                writer.setColor(x, y, new Color(newR, newG, newB, color.getOpacity()));
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return "Sépia";
    }
}
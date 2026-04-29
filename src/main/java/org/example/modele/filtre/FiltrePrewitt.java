package org.example.modele.filtre;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class FiltrePrewitt extends AbstractFiltre {

    @Override
    public Image apply(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        WritableImage result = createOutputImage(image);
        PixelReader reader = getReader(image);
        PixelWriter writer = getWriter(result);

        // Masques de Prewitt pour détecter les contours
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

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                double gx = 0, gy = 0;

                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {
                        Color c = reader.getColor(x + kx, y + ky);
                        // On convertit en niveau de gris
                        double gray = (c.getRed() + c.getGreen() + c.getBlue()) / 3.0;
                        gx += gray * kernelX[ky + 1][kx + 1];
                        gy += gray * kernelY[ky + 1][kx + 1];
                    }
                }

                double magnitude = Math.min(Math.sqrt(gx * gx + gy * gy), 1.0);
                writer.setColor(x, y, new Color(magnitude, magnitude, magnitude, 1.0));
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return "Prewitt (contours)";
    }
}
package org.example.modele.filtre;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

//la classe hérite de AbstractFiltre
public class FiltreRGBSwap extends AbstractFiltre {

    @Override
    public Image apply(Image image) {//méthode qui applique le filtre

        // initialise width, height, result, reader et writer
        init(image);

        //on parcours pour récuperer les composantes RGB puis on transforme en GBR
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = reader.getColor(x, y);

                // On échange (R,G,B) → (G,B,R)
                double newR = color.getGreen();
                double newG = color.getBlue();
                double newB = color.getRed();

                //on écrit la nouvelle couleur tout en conservant l'opacité originale
                writer.setColor(x, y, new Color(newR, newG, newB, color.getOpacity()));
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return "Échange RGB";
    }    // Retourne le nom du filtre

}
package org.example.modele.transformation;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;


public abstract class AbstractTransformation implements Transformation {

    @Override
    public Image apply(Image image) {

        // Taille de l'image d'origine
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        // la taille de l'image résultat
        int newWidth = getNewWidth(width, height);
        int newHeight = getNewHeight(width, height);

        // Image à modifier
        WritableImage result = new WritableImage(newWidth, newHeight);

        // Lecture des pixels de l'image source
        PixelReader reader = image.getPixelReader();

        // ecriture des pixels dans la nouvelle image
        PixelWriter writer = result.getPixelWriter();

        // Appel de la logique spécifique à chaque transformation
        process(reader, writer, width, height);

        return result;
    }

    //largeur de l'image résultat
    protected int getNewWidth(int width, int height) {
        return width;
    }


     //hauteur de l'image résultat.

    protected int getNewHeight(int width, int height) {
        return height;
    }


    //
    protected abstract void process(
            PixelReader reader,
            PixelWriter writer,
            int width,
            int height
    );
}
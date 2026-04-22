package org.example.transformation;

import javafx.scene.image.Image;

public interface Transformation {
    Image apply(Image image);
    String getName();
}
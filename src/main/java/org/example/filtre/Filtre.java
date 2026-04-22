package org.example.filtre;

import javafx.scene.image.Image;

public interface Filtre{
    Image apply(Image image);
    String getName();
}

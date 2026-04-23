package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.example.filtre.Filtre;
import org.example.filtre.FiltreNoirEtBlanc;
import org.example.filtre.FiltreSepia;
import org.example.filtre.FiltreRGBSwap;
import org.example.filtre.FiltrePrewitt;
import java.io.File;
import org.example.transformation.Transformation;
import org.example.transformation.Rotation;
import org.example.transformation.Symetrie;
import javafx.scene.control.ComboBox;

public class Controller {

    private Image currentImage;
    @FXML
    private ImageView imageView;

    @FXML
    private ComboBox<Filtre> comboFiltres;

    @FXML
    private ComboBox<Transformation> comboTransformations;

    private Image originalImage;

    @FXML
    public void initialize() {
        // On ajoute les filtres dans la liste déroulante
        comboFiltres.getItems().addAll(
                new FiltreNoirEtBlanc(),
                new FiltreSepia(),
                new FiltreRGBSwap(),
                new FiltrePrewitt()
        );
        comboFiltres.setConverter(new javafx.util.StringConverter<Filtre>() {
            @Override
            public String toString(Filtre filtre) {
                return filtre == null ? "" : filtre.getName();
            }

            @Override
            public Filtre fromString(String s) {
                return null;
            }
        });
        comboTransformations.getItems().addAll(
                new Rotation(),
                new Symetrie(true),
                new Symetrie(false)
        );
        comboTransformations.setConverter(new javafx.util.StringConverter<Transformation>() {
            @Override
            public String toString(Transformation t) {
                return t == null ? "" : t.getName();
            }
            @Override
            public Transformation fromString(String s) { return null; }
        });

        imageView.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                imageView.fitWidthProperty().bind(newScene.widthProperty());
                imageView.fitHeightProperty().bind(newScene.heightProperty().subtract(60));
            }
        });imageView.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                imageView.fitWidthProperty().bind(newScene.widthProperty());
                imageView.fitHeightProperty().bind(newScene.heightProperty().subtract(60));
            }
        });
    }

    @FXML
    public void handleLoadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.bmp")
        );
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            originalImage = new Image(selectedFile.toURI().toString());
            imageView.setImage(originalImage);
        }
        currentImage = originalImage;
    }

    @FXML
    public void handleAppliquerFiltre() {
        Filtre filtre = comboFiltres.getValue();
        if (filtre != null && originalImage != null) {
            currentImage = filtre.apply(currentImage);
            imageView.setImage(currentImage);
        }
    }

    @FXML
    public void handleReinitialiser() {
        if (originalImage != null) {
            imageView.setImage(originalImage);
        }
    }

    @FXML
    public void handleAppliquerTransformation() {
        Transformation t = comboTransformations.getValue();
        if (t != null && originalImage != null) {
            currentImage = t.apply(currentImage);
            imageView.setImage(currentImage);
        }
    }
}
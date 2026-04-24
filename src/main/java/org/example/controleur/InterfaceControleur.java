package org.example.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import org.example.modele.entite.ImageModele;
import org.example.modele.filtre.Filtre;
import org.example.modele.filtre.FiltreNoirEtBlanc;
import org.example.modele.filtre.FiltreSepia;
import org.example.modele.filtre.FiltreRGBSwap;
import org.example.modele.filtre.FiltrePrewitt;
import org.example.modele.transformation.Transformation;
import org.example.modele.transformation.Rotation;
import org.example.modele.transformation.Symetrie;
import javafx.stage.FileChooser;
import java.io.File;

public class InterfaceControleur {

    @FXML
    private ImageView imageView;

    @FXML
    private ComboBox<Filtre> comboFiltres;

    @FXML
    private ComboBox<Transformation> comboTransformations;

    private final ImageModele modele = new ImageModele();

    @FXML
    public void initialize() {
        comboFiltres.getItems().addAll(
                new FiltreNoirEtBlanc(),
                new FiltreSepia(),
                new FiltreRGBSwap(),
                new FiltrePrewitt()
        );
        comboFiltres.setConverter(new javafx.util.StringConverter<>() {
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
        comboTransformations.setConverter(new javafx.util.StringConverter<>() {
            @Override
            public String toString(Transformation t) {
                return t == null ? "" : t.getName();
            }

            @Override
            public Transformation fromString(String s) {
                return null;
            }
        });

        imageView.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                imageView.fitWidthProperty().bind(newScene.widthProperty());
                imageView.fitHeightProperty().bind(newScene.heightProperty().subtract(60));
            }
        });
    }

    @FXML
    public void handleChargerImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.bmp")
        );
        File fichier = fileChooser.showOpenDialog(null);
        if (fichier != null) {
            modele.chargerImage(fichier);
            imageView.setImage(modele.getImageCourante());
        }
    }

    @FXML
    public void handleAppliquerFiltre() {
        Filtre filtre = comboFiltres.getValue();
        if (filtre != null && modele.aUneImage()) {
            modele.setImageCourante(filtre.apply(modele.getImageOriginale()));
            imageView.setImage(modele.getImageCourante());
        }
    }

    @FXML
    public void handleAppliquerTransformation() {
        Transformation t = comboTransformations.getValue();
        if (t != null && modele.aUneImage()) {
            modele.setImageCourante(t.apply(modele.getImageCourante()));
            imageView.setImage(modele.getImageCourante());
        }
    }

    @FXML
    public void handleReinitialiser() {
        if (modele.aUneImage()) {
            modele.reinitialiser();
            imageView.setImage(modele.getImageCourante());
        }
    }
}
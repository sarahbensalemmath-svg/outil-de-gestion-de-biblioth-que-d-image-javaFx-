package org.example.controleur;

import javafx.event.ActionEvent;
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
import org.example.modele.securite.CrypterImage;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;

public class InterfaceControleur {

    @FXML
    //affiche l'image stockée dans image modele
    private ImageView imageView;

    @FXML   //c'est la liste (menu ) des filtres que l'utilisateur vois
    private ComboBox<Filtre> comboFiltres;

    @FXML
    //menu déroulant des transformations
    private ComboBox<Transformation> comboTransformations;

    //modèle qui gère toute la logique de l'image flotres , chargement ...
    private final ImageModele modele = new ImageModele();

    @FXML
    public void initialize() {
        //ajoute les filtres dans la list
        comboFiltres.getItems().addAll(
                new FiltreNoirEtBlanc(),
                new FiltreSepia(),
                new FiltreRGBSwap(),
                new FiltrePrewitt()
        );
        //gere les noms des filtres dans la liste
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
        //ajute des transformations
        comboTransformations.getItems().addAll(
                new Rotation(),
                new Symetrie(true),
                new Symetrie(false)
        );
        //gestions de leurs noms dans la liste affichée
        comboTransformations.setConverter(new javafx.util.StringConverter<>() {
            @Override
            public String toString(Transformation t) {
                return t == null ? "":t.getName();
            }

            @Override
            public Transformation fromString(String s) {
                return null;
            }
        });

        //fit la taille de l'image à la taille de la fenetre
        imageView.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                imageView.fitWidthProperty().bind(newScene.widthProperty());
                imageView.fitHeightProperty().bind(newScene.heightProperty().subtract(60));
            }
        });
    }

    @FXML
    //charge l'image dans le modèle  et l'affiche
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
    //recupère l'image originale et lui applique le filtre
    public void handleAppliquerFiltre() {
        Filtre filtre = comboFiltres.getValue();
        if (filtre != null && modele.aUneImage()) {
            modele.setImageCourante(filtre.apply(modele.getImageOriginale()));
            imageView.setImage(modele.getImageCourante());
        }
    }

    @FXML
    //recupere la transformation et applique sur l'image courrante ( on accumule )
    public void handleAppliquerTransformation() {
        Transformation t = comboTransformations.getValue();
        if (t != null && modele.aUneImage()) {
            modele.setImageCourante(t.apply(modele.getImageCourante()));
            imageView.setImage(modele.getImageCourante());
        }
    }

    @FXML
    //remets l'image originale
    public void handleReinitialiser() {
        if (modele.aUneImage()) {
            modele.reinitialiser();
            imageView.setImage(modele.getImageCourante());
        }
    }
    @FXML
    public void handleCrypter() throws Exception {
        if (!modele.aUneImage()) return;

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Chiffrer l'image");
        dialog.setHeaderText("Entrez un mot de passe");
        dialog.setContentText("Mot de passe :");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().isEmpty()) {
            CrypterImage crypter = new CrypterImage();
            modele.setImageCourante(crypter.chiffrer(modele.getImageCourante(), result.get()));
            imageView.setImage(modele.getImageCourante());
        }
    }


    @FXML
    public void handleDecrypter() throws Exception {
        if (!modele.aUneImage()) return;

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Déchiffrer l'image");
        dialog.setHeaderText("Entrez le mot de passe");
        dialog.setContentText("Mot de passe :");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().isEmpty()) {
            CrypterImage crypter = new CrypterImage();
            modele.setImageCourante(crypter.dechiffrer(modele.getImageCourante(), result.get()));
            imageView.setImage(modele.getImageCourante());
        }
    }
}
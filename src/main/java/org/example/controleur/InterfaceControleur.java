//controleur principele ( selon modèle MVC ) gère les interactions entre l'utilisateur ( la view )
// et les services (le modèle )
package org.example.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.example.modele.filtre.*;
import org.example.modele.transformation.*;
import org.example.modele.entite.service.ImageService;
import org.example.modele.entite.service.TagService;
import java.io.File;
import java.util.List;
import java.util.Optional;

public class InterfaceControleur {

    @FXML private ImageView imageView;
    @FXML private ComboBox<Filtre> comboFiltres;
    @FXML private ComboBox<Transformation> comboTransformations;
    @FXML private TextField champTag;
    @FXML private ListView<String> listeTags;
    @FXML private TextField champRecherche;
    @FXML private ListView<String> listeResultats;

    private final ImageService imageService = new ImageService();
    private final TagService tagService = new TagService();

    // Composants de l'interface (boutons , listes ...)depuis interface.wFXML
    @FXML
    public void initialize() {   //préparations des menus déroulants
        comboFiltres.getItems().addAll(
                new FiltreNoirEtBlanc(),
                new FiltreSepia(),
                new FiltreRGBSwap(),
                new FiltrePrewitt()
        );
        //affiche les noms lisibles des filtres
        comboFiltres.setConverter(new javafx.util.StringConverter<>() {
            @Override public String toString(Filtre f) { return f == null ? "" : f.getName(); }
            @Override public Filtre fromString(String s) { return null; }
        });
//ajoute les transformations dans le menu
        comboTransformations.getItems().addAll(
                new Rotation(),
                new Symetrie(true),
                new Symetrie(false)
        );
        //affiche les noms de transformations
        comboTransformations.setConverter(new javafx.util.StringConverter<>() {
            @Override public String toString(Transformation t) { return t == null ? "" : t.getName(); }
            @Override public Transformation fromString(String s) { return null; }
        });

        //adaptation automatique à la taille de la fenetre
        imageView.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                imageView.fitWidthProperty().bind(newScene.widthProperty());
                imageView.fitHeightProperty().bind(newScene.heightProperty().subtract(60));
            }
        });

        //choisir
        //charge l'image correspondate au choix effectué si elle exite
        listeResultats.setOnMouseClicked(event -> {
            String chemin = listeResultats.getSelectionModel().getSelectedItem();
            if (chemin != null) {
                File fichier = new File(chemin);
                if (fichier.exists()) {
                    imageService.charger(fichier);
                    imageView.setImage(imageService.getImageCourante());
                    rafraichirListeTags();
                }
            }
        });
    }

    @FXML
    // Ouvre l'explorateur de fichiers pour choisir une image
    public void handleChargerImage() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.bmp")
        );//accepte uniquement les formats d'image courants PNG, JPG, JPEG et BMP
        File fichier = fc.showOpenDialog(null);
        if (fichier != null) {
            imageService.charger(fichier);
            imageView.setImage(imageService.getImageCourante());
            rafraichirListeTags();
        }
    }

    @FXML
    public void handleAppliquerFiltre() { //applique le  filtre choisi et l'enregistre comme tag
        Filtre filtre = comboFiltres.getValue(); //recupere le filtre choisi
        if (filtre == null || !imageService.aUneImage()) return;
        imageView.setImage(imageService.appliquerFiltre(filtre));
        tagService.enregistrerFiltre(imageService.getFichier().getAbsolutePath(), filtre.getName());
    }

    @FXML
    public void handleAppliquerTransformation() {//applique la transformation choisie et l'enregistre comme taG
        Transformation t = comboTransformations.getValue();//recupere la valeur de la transformation
        if (t == null || !imageService.aUneImage()) return;// si la valeur de la trasformation est null on fait rien
        imageView.setImage(imageService.appliquerTransformation(t));//applqieu la transformation sinon
        tagService.enregistrerFiltre(imageService.getFichier().getAbsolutePath(), t.getName());//enregistre comme tag
    }

    @FXML
    //remettre l'image dans son état d'origine sans filtre ni transformation
    public void handleReinitialiser() {
        if (!imageService.aUneImage()) return;
        imageService.reinitialiser();
        imageView.setImage(imageService.getImageCourante());
    }

    @FXML
    // Chiffre l'image avec un mot de passe saisi par l'utilisateur
    public void handleCrypter() throws Exception {
        if (!imageService.aUneImage()) return;
        Optional<String> result = demanderMotDePasse("Chiffrer l'image");
        if (result.isPresent() && !result.get().isEmpty()) {// Si l'utilisateur a bien saisi un mot de passe ça donne un chiffrement
            imageView.setImage(imageService.chiffrer(result.get()));
        }
    }

    @FXML
    //vérifie si l'utilisateur a bien saisi le bon mot de  passe (celui utilisé pour crypter ) et la remets  en état
    public void handleDecrypter() throws Exception {
        if (!imageService.aUneImage()) return;
        Optional<String> result = demanderMotDePasse("Déchiffrer l'image");
        if (result.isPresent() && !result.get().isEmpty()) {
            imageView.setImage(imageService.dechiffrer(result.get()));
        }
    }

    @FXML
    public void handleAjouterTag() {
        if (!imageService.aUneImage()) {
            new Alert(Alert.AlertType.WARNING, "Chargez d'abord une image !").showAndWait();
            return;
        }
        String tag = champTag.getText().trim();
        if (tag.isEmpty()) return;
        tagService.ajouterTag(imageService.getFichier().getAbsolutePath(), tag);
        champTag.clear();
        rafraichirListeTags();
    }

    @FXML
    public void handleSupprimerTag() {
        if (!imageService.aUneImage()) return;
        String tagSelectionne = listeTags.getSelectionModel().getSelectedItem();
        if (tagSelectionne == null) return;
        tagService.supprimerTag(imageService.getFichier().getAbsolutePath(), tagSelectionne);
        rafraichirListeTags();
    }

    @FXML
    public void handleRechercherParTag() {
        String tag = champRecherche.getText().trim();
        if (tag.isEmpty()) return;
        List<String> resultats = tagService.rechercherParTag(tag);
        listeResultats.getItems().clear();
        if (resultats.isEmpty()) {
            listeResultats.getItems().add("Aucune image trouvée pour : " + tag);
        } else {
            listeResultats.getItems().addAll(resultats);
        }
    }

    @FXML
    public void handleEnregistrer() throws Exception {
        if (!imageService.aUneImage()) return;
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
        File fichier = fc.showSaveDialog(null);
        if (fichier != null) {
            imageService.enregistrer(fichier);
        }
    }

    private void rafraichirListeTags() {
        listeTags.getItems().clear();
        if (imageService.aUneImage()) {
            listeTags.getItems().addAll(
                    tagService.getTags(imageService.getFichier().getAbsolutePath())
            );
        }
    }
//affichage de la fenetre pour bien saisir le mot de passe
    private Optional<String> demanderMotDePasse(String titre) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(titre);
        dialog.setHeaderText("Entrez un mot de passe");
        dialog.setContentText("Mot de passe :");
        return dialog.showAndWait();
    }
}
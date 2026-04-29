package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

//on a herité de application qui est la classe de base pour tout app javaFX
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //chargement du fichier fxml qui définit l'interface graphique
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/Interface.fxml")
        );

        //création de la scene à partir du fichier fxml
        Scene scene = new Scene(loader.load());

        //titre  de la fenetre et démarrage de la scène
        stage.setTitle("Outil de gestion d'une bibliothèque d'images");
        stage.setScene(scene);

        //le plein écran au démarrage
        stage.setMaximized(true);
        stage.show();

    }
    // Point d'entrée JavaFX : lance l'application et déclenche l'appel de la méthode start()
    public static void main(String[] args) {
        launch(args);
    }
}
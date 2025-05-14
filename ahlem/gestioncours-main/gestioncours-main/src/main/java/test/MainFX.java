package test; // adapte à ton package

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class
MainFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // --- C’EST ICI que tu mets getResource(...) ---
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/views/GererCours.fxml"));

        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gestion des cours");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

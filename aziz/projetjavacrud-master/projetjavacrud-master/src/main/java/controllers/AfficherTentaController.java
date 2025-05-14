package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import models.Tentative;
import services.TentativeService;

import java.util.List;
import java.io.IOException;

public class AfficherTentaController {

    @FXML
    private TableView<Tentative> tentativeTable;

    @FXML
    private TableColumn<Tentative, Integer> idtCol;

    @FXML
    private TableColumn<Tentative, Integer> idquizCol;

    @FXML
    private TableColumn<Tentative, Integer> idqCol;

    @FXML
    private TableColumn<Tentative, Integer> idetudCol;

    @FXML
    private TableColumn<Tentative, Boolean> reponseCol;

    private final TentativeService tentativeService = new TentativeService();

    public void initialize() {
        // Lier les colonnes avec les propriétés de Tentative
        idtCol.setCellValueFactory(new PropertyValueFactory<>("idt"));
        idquizCol.setCellValueFactory(new PropertyValueFactory<>("idquiz"));
        idqCol.setCellValueFactory(new PropertyValueFactory<>("idq"));
        idetudCol.setCellValueFactory(new PropertyValueFactory<>("idetud"));
        reponseCol.setCellValueFactory(new PropertyValueFactory<>("reponse"));
    }

    public void afficherTentativesParQuiz(int idquiz) {
        List<Tentative> tentatives = tentativeService.recupererParQuiz(idquiz);
        tentativeTable.getItems().setAll(tentatives);
    }

    // Méthode pour revenir à l'accueil
    @FXML
    private void naviguerAcceuil(ActionEvent event) {
        try {
            // Charger la vue d'accueil (par exemple, "file.fxml")
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/file.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur de l'accueil, si nécessaire
            FileController fileController = loader.getController();

            // Fermer la fenêtre actuelle
            Stage stage = (Stage) tentativeTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Page d'accueil");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger la page d'accueil.");
        }
    }

    // Méthode pour quitter le programme
    @FXML
    private void QuitterProgramme(ActionEvent event) {
        // Fermer la fenêtre et quitter l'application
        Stage stage = (Stage) tentativeTable.getScene().getWindow();
        stage.close();  // Ferme la fenêtre actuelle
        System.exit(0); // Quitte l'application
    }

    // Méthode pour afficher des alertes
    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

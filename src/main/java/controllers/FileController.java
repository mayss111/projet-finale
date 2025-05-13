package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Question;
import services.QuestionService;

import java.io.IOException;
import java.util.List;

public class FileController {

    @FXML
    private Button SupprimerQuiz;

    @FXML
    private Button AfficherQuiz;

    @FXML
    private Button AfficherTenta;

    @FXML
    private Button CreerQuiz;

    @FXML
    private Button Quitter;

    @FXML
    private Button ModifierQuiz;

    @FXML
    private TextField idquizTF;

    private final QuestionService questionService = new QuestionService();

    @FXML
    void AfficherQuiz(ActionEvent event) {
        try {
            int quizId = Integer.parseInt(idquizTF.getText().trim());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherquiz.fxml")); // Correct path
            Parent root = loader.load();
            AfficherQuizController controller = loader.getController();
            controller.setIdQuiz(quizId);
            Stage stage = (Stage) idquizTF.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Questions du Quiz");
            stage.show();
        } catch (NumberFormatException e) {
            showAlert("Erreur de saisie", "Veuillez entrer un ID de quiz valide.");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur de chargement", "Impossible de charger la page du quiz.");
        }
    }

    @FXML
    void CreerQuiz(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/creer.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Créer un Quiz");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ouvrir la page de création.");
        }
    }

    @FXML
    void ModifierQuiz(ActionEvent event) {
        try {
            int quizId = Integer.parseInt(idquizTF.getText().trim());
            List<Question> questions = questionService.recupererQuestionsEtReponsesParQuizId(quizId);
            if (questions.isEmpty()) {
                showAlert("Erreur", "Aucune question trouvée pour ce quiz.");
                return;
            }

            Question questionToModify = questions.get(0);  // Modifier la première question
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifier.fxml"));
            Parent root = loader.load();

            ModifierController modifierController = loader.getController();
            modifierController.setQuizId(quizId);
            modifierController.setQuestion(questionToModify);

            Stage stage = (Stage) idquizTF.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier la question");
            stage.show();
        } catch (NumberFormatException e) {
            showAlert("Erreur de saisie", "Veuillez entrer un ID valide.");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger la vue de modification.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur inconnue", "Une erreur est survenue, veuillez réessayer.");
        }
    }

    @FXML
    void SupprimerQuiz(ActionEvent event) {
        try {
            int idquiz = Integer.parseInt(idquizTF.getText().trim());
            questionService.supprimer(idquiz);
            showAlert("Succès", "Toutes les questions du quiz ont été supprimées.");
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer un ID de quiz valide.");
        }
    }

    @FXML
    void Quitter(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void AfficherTenta(ActionEvent event) {
        try {

            int idQuiz = Integer.parseInt(idquizTF.getText().trim());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/affichertenta.fxml"));
            Parent root = loader.load();
            AfficherTentaController controller = loader.getController();
            controller.afficherTentativesParQuiz(idQuiz);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Tentatives du Quiz");
            stage.show();
        } catch (NumberFormatException e) {
            showAlert("Erreur de saisie", "Veuillez entrer un ID de quiz valide.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'afficher les tentatives : " + e.getMessage());
        }
    }

    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR); // Use ERROR for errors
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

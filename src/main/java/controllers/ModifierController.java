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

public class ModifierController {

    @FXML
    private TextField idqTF;

    @FXML
    private TextField questionTF;

    @FXML
    private TextField prop1TF;

    @FXML
    private TextField prop2TF;

    @FXML
    private TextField propcTF;

    @FXML
    private Button ModifierQuiz;

    @FXML
    private Button goAcceuil;

    @FXML
    private Button quitter;

    private Question questionActuelle;
    private int quizId;

    public void setQuestion(Question question) {
        this.questionActuelle = question;
        if (question != null) {
            idqTF.setText(String.valueOf(question.getIdq()));
            questionTF.setText(question.getQuestion());
            prop1TF.setText(question.getProp1());
            prop2TF.setText(question.getProp2());
            propcTF.setText(question.getPropc());
            this.quizId = question.getIdquiz(); // Stocker l'ID du quiz
        }
    }

    public void setQuizId(int idquiz) {
        this.quizId = idquiz;
    }

    @FXML
    void modifierQuestion(ActionEvent event) {
        if (questionActuelle == null) {
            showAlert("Erreur", "Aucune question à modifier.");
            return;
        }

        try {
            int idq = Integer.parseInt(idqTF.getText().trim());
            String texte = questionTF.getText().trim();
            String prop1 = prop1TF.getText().trim();
            String prop2 = prop2TF.getText().trim();
            String propc = propcTF.getText().trim();

            if (texte.isEmpty() || prop1.isEmpty() || prop2.isEmpty() || propc.isEmpty()) {
                showAlert("Validation", "Tous les champs doivent être remplis.");
                return;
            }

            Question q = new Question(idq, quizId, texte, prop1, prop2, propc);
            new QuestionService().modifier(q);

            showAlert("Succès", "✅ La question a été modifiée avec succès.");

        } catch (NumberFormatException e) {
            showAlert("Erreur", "L'ID de la question doit être un entier.");
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue : " + e.getMessage());
        }
    }

    @FXML
    void naviguerAcceuil(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/file.fxml"));
            Stage stage = (Stage) goAcceuil.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Accueil");
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger la page d'accueil.");
        }
    }

    @FXML
    void QuitterProgramme(ActionEvent event) {
        System.exit(0);
    }

    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

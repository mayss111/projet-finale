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

public class CreerController {

    @FXML
    private Button Acceuil;

    @FXML
    private Button Quitter;

    @FXML
    private Button ajouterQuiz;

    @FXML
    private Button goAcceuil;

    @FXML
    private TextField prop1TF;

    @FXML
    private TextField prop2TF;

    @FXML
    private TextField propcTF;

    @FXML
    private TextField questionTF;

    private int quizId; // ID du quiz à associer
    private Question questionExistante = null; // Pour modification

    // Setter appelé par FileController
    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    // Setter pour modification d'une question
    public void setQuestion(Question q) {
        this.questionExistante = q;
        questionTF.setText(q.getQuestion());
        prop1TF.setText(q.getProp1());
        prop2TF.setText(q.getProp2());
        propcTF.setText(q.getPropc());
    }

    @FXML
    void QuitterProgramme(ActionEvent event) {
        System.exit(0); // Ferme l'application
    }

    @FXML
    void naviguerAcceuil(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/file.fxml"));
            Stage stage = (Stage) goAcceuil.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ajouterquiz(ActionEvent event) {
        String texte = questionTF.getText().trim();
        String prop1 = prop1TF.getText().trim();
        String prop2 = prop2TF.getText().trim();
        String propc = propcTF.getText().trim();

        if (texte.isEmpty() || prop1.isEmpty() || prop2.isEmpty() || propc.isEmpty()) {
            showAlert("Champs manquants", "Veuillez remplir tous les champs.");
            return;
        }

        QuestionService service = new QuestionService();

        if (questionExistante == null) {
            // Création
            Question nouvelle = new Question(texte, prop1, prop2, propc, quizId);
            service.ajouter(nouvelle);
            showAlert("Succès", "✅ Question ajoutée avec succès !");
        } else {
            // Modification
            Question modifiee = new Question(
                    questionExistante.getIdq(),
                    quizId,
                    texte,
                    prop1,
                    prop2,
                    propc
            );
            service.modifier(modifiee);
            showAlert("Succès", "✅ Question modifiée avec succès !");
        }
    }

    private void showAlert(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }
}

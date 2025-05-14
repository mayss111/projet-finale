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
import utils.EmailSender;

import jakarta.mail.MessagingException;
import java.io.IOException;

public class CreerController {

    @FXML
    private Button goAcceuil;

    @FXML
    private Button Quitter;

    @FXML
    private Button ajouterQuiz;

    @FXML
    private TextField questionTF;

    @FXML
    private TextField prop1TF;

    @FXML
    private TextField prop2TF;

    @FXML
    private TextField propcTF;

    private int quizId;
    private Question questionExistante = null;

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public void setQuestion(Question q) {
        this.questionExistante = q;
        questionTF.setText(q.getQuestion());
        prop1TF.setText(q.getProp1());
        prop2TF.setText(q.getProp2());
        propcTF.setText(q.getPropc());
    }

    @FXML
    void QuitterProgramme(ActionEvent event) {
        System.exit(0);
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
            showAlert("Erreur", "Impossible de charger la page d'accueil.");
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
        String action, message;

        if (questionExistante == null) {
            Question nouvelle = new Question(texte, prop1, prop2, propc, quizId);
            service.ajouter(nouvelle);
            action = "ajoutée";
            message = "✅ Question ajoutée avec succès !";
        } else {
            Question modifiee = new Question(
                    questionExistante.getIdq(),
                    quizId,
                    texte,
                    prop1,
                    prop2,
                    propc
            );
            service.modifier(modifiee);
            action = "modifiée";
            message = "✅ Question modifiée avec succès !";
        }

        showAlert("Succès", message);

        // Envoi de l'email
        try {
            String sujet = "Question " + action + " - Quiz #" + quizId;
            String corps = String.format("""
                    Bonjour,

                    Une question a été %s dans le quiz #%d.

                    Question : %s
                    Proposition 1 : %s
                    Proposition 2 : %s
                    Bonne réponse : %s

                    Cordialement.
                    """, action, quizId, texte, prop1, prop2, propc);

            EmailSender.sendSimpleEmail("mrazizgamerlol00@gmail.com", sujet, corps);
        } catch (MessagingException e) {
            e.printStackTrace();
            showAlert("Erreur d'email", "❌ L'email de notification n'a pas pu être envoyé.");
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

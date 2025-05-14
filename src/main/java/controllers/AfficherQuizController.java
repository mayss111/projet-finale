package controllers;

import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Question;
import services.QuestionService;

public class AfficherQuizController {

    private int idQuiz;

    public void setIdQuiz(int idQuiz) {
        this.idQuiz = idQuiz;
        chargerQuestions();
    }

    private void chargerQuestions() {
        QuestionService questionService = new QuestionService();
        try {
            List<Question> questions = questionService.recupererQuestionsEtReponsesParQuizId(idQuiz);
            ObservableList<Question> questionObservableList = FXCollections.observableList(questions);
            tableview.setItems(questionObservableList);

            idqtv.setCellValueFactory(new PropertyValueFactory<>("idq"));
            prop1tv.setCellValueFactory(new PropertyValueFactory<>("prop1"));
            prop2tv.setCellValueFactory(new PropertyValueFactory<>("prop2"));
            propctv.setCellValueFactory(new PropertyValueFactory<>("propc"));
            questiontv.setCellValueFactory(new PropertyValueFactory<>("question"));
        } catch (Exception e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
    }

    @FXML
    private Button Acceuil;

    @FXML
    private Button Quitter;

    @FXML
    private TableColumn<Question, Integer> idqtv;

    @FXML
    private TableColumn<Question, String> prop1tv;

    @FXML
    private TableColumn<Question, String> prop2tv;

    @FXML
    private TableColumn<Question, String> propctv;

    @FXML
    private TableColumn<Question, String> questiontv;

    @FXML
    private TableView<Question> tableview;

    @FXML
    void initialize() {
        // Vide : on charge après setIdQuiz
    }

    @FXML
    void Acceuil(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/file.fxml"));
            Stage stage = (Stage) Acceuil.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Quitter(ActionEvent event) {
        System.exit(0);
    }
}

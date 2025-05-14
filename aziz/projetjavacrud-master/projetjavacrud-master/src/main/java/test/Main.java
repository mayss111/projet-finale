package test;

import models.Question;
import models.Quiz;
import services.QuestionService;
import services.QuizService;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        int idQuiz = 1;
        String question = "Quelle est la capitale de la France ?";
        String prop1 = "Lyon";
        String prop2 = "Marseille";
        String propc = "Paris";

        // Création de l'objet Question
        Question q = new Question(question, prop1, prop2, propc, idQuiz);

        // Appel du service
        QuestionService qs = new QuestionService();
        qs.ajouter(q); // Cette méthode affichera le message de confirmation ou d'erreur

        QuizService quizService = new QuizService();

        // Appel de la méthode recuperer pour récupérer tous les quiz de la base de données
        List<Quiz> quizzes = quizService.recuperer();

        // Affichage des quiz récupérés
        if (quizzes.isEmpty()) {
            System.out.println("⚠️ Aucun quiz trouvé dans la base de données.");
        } else {
            System.out.println("Voici la liste des quiz :");
            for (Quiz quiz : quizzes) {
                System.out.println("Quiz ID: " + quiz.getId());
            }
        }}}







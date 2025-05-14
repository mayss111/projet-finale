package services;

import models.Question;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionService implements IService<Question> {
    private Connection cnx;
    public QuestionService() {
        cnx= MyDatabase.getInstance().getCnx();
    }



    @Override
    public void ajouter(Question question) {
        String req = "INSERT INTO question (idquiz, question, prop1, prop2, propc) VALUES ('"
                + question.getIdquiz() + "', '"
                + question.getQuestion() + "', '"
                + question.getProp1() + "', '"
                + question.getProp2() + "', '"
                + question.getPropc() + "')";
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("✅ Question ajoutée avec succès !");
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'ajout : " + e.getMessage());
        }
    }


    @Override
    public void modifier(Question question) {
        String req = "UPDATE question SET question = ?, prop1 = ?, prop2 = ?, propc = ?, idquiz = ? WHERE idq = ?";
        try {
            PreparedStatement qs = cnx.prepareStatement(req);
            qs.setString(1, question.getQuestion());
            qs.setString(2, question.getProp1());
            qs.setString(3, question.getProp2());
            qs.setString(4, question.getPropc());
            qs.setInt(5, question.getIdquiz());
            qs.setInt(6, question.getIdq());

            int rowsUpdated = qs.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Question mise à jour avec succès !");
            } else {
                System.out.println("⚠️ Aucune question mise à jour (idq introuvable).");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la mise à jour : " + e.getMessage());
        }
    }


    @Override
    public void suprrimer(int id) {

    }


    @Override
    public void supprimer(int idquiz) {
        String req = "DELETE FROM question WHERE idquiz = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idquiz);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("✅ Questions du quiz supprimées avec succès !");
            } else {
                System.out.println("⚠️ Aucune question trouvée pour cet ID de quiz.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la suppression des questions : " + e.getMessage());
        }
    }


    @Override
    public List<Question> recuperer() {
        List<Question> questions = new ArrayList<>();
        String req = "SELECT * FROM question";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                Question question = new Question(
                        rs.getInt("idq"),
                        rs.getInt("idquiz"),
                        rs.getString("question"),
                        rs.getString("prop1"),
                        rs.getString("prop2"),
                        rs.getString("propc")
                );
                questions.add(question);
            }

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération des questions : " + e.getMessage());
        }

        return questions;
    }
    public List<Question> recupererQuestionsEtReponsesParQuizId(int idquiz) {
        List<Question> questions = new ArrayList<>();
        String req = "SELECT idq, question, prop1, prop2, propc FROM question WHERE idquiz = ?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idquiz);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Créer une nouvelle question avec les données récupérées de la base
                Question question = new Question(
                        rs.getInt("idq"),  // ID de la question
                        idquiz,            // ID du quiz
                        rs.getString("question"), // Le texte de la question
                        rs.getString("prop1"),    // Première proposition
                        rs.getString("prop2"),    // Deuxième proposition
                        rs.getString("propc")     // Proposition correcte
                );
                questions.add(question);
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération des questions et réponses : " + e.getMessage());
        }

        return questions;
    }



}

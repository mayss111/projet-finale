package services;

import models.Quiz;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizService implements IService<Quiz> {
    private Connection cnx;

    public QuizService() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    @Override
    public void ajouter(Quiz quiz) {
        String req = "INSERT INTO quiz (id) VALUES (?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, quiz.getId());
            ps.executeUpdate();
            System.out.println("✅ Quiz ajouté avec succès !");
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'ajout du quiz : " + e.getMessage());
        }
    }

    @Override
    public void modifier(Quiz quiz) {

    }


    @Override
    public void suprrimer(int id) {
        String req = "DELETE FROM quiz WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("✅ Quiz supprimé avec succès !");
            } else {
                System.out.println("⚠️ Aucun quiz trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la suppression du quiz : " + e.getMessage());
        }
    }

    @Override
    public void supprimer(int idquiz) {

    }

    @Override
    public List<Quiz> recuperer() {
        List<Quiz> quizzes = new ArrayList<>();
        String req = "SELECT * FROM quiz";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                Quiz quiz = new Quiz(rs.getInt("id"));
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération des quizzes : " + e.getMessage());
        }
        return quizzes;
    }

}

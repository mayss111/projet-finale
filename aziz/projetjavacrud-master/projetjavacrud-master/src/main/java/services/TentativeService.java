package services;

import models.Tentative;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TentativeService {

    private Connection cnx;

    public TentativeService() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    // Récupérer toutes les tentatives
    public List<Tentative> recuperer() {
        List<Tentative> tentatives = new ArrayList<>();
        String req = "SELECT * FROM tentative";

        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Tentative t = new Tentative(
                        rs.getInt("idt"),
                        rs.getInt("idquiz"),
                        rs.getInt("idq"),
                        rs.getInt("idetud"),
                        rs.getBoolean("reponse")
                );
                tentatives.add(t);
            }
            System.out.println("✅ Toutes les tentatives ont été récupérées !");
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération des tentatives : " + e.getMessage());
        }

        return tentatives;
    }

    // Récupérer les tentatives d’un quiz spécifique
    public List<Tentative> recupererParQuiz(int idquiz) {
        List<Tentative> tentatives = new ArrayList<>();
        String req = "SELECT * FROM tentative WHERE idquiz = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, idquiz);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Tentative t = new Tentative(
                        rs.getInt("idt"),
                        rs.getInt("idquiz"),
                        rs.getInt("idq"),
                        rs.getInt("idetud"),
                        rs.getBoolean("reponse")
                );
                tentatives.add(t);
            }

            System.out.println("✅ Tentatives du quiz " + idquiz + " récupérées !");
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération des tentatives du quiz : " + e.getMessage());
        }

        return tentatives;
    }
}

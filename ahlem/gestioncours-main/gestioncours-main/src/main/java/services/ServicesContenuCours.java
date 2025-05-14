package services;

import contenu.ContenuCours;
import database.MyDatabase;
import gestioncours.Course;
import gestioncours.contenttype;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicesContenuCours{

    private final Connection cnx;

    public ServicesContenuCours() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    // Ajouter un contenu
    public void ajouter(ContenuCours c) throws SQLException {
        if (c.getTitre() == null || c.getType() == null || c.getUrl() == null) {
            throw new IllegalArgumentException("Titre, type, ou url manquant");
        }
        String req = "INSERT INTO contenu ( titre, type, url,idcours) VALUES ( ?, ?, ?,?)";
            PreparedStatement pre = cnx.prepareStatement(req);
        pre.setString(1, c.getTitre());
        pre.setString(2, c.getType().name());
        pre.setString(3, c.getUrl());
        pre.setString(4, String.valueOf(c.getIdCours()));
        pre.executeUpdate();
    }


//    // Modifier un contenu
//    public void modifier(ContenuCours c) throws SQLException {
//        String req = "UPDATE contenu SET titre=?, type=?, url=? WHERE id=?, idcours=?";
//        PreparedStatement pre = cnx.prepareStatement(req);
//        pre.setString(1, c.getTitre());
//        pre.setString(2, c.getType().name());
//        pre.setString(3, c.getUrl());
//        pre.setInt(4, c.getId()); // ✅ ID en int
//        pre.executeUpdate();
//    }
//
//    // Supprimer un contenu
//    public void supprimer(int id) throws SQLException {
//        String req = "DELETE FROM contenu WHERE id = ?"; // ✅ Correction table
//        PreparedStatement pre = cnx.prepareStatement(req);
//        pre.setInt(1, id); // ✅ ID en int
//        pre.executeUpdate();
//    }

    // Afficher tous les contenus
//    public List<ContenuCours> afficher() throws SQLException {
//        List<ContenuCours> contenus = new ArrayList<>();
//        String sql = "SELECT * FROM contenu";
//        Statement st = cnx.createStatement();
//        ResultSet rs = st.executeQuery(sql);
//        while (rs.next()) {
//            int id = rs.getInt("id"); // ✅ ID en int
//            String titre = rs.getString("titre");
//            contenttype type = contenttype.valueOf(rs.getString("type"));
//            String url = rs.getString("url");
//            int idcours = 0;
//            ContenuCours contenu = new ContenuCours(id, titre, type, url,idcours);
//            contenus.add(contenu);}
//        return contenus;}
//    public List<ContenuCours> getAll() {
//        return contenus;}
// Declare 'contenus' as a class-level variable
    private List<ContenuCours> contenus = new ArrayList<>();

    public List<ContenuCours> afficher() throws SQLException {
        contenus.clear(); // Clear existing data before refilling
        String sql = "SELECT * FROM contenu";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            int id = rs.getInt("id");
            String titre = rs.getString("titre");
            contenttype type = contenttype.valueOf(rs.getString("type"));
            String url = rs.getString("url");
            int idcours = 0; // Ensure this is correctly retrieved from the DB if needed
            ContenuCours contenu = new ContenuCours(id, titre, type, url, idcours);
            contenus.add(contenu);
        }
        return contenus;
    }

    public List<ContenuCours> getAll() {
        return contenus; // Now correctly references the class-level variable
    }


    public boolean checkCourseExists(int idCours) throws SQLException {
        // Requête pour vérifier si le cours existe dans la table gestioncours
        String query = "SELECT COUNT(*) FROM gestioncours WHERE id = ?";

        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setInt(1, idCours); // On lie l'ID du cours à la requête
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Si le count est supérieur à 0, le cours existe
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false; // Si aucun résultat n'a été trouvé, le cours n'existe pas
    }

}
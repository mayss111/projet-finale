package services;

import contenu.ContenuCours;
import database.MyDatabase;
import gestioncours.Course;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicesTreeviewcours {
    private final Connection cnx;
    public ServicesTreeviewcours () throws SQLException {
        // Utilisation de MyDatabase pour obtenir la connexion via la méthode getCnx()
        this.cnx = MyDatabase.getInstance().getCnx();
    }

//    public void modifier(Course c) throws SQLException {
//        String req = "UPDATE gestioncours SET titre=?, subject=?, niveau=?, contents=? WHERE id=?";
//        Connection cnx = MyDatabase.getInstance().getCnx();
//
//
//        try (PreparedStatement pre = cnx.prepareStatement(req)) {
//            //
//            // ✅ Log des valeurs avant la mise à jour
//            System.out.println("=== DONNÉES À ENVOYER ===");
//            System.out.println("Titre : " + c.getTitre());
//            System.out.println("Sujet : " + c.getSubject());
//            System.out.println("Niveau : " + c.getNiveau());
//            System.out.println("ID : " + c.getId());
//            //
//            pre.setString(1, c.getTitre());
//            pre.setString(2, c.getSubject());
//            pre.setString(3, c.getNiveau());
//
//            StringBuilder contentsBuilder = new StringBuilder();
//            for (ContenuCours contenu : c.getContents()) {
//                contentsBuilder.append(contenu.toString()).append("; ");
//            }
//            pre.setString(4, contentsBuilder.toString());
//            pre.setInt(5, c.getId());
//
//            int rows = pre.executeUpdate();
//            if (rows > 0) {
//                System.out.println("Cours modifié avec succès.");
//            } else {
//                System.out.println("Aucun cours modifié. ID introuvable.");
//            }
//        }
//    }
public void modifier(Course c) throws SQLException {
    String req = "UPDATE gestioncours SET titre=?, subject=?, niveau=?, contents=? WHERE id=?";
    Connection cnx = MyDatabase.getInstance().getCnx(); // 🔧 Correction ici

    try (PreparedStatement pre = cnx.prepareStatement(req)) {
        System.out.println("=== DONNÉES À ENVOYER ===");
        System.out.println("Titre : " + c.getTitre());
        System.out.println("Sujet : " + c.getSubject());
        System.out.println("Niveau : " + c.getNiveau());
        System.out.println("ID : " + c.getId());

        pre.setString(1, c.getTitre());
        pre.setString(2, c.getSubject());
        pre.setString(3, c.getNiveau());
        pre.setString(4, c.getContents());


        //StringBuilder contentsBuilder = new StringBuilder();
        //for (ContenuCours contenu : c.getContents()) {
          //  contentsBuilder.append(contenu.toString()).append("; ");
       // }
//pre.setString(4, contentsBuilder.toString());
        pre.setInt(5, c.getId());

        int rows = pre.executeUpdate();
        if (rows > 0) {
            System.out.println("Cours modifié avec succès.");
        } else {
            System.out.println("Aucun cours modifié. ID introuvable.");
        }
    }catch (SQLException e) {
        // Gestion des erreurs SQL
        System.out.println("Erreur lors de la modification du cours : " + e.getMessage());
        throw e;  // Rethrow pour que l'appelant puisse gérer l'exception
    }


}


    // Méthode pour supprimer un cours
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM gestioncours WHERE id = ?"; // Requête SQL pour supprimer un cours
        try (PreparedStatement stmt = cnx.prepareStatement(sql)) {
            stmt.setInt(1, id); // Associer l'ID à la requête
            stmt.executeUpdate(); // Exécuter la mise à jour (suppression)
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la suppression du cours : " + e.getMessage(), e);
        }
    }



    // Méthode pour ouvrir le contenu d'un cours (lien URL)
    public void ouvrirContenu(int idCours) {
        String sql = "SELECT contents FROM gestioncours WHERE id = ?";
        try {
            PreparedStatement stmt = cnx.prepareStatement(sql);
            stmt.setInt(1, idCours);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String contentsStr = rs.getString("contents");
                if (contentsStr == null || contentsStr.isEmpty()) {
                    System.out.println("Aucun contenu trouvé pour ce cours.");
                    return;
                }

                // Traitement de chaque lien dans le champ 'contents'
                String[] contenus = contentsStr.split(";");
                for (String lien : contenus) {
                    lien = lien.trim();
                    if (lien.startsWith("http")) { // Validation de l'URL
                        try {
                            // Tentative d'ouverture du lien
                            Desktop.getDesktop().browse(new URI(lien));
                            System.out.println("Ouverture : " + lien);
                        } catch (IOException | URISyntaxException e) {
                            System.out.println("Erreur ouverture : " + lien + " - " + e.getMessage());
                        }
                    } else {
                        // Message d'ignorance si le lien n'est pas valide
                        System.out.println("Contenu ignoré (non URL) : " + lien);
                    }
                }

            } else {
                System.out.println("Cours non trouvé.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    }

    public List<Course> afficher() throws SQLException {
        List<Course> cours = new ArrayList<>();
        String sql = "SELECT * FROM gestioncours";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("id");
            String title = rs.getString("titre");
            String subject = rs.getString("subject");
            String niveau = rs.getString("niveau");
            String contents = rs.getString("contents");


           // List<ContenuCours> contents = new ArrayList<>(); // À compléter selon le format
            Course c = new Course(id, title, subject, niveau, contents);
            cours.add(c);
        }

        return cours;
    }

    public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM gestioncours";  // SQL pour récupérer tous les cours
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("id");
            String titre = rs.getString("titre");
            String subject = rs.getString("subject");
            String niveau = rs.getString("niveau");
            String contents = rs.getString("contents");

            courses.add(new Course(id, titre, subject, niveau, contents));  // Ajouter un cours à la liste
        }
        return courses;  // Retourner la liste des cours
    }
}

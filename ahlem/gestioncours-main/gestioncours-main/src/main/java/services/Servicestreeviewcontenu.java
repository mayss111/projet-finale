package services;

import contenu.ContenuCours;
import database.MyDatabase;
import gestioncours.contenttype;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Servicestreeviewcontenu {
    private final Connection cnx;

    public Servicestreeviewcontenu() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    // ✅ Modifier un contenu
    public void modifier(ContenuCours c) throws SQLException {
        String req = "UPDATE contenu SET titre = ?, type = ?, url = ? WHERE id = ? AND idcours = ?";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setString(1, c.getTitre());
            pre.setString(2, c.getType().name());
            pre.setString(3, c.getUrl());
            pre.setInt(4, c.getId());
            pre.setInt(5, c.getIdCours());
            pre.executeUpdate();
        }
    }

    // ✅ Supprimer un contenu
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM contenu WHERE id = ?";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setInt(1, id);
            pre.executeUpdate();
        }
    }

    // ✅ Charger tous les contenus liés à un cours
    public List<ContenuCours> getContenusByCours(int idCours) throws SQLException {
        List<ContenuCours> liste = new ArrayList<>();
        String req = "SELECT * FROM contenu WHERE idcours = ?";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setInt(1, idCours);
            try (ResultSet rs = pre.executeQuery()) {
                while (rs.next()) {
                    ContenuCours c = new ContenuCours(
                            rs.getInt("id"),
                            rs.getString("titre"),
                            contenttype.valueOf(rs.getString("type")),
                            rs.getString("url"),
                            rs.getInt("idcours")
                    );
                    liste.add(c);
                }
            }
        }
        return liste;
    }
    public List<ContenuCours> afficher() throws SQLException {
        List<ContenuCours> liste = new ArrayList<>();
        String req = "SELECT * FROM contenu";
        try (Statement stm = cnx.createStatement(); ResultSet rs = stm.executeQuery(req)) {
            while (rs.next()) {
                ContenuCours c = new ContenuCours(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        contenttype.valueOf(rs.getString("type")),
                        rs.getString("url"),
                        rs.getInt("idcours")
                );
                liste.add(c);
            }
        }
        return liste;
    }


    public List<ContenuCours> afficherParCours(int idCours) throws SQLException {
        List<ContenuCours> liste = new ArrayList<>();
        String req = "SELECT * FROM contenu WHERE idcours = ?";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setInt(1, idCours);
            try (ResultSet rs = pre.executeQuery()) {
                while (rs.next()) {
                    ContenuCours c = new ContenuCours(
                            rs.getInt("id"),
                            rs.getString("titre"),
                            contenttype.valueOf(rs.getString("type")),
                            rs.getString("url"),
                            rs.getInt("idcours")
                    );
                    liste.add(c);
                }
            }
        }
        return liste;
    }

}

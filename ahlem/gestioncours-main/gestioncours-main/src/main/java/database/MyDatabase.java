package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDatabase {

    private  final Connection cnx;

    private static MyDatabase instance;

    private MyDatabase() {
        String url = "jdbc:mysql://localhost:3306/cours";

        String username = "root";
        String password = "";

        try {
            cnx = DriverManager.getConnection(url, username, password);
            System.out.println("✅ Connexion établie");
        } catch (SQLException ex) {
            System.err.println("❌ Erreur de connexion à la base : " + ex.getMessage());
            throw new RuntimeException("Échec de connexion à la base de données", ex);
        }

    }

    public static MyDatabase getInstance() {
        if (instance == null) {
            instance = new MyDatabase();
        }
        return instance;
    }
    public Connection getCnx() {
        return cnx;
    }
}

/*import java.sql.Connection;

import Entities.User;
import Utils.ConnectionManager;
import service.UserService;

public class TestDB {
    public static void main(String[] args) {
        Connection conn = ConnectionManager.getConnection();

        if (conn != null) {
            System.out.println("🟢 Test de connexion réussi !");
            ConnectionManager.closeconnection(conn);
        } else {
            System.out.println("🔴 Échec de la connexion.");
            return;
        }

        // Création d’un utilisateur fictif
        User newUser = createUser();

        // Insertion dans la base de données
        UserService userService = UserService.getInstance();
        userService.SignUp(newUser); // ou userService.addUser(newUser);
    }

    public static User createUser() {
        String nom = "Doe";
        String password = "Password123";
        String email = "johndoe@example.com";
        String firstName = "John";
        String address = "1001 Main St";
        int zipCode = 1001;
        String role = "user";


        User user = new User(nom, password, email, firstName, address,ville, zipCode, role);
        user.setIs_verified(false);
        user.setReset_token(null);
        user.setVerified("tt");// attention si la colonne n'accepte pas NULL
        user.setReset_token_expired_at(null);
       // attention si String non-null
        return user;
    }
}
*/
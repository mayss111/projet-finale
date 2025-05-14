package service;

import Entities.User;
import Interfaces.Iservice;
import Utils.ConnectionManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService implements Iservice<User> {

    public User userConnect;
    private static UserService instance;
    private User authenticatedUser;

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    @Override
    public void create(User entity) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (nom, password, email, prenom, role, is_verified, adress, ville, zipcode, reset_token, reset_token_expired_at, verified) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")){

            preparedStatement.setString(1, entity.getNom());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3, entity.getEmail());
            preparedStatement.setString(4, entity.getPrenom());
            preparedStatement.setString(5, entity.getRole());
            preparedStatement.setBoolean(6, entity.isIs_verified());
            preparedStatement.setString(7, entity.getAdress());
            preparedStatement.setString(8, entity.getVille());
            preparedStatement.setInt(9, entity.getZipcode());
            preparedStatement.setString(10, entity.getReset_token());
            preparedStatement.setTimestamp(11, entity.getReset_token_expired_at());
            preparedStatement.setString(12, entity.getVerified());
            preparedStatement.executeUpdate();
            System.out.println("User created successfully");
        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
        }
    }

    @Override
    public void update(User entity) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE user SET nom = ?, password = ?, email = ?, prenom = ?, role = ?, is_verified = ?, adress = ?, ville = ?, zipcode = ?, reset_token = ?, reset_token_expired_at = ?, verified = ? WHERE id = ?")) {

            preparedStatement.setString(1, entity.getNom());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3, entity.getEmail());
            preparedStatement.setString(4, entity.getPrenom());
            preparedStatement.setString(5, entity.getRole());
            preparedStatement.setBoolean(6, entity.isIs_verified());
            preparedStatement.setString(7, entity.getAdress());
            preparedStatement.setString(8, entity.getVille());
            preparedStatement.setInt(9, entity.getZipcode());
            preparedStatement.setString(10, entity.getReset_token());
            preparedStatement.setTimestamp(11, entity.getReset_token_expired_at());
            preparedStatement.setString(12, entity.getVerified());
            preparedStatement.setInt(13, entity.getId());
            preparedStatement.executeUpdate();
            System.out.println("User updated successfully");
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
        }
    }

    @Override
    public void delete(User entity) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user WHERE id = ?")) {

            preparedStatement.setInt(1, entity.getId());
            preparedStatement.executeUpdate();
            System.out.println("User deleted successfully");
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM user")) {
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("prenom"),
                        resultSet.getString("role"),
                        resultSet.getBoolean("is_verified"),
                        resultSet.getString("adress"),
                        resultSet.getString("ville"),
                        resultSet.getInt("zipcode"),
                        resultSet.getString("reset_token"),
                        resultSet.getTimestamp("reset_token_expired_at"),
                        resultSet.getString("verified")
                );
                userList.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all users: " + e.getMessage());
        }
        return userList;
    }

    public boolean SignUp(User user) {
        boolean success = false;
        String query = "INSERT INTO user (nom, password, email, prenom, role, is_verified, adress, ville, zipcode, reset_token, reset_token_expired_at, verified) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionManager.getConnection()) {
            if (conn == null) throw new SQLException("Failed to establish database connection.");

            try (PreparedStatement checkStmt = conn.prepareStatement("SELECT email FROM user WHERE email = ?")) {
                checkStmt.setString(1, user.getEmail());
                try (ResultSet resultSet = checkStmt.executeQuery()) {
                    if (!resultSet.next()) {
                        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
                        String hashedPassword = passwordEncoder.encode(user.getPassword());

                        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                            preparedStatement.setString(1, user.getNom());
                            preparedStatement.setString(2, hashedPassword);
                            preparedStatement.setString(3, user.getEmail());
                            preparedStatement.setString(4, user.getPrenom());
                            preparedStatement.setString(5, user.getRole());
                            preparedStatement.setBoolean(6, user.isIs_verified());
                            preparedStatement.setString(7, user.getAdress());
                            preparedStatement.setString(8, user.getVille());
                            preparedStatement.setInt(9, user.getZipcode());
                            preparedStatement.setString(10, user.getReset_token());
                            preparedStatement.setTimestamp(11, user.getReset_token_expired_at());
                            preparedStatement.setString(12, user.getVerified());
                            success = preparedStatement.executeUpdate() > 0;
                        }
                    } else {
                        System.out.println("Email already exists");
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return success;
    }

    public User authenticate(String email, String password) {
        User user = null;
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM user WHERE email = ?")) {

            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
                    if (passwordEncoder.matches(password, resultSet.getString("password"))) {
                        user = new User(
                                resultSet.getInt("id"),
                                resultSet.getString("nom"),
                                resultSet.getString("password"),
                                resultSet.getString("email"),
                                resultSet.getString("prenom"),
                                resultSet.getString("role"),
                                resultSet.getBoolean("is_verified"),
                                resultSet.getString("adress"),
                                resultSet.getString("ville"),
                                resultSet.getInt("zipcode"),
                                resultSet.getString("reset_token"),
                                resultSet.getTimestamp("reset_token_expired_at"),
                                resultSet.getString("verified")
                        );
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        authenticatedUser = user;
        return user;
    }

    public boolean ForgetPassword(String email, String newPassword) throws Exception {
        User user = getUserByEmail(email);
        if (user == null) return false;

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
        String hashedPassword = passwordEncoder.encode(newPassword);
        String query = "UPDATE user SET password = ? WHERE id = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, hashedPassword);
            preparedStatement.setInt(2, user.getId());
            return preparedStatement.executeUpdate() > 0;
        }
    }

    public User getUserByEmail(String email) {
        User user = null;
        String query = "SELECT * FROM user WHERE email = ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User(
                            resultSet.getInt("id"),
                            resultSet.getString("nom"),
                            resultSet.getString("password"),
                            resultSet.getString("email"),
                            resultSet.getString("prenom"),
                            resultSet.getString("role"),
                            resultSet.getBoolean("is_verified"),
                            resultSet.getString("adress"),
                            resultSet.getString("ville"),
                            resultSet.getInt("zipcode"),
                            resultSet.getString("reset_token"),
                            resultSet.getTimestamp("reset_token_expired_at"),
                            resultSet.getString("verified")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving user: " + e.getMessage());
        }
        return user;
    }

    public int getUserIdByEmail(String email) {
        User user = getUserByEmail(email);
        return user != null ? user.getId() : -1;
    }

    public boolean isEmailExist(String email) {
        boolean exists = false;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT email FROM user WHERE email = ?")) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                exists = resultSet.next();
            }
        } catch (SQLException e) {
            System.err.println("Error checking email existence: " + e.getMessage());
        }
        return exists;
    }

    public User getAuthenticatedUser() {
        return authenticatedUser;
    }

    public void setAuthenticatedUser(User authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    public boolean checkEmailExists(String email) {
        return isEmailExist(email);
    }
    public List<User> getVerified() {
        List<User> verifiedUsers = new ArrayList<>();
        String query = "SELECT * FROM user WHERE is_verified = true";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("prenom"),
                        resultSet.getString("role"),
                        resultSet.getBoolean("is_verified"),
                        resultSet.getString("adress"),
                        resultSet.getString("ville"),
                        resultSet.getInt("zipcode"),
                        resultSet.getString("reset_token"),
                        resultSet.getTimestamp("reset_token_expired_at"),
                        resultSet.getString("verified")
                );
                verifiedUsers.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving verified users: " + e.getMessage());
        }

        return verifiedUsers;
    }
}

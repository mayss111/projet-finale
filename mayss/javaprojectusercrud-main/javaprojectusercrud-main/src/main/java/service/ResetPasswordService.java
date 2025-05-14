package service;

import Entities.ResetPassword;
import Entities.User;
import Interfaces.IResetPasswordService;
import Utils.ConnectionManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.*;

public class ResetPasswordService implements IResetPasswordService {

    public void create(ResetPassword resetPassword) {
        String query = "INSERT INTO resetpassword (email, code, date, user_id) VALUES (?, ?, ?, ?)";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Prepare the fields
            User user = resetPassword.getUser();
            preparedStatement.setString(1, user.getEmail()); // email
            preparedStatement.setInt(2, resetPassword.getCode()); // code
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis())); // date_creation
            preparedStatement.setInt(4, getUserIdByEmail(user.getEmail())); // user_id

            // Execute the query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error creating reset password record: " + e.getMessage());
        }
    }


    public int getUserIdByEmail(String email) {
        UserService userService = new UserService();
        User user = userService.getUserByEmail(email);
        if (user != null) {
            return user.getId();
        } else {
            System.err.println("User not found for email: " + email);
            return -1; // Return an invalid ID or handle as needed
        }
    }
    @Override
    public ResetPassword get(User user) {
        ResetPassword resetPassword = null;
        String email = user.getEmail().trim(); // Trim to avoid any whitespace issues
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resetpassword WHERE email = ? ORDER BY date DESC LIMIT 1")) {

            System.out.println("Executing query with email: " + email); // Log email
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resetPassword = new ResetPassword();
                resetPassword.setUser(user);
                resetPassword.setCode(resultSet.getInt("code"));
                resetPassword.setDateCreation(resultSet.getTimestamp("date"));
            } else {
                System.out.println("No reset password entry found for the user with email: " + email);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving reset password: " + e.getMessage());
        }

        if (resetPassword != null) {
            System.out.println("Reset Password Entry Found: " + resetPassword);
        } else {
            System.out.println("resetPassword is null");
        }

        return resetPassword;
    }



    @Override
    public void ResetPassword(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        // Implement the logic to update user password in the database
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE user SET password = ? WHERE email = ?")) {

            preparedStatement.setString(1, hashedPassword);
            preparedStatement.setString(2, user.getEmail());

            preparedStatement.executeUpdate();
            System.out.println("Password updated successfully");
        } catch (SQLException e) {
            System.err.println("Error updating password: " + e.getMessage());
        }
    }
}

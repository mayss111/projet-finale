package controllers;


import Entities.ResetPassword;
import Entities.User;
import service.JavaMailUtil;
import service.ResetPasswordService;
import service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

public class ForgetPasswordController {

    @FXML
    private Button btnConfirmerCode;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSendCode;

    @FXML
    private Label lbCodeError;

    @FXML
    private Label lbConfirmPasswordReset;

    @FXML
    private Label lbEmail;

    @FXML
    private Label lbPasswordReset;

    @FXML
    private TextField tfCode;

    @FXML
    private PasswordField tfConfirmPasswordReset;

    @FXML
    private TextField tfEmailForget;

    @FXML
    private PasswordField tfPasswordReset;

    @FXML
    private VBox vboxCode;

    @FXML
    private VBox vboxEmail;

    @FXML
    private VBox vboxMain;

    @FXML
    private VBox vboxReset;

    String email;

    Parent fxml;
    private ResetPassword resetPassword;
    private UserService userService = new UserService(); // Initialize UserService
    private ResetPasswordService resetPasswordService = new ResetPasswordService(); // Initialize ResetPasswordService

    @FXML
    void fnConfirmerCode(ActionEvent event) {
        if (validateEmail()) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime threeHoursAgo = now.minusHours(3);

            User user = new User();
            user.setEmail(email);
            ResetPassword rp = resetPasswordService.get(user);

            if (rp != null) {
                if (rp.getCode() == Integer.parseInt(tfCode.getText())) {
                    if (rp.getDateCreation().toLocalDateTime().isBefore(threeHoursAgo)) {
                        lbCodeError.setText("Le code a expiré. Veuillez demander un nouveau code.");
                    } else {
                        lbCodeError.setText("");
                        vboxMain.getChildren().set(0, vboxReset);
                        vboxMain.getChildren().removeAll(vboxEmail, vboxCode);
                        vboxReset.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                    }
                } else {
                    lbCodeError.setText("Veuillez entrer un code valide");
                }
            } else {
                lbCodeError.setText("Aucun code de réinitialisation trouvé pour cet utilisateur. Veuillez envoyer un nouveau code.");
            }
        }
    }

    @FXML
    void fnReset(ActionEvent event) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(tfPasswordReset.getText());
        resetPasswordService.ResetPassword(user);

        // Move to the sign-in page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
            Parent root = loader.load();

            // Create a new scene with the loaded FXML
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            // Get the current stage
            Stage currentStage = (Stage) btnReset.getScene().getWindow();
            currentStage.close(); // Close the current stage

            // Create a new stage and set the scene
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.initStyle(StageStyle.TRANSPARENT);
            newStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void fnSendCode(ActionEvent event) {
        if (validateEmail()) {
            Random random = new Random();
            int randomNumber = random.nextInt(9000) + 1000;
            String randomString = String.valueOf(randomNumber);

            try {
                User user = new User();
                user.setEmail(tfEmailForget.getText());
                ResetPassword rp = new ResetPassword();
                rp.setUser(user);
                rp.setCode(randomNumber);
                rp.setDateCreation(new Timestamp(System.currentTimeMillis())); // Set the current time
                resetPasswordService.create(rp);
                JavaMailUtil.sendMail(tfEmailForget.getText(), "Reset Password", "If you forgot your password, please use this code to reset it: " + randomString);
                email = tfEmailForget.getText();
                vboxMain.getChildren().set(0, vboxCode);
                vboxMain.getChildren().removeAll(vboxEmail, vboxReset);
                vboxCode.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private boolean validateEmail() {
        String email = tfEmailForget.getText();
        if (isValidEmail(email) && userService.getUserByEmail(email) != null) {
            lbEmail.setText("");
            return true;
        } else {
            lbEmail.setText("Please enter a valid email");
            return false;
        }
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailPattern);
    }
}


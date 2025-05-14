package controllers;

import Entities.User;
import service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class SignupController implements Initializable {

    @FXML
    private Button btnsignUp;

    @FXML
    private ComboBox<String> cbRole;

    @FXML
    private Label lbAdress;

    @FXML
    private Label lbConfirmEmail;

    @FXML
    private Label lbConfirmPassword;

    @FXML
    private Label lbEmail;

    @FXML
    private Label lbNom;

    @FXML
    private Label lbPassword;

    @FXML
    private Label lbPrenom;

    @FXML
    private Label lbRole;

    @FXML
    private TextField tfAdress;

    @FXML
    private TextField tfConfirmEmail;

    @FXML
    private PasswordField tfConfirmPassword;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfNom;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private TextField tfPrenom;



    private boolean bConfirmEmail=false,bEqualPassword = false, bNom, bPrenom, bEmail = false, bAdd = false, bPass = false, bRole;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfEmail.setOnKeyReleased(this::validateEmail);
        tfConfirmEmail.setOnKeyReleased(this::validateConfirmEmail);
        tfPassword.setOnKeyReleased(this::validatePassword);
        tfAdress.setOnKeyReleased(this::validateAddress);
        tfNom.setOnKeyReleased(this::validateNom);
        tfPrenom.setOnKeyReleased(this::validatePrenom);
        cbRole.getItems().addAll("etudiant", "enseigant", "admin");
    }

    @FXML
    private void fnSignup(ActionEvent event) throws Exception {
        UserService ps = new UserService();
        clearErrorLabels();
        bNom = !tfNom.getText().isEmpty();
        bPrenom = !tfPrenom.getText().isEmpty();
        bEmail = isValidEmail(tfEmail.getText());
        bRole = cbRole.getValue()!=null && !cbRole.getValue().isEmpty();
        bConfirmEmail = tfConfirmEmail.getText().equals(tfEmail.getText());
        String passwordError = isValidPassword(tfPassword.getText(), tfNom.getText(), tfPrenom.getText());
        bPass = passwordError == null;
        bEqualPassword = bPass && tfPassword.getText().equals(tfConfirmPassword.getText());

        if (bNom && bPrenom && bEmail && bConfirmEmail && bPass && bEqualPassword && bAdd) {
            String address = tfAdress.getText();
            Pattern pattern = Pattern.compile("^(\\d{4}),\\s*([^,]+),\\s*(.+)$");
            Matcher matcher = pattern.matcher(address);
            if (matcher.matches()) {
                int zipCode = Integer.parseInt(matcher.group(1));
                String ville = matcher.group(2);
                String fullAddress = matcher.group(3);

                if (ps.SignUp(new User(tfNom.getText(), tfPassword.getText(),tfEmail.getText(), tfPrenom.getText(),  fullAddress, ville, zipCode, "[\"ROLE_"+cbRole.getValue()+"\"]"))) {
                    Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
                    tfNom.getScene().setRoot(root);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Account created successfully!");
                    alert.setHeaderText(null);
                    alert.setTitle("Success");
                    alert.show();
                } else {
                    showErrorAlert("Email already exists in the database!!");
                }
            } else {
                showErrorAlert("Invalid address format. Please use the format: zipCode, Ville, Address");
            }
        } else {
            showErrorAlert("All information should be valid!");
        }
    }

    private void clearErrorLabels() {
        lbNom.setText("");
        lbEmail.setText("");
        lbAdress.setText("");
        lbPassword.setText("");
        lbConfirmPassword.setText("");
        lbRole.setText("");
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.setTitle("Invalid Information");
        alert.setHeaderText(null);
        alert.show();

        if (!bNom) {
            lbNom.setText("Please enter your family name");
        }
        if (!bPrenom) {
            lbPrenom.setText("Please enter your first name");
        }
        if (!bEmail) {
            lbEmail.setText("Please enter a valid email");
        }
        if (!bPass) {
            lbPassword.setText("Please enter a valid password");
        }
        if (!tfConfirmPassword.getText().isEmpty() && !bEqualPassword) {
            lbConfirmPassword.setText("Passwords do not match");
        }
        if (!bAdd) {
            lbAdress.setText("Please enter a valid address");
        }

        if (!bRole){
            lbRole.setText("Please choose a role");
        }
    }

    private void validateEmail(KeyEvent event) {
        String email = tfEmail.getText();
        bEmail = isValidEmail(email);
        lbEmail.setText(bEmail ? "" : "Please enter a valid email");
    }

    private void validateConfirmEmail(KeyEvent event) {
        String confirmEmail = tfConfirmEmail.getText();
        bConfirmEmail = confirmEmail.equals(tfEmail.getText());
        lbConfirmEmail.setText(bConfirmEmail ? "" : "Emails do not match");
    }

    private void validatePassword(KeyEvent event) {
        String password = tfPassword.getText();
        String passwordError = isValidPassword(password, tfNom.getText(), tfPrenom.getText());
        bPass = passwordError == null;
        lbPassword.setText(bPass ? "" : passwordError);
    }

    private void validateAddress(KeyEvent event) {
        String address = tfAdress.getText();
        Pattern pattern = Pattern.compile("^(\\d{4}),\\s*([^,]+),\\s*(.+)$");
        Matcher matcher = pattern.matcher(address);
        bAdd = matcher.matches();
        lbAdress.setText(bAdd ? "" : "Invalid address format. Please use the format: zipCode, Ville, Address");
    }

    private void validateNom(KeyEvent event) {
        bNom = !tfNom.getText().isEmpty();
        lbNom.setText(bNom ? "" : "Please enter your family name");
    }

    private void validatePrenom(KeyEvent event) {
        bPrenom = !tfPrenom.getText().isEmpty();
        lbPrenom.setText(bPrenom ? "" : "Please enter your first name");
    }

    private void validateRole(KeyEvent event) {
        bRole = !cbRole.getValue().isEmpty();
        lbPrenom.setText(bPrenom ? "" : "Please choose a role");
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailPattern);
    }

    private String isValidPassword(String password, String Nom, String Prenom) {
        if (password.length() < 8) {
            return "Password must be at least 8 characters long";
        }
        if (!password.matches(".*[A-Z].*")) {
            return "Password must contain at least one uppercase letter";
        }
        if (!password.matches(".*[a-z].*")) {
            return "Password must contain at least one lowercase letter";
        }
        if (!password.matches(".*\\d.*")) {
            return "Password must contain at least one digit";
        }
        if (Nom != null && !Nom.isEmpty() && password.toLowerCase().contains(Nom.toLowerCase())) {
            return "Password cannot contain your family name";
        }
        if (Prenom != null && !Prenom.isEmpty() && password.toLowerCase().contains(Prenom.toLowerCase())) {
            return "Password cannot contain your name";
        }
        return null;
    }
}

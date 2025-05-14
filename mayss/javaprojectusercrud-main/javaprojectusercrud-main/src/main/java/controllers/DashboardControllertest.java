package controllers;
import Entities.User;
import service.ResetPasswordService;
import service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DashboardControllertest implements Initializable {
    @FXML
    private Pane Profile_cc;

    @FXML
    private TextField age_tf;

    @FXML
    private Button backInterface;

    @FXML
    private ScrollPane boxh;

    @FXML
    private Button btnModifProfil;

    @FXML
    private Button btnResetPass;

    @FXML
    private Label hello;

    @FXML
    private Label hello1;

    @FXML
    private Label hello11;

    @FXML
    private Label hello111;

    @FXML
    private Label hello1111;

    @FXML
    private Label hello11111;

    @FXML
    private Label hello1112;

    @FXML
    private Label hello2;

    @FXML
    private Label hello21;

    @FXML
    private Label lbConfirmePasswordReset;

    @FXML
    private Label lbEmailProfil;

    @FXML
    private Label lbFullAddresseProfil;

    @FXML
    private Label lbFullNameProfil;

    @FXML
    private Label lbNumTelProfil;

    @FXML
    private Label lbPasswordReset;

    @FXML
    private TextField nom_tf;

    @FXML
    private Pane pnprofile;

    @FXML
    private TextField prenom_tf;

    @FXML
    private TextField tfAdresse;

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


    private Stage stage;

    private Image image_url;

    private UserService userService;

    @FXML
    private Label lbUserNameGlobal;


    @FXML
    void add(ActionEvent event) {

    }

    @FXML
    void afficher(ActionEvent event) {

    }


    @FXML
    void fnLogOut(ActionEvent event) {
        try {
            // Load the new page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
            Parent root = loader.load();


            // Create a new stage for the new scene
            Stage newStage = new Stage();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            newStage.setScene(scene);
            newStage.initStyle(StageStyle.TRANSPARENT);
            newStage.show();

            // Close the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void fnProfil(ActionEvent event) {
        pnprofile.toFront();
    }

    @FXML
    void handleBrowser(ActionEvent event) {

    }

    @FXML
    void openInterface(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userService = UserService.getInstance();
        pnprofile.toBack();
        User authenticatedUser = userService.getAuthenticatedUser();
        tfEmail.setText(authenticatedUser.getEmail());
        tfPrenom.setText(authenticatedUser.getPrenom());
        tfNom.setText(authenticatedUser.getNom());
        tfAdresse.setText(authenticatedUser.getZipcode()+", "+authenticatedUser.getVille()+", "+authenticatedUser.getAdress());
        lbUserNameGlobal.setText(authenticatedUser.getNom() + " "+ authenticatedUser.getPrenom());
    }

    @FXML
    void ResetPasswordAction(ActionEvent event) throws Exception {
        userService = UserService.getInstance();
        User authenticatedUser = userService.getAuthenticatedUser();
        ResetPasswordService rps = new ResetPasswordService();
        authenticatedUser.setPassword(tfConfirmPassword.getText());
        rps.ResetPassword(authenticatedUser);
        tfPassword.setText("");
        tfConfirmPassword.setText("");
        showUpdate("Password Updated succesfully");

    }

    @FXML
    void changeProfilAction(ActionEvent event) {
        userService = UserService.getInstance();
        // Your existing initialization code...
        // Example usage:
        User authenticatedUser = userService.getAuthenticatedUser();
        authenticatedUser.setEmail(tfEmail.getText());
        authenticatedUser.setNom(tfNom.getText());
        authenticatedUser.setPrenom(tfPrenom.getText());
        String address = tfAdresse.getText();
        Pattern pattern = Pattern.compile("^(\\d{4}),\\s*([^,]+),\\s*(.+)$");
        Matcher matcher = pattern.matcher(address);
        if (matcher.matches()) {
            int zipCode = Integer.parseInt(matcher.group(1));
            String ville = matcher.group(2);
            String fullAddress = matcher.group(3);
            authenticatedUser.setZipcode(zipCode);
            authenticatedUser.setVille(ville);
            authenticatedUser.setAdress(fullAddress);
            userService.update(authenticatedUser);
            showUpdate("user infos updated succesfully");
            pnprofile.toBack();
        }else{
            showErrorAlert("check your information");
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.setTitle("Invalid Information");
        alert.setHeaderText(null);
        alert.show();
    }

    private void showUpdate(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.setTitle("User Updated");
        alert.setHeaderText(null);
        alert.show();
    }

    @FXML
    void fnUserName(MouseEvent event) {
        pnprofile.toFront();
    }
    @FXML
    void hiden_anchor(MouseEvent event) {


    }
}

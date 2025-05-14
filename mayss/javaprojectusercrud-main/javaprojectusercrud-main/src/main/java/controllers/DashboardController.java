package controllers;

import Entities.User;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.control.Label;
import service.ResetPasswordService;
import service.UserService;
import javafx.scene.control.TextField;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DashboardController implements Initializable {


    @FXML
    private AnchorPane AnWelcome;

    @FXML
    private AnchorPane ExpendObj;

    @FXML
    private AnchorPane MainContainer;

    @FXML
    private Pane btnModifProfil;

    @FXML
    private Pane btnResetPass;

    @FXML
    private Label btn_home;

    @FXML
    private Label btn_settings;

    @FXML
    private Label lbUserNameGlobal;
    @FXML
    private Label lbUserNameGlobal1;

    @FXML
    private AnchorPane pnprofile;

    @FXML
    private TextField tfAdresse;

    @FXML
    private TextField tfConfirmPassword;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfNom;

    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfPrenom;
    private UserService userService;

    @FXML
    void DomainExpantion(MouseEvent event) {

    }

    @FXML
    void Home_display(MouseEvent event) {
        expandContainer(event);
        User authenticatedUser = userService.getAuthenticatedUser();
        userService = UserService.getInstance();
        lbUserNameGlobal1.setText(authenticatedUser.getNom() + " "+ authenticatedUser.getPrenom());


    }

    @FXML
    void Settings_display(MouseEvent event) {
        expandContainer(event);


    }

    @FXML
    void ResetPasswordAction(MouseEvent event)throws Exception {
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
    void changeProfilAction(MouseEvent event) {
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
    @FXML
    void fnLogOut(MouseEvent event) {
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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userService = UserService.getInstance();
        pnprofile.toBack();
        User authenticatedUser = userService.getAuthenticatedUser();
        tfEmail.setText(authenticatedUser.getEmail());
        tfPrenom.setText(authenticatedUser.getPrenom());
        tfNom.setText(authenticatedUser.getNom());
        tfAdresse.setText(authenticatedUser.getZipcode() + ", " + authenticatedUser.getVille() + ", " + authenticatedUser.getAdress());
        lbUserNameGlobal.setText(authenticatedUser.getNom() + " " + authenticatedUser.getPrenom());
        lbUserNameGlobal1.setText(authenticatedUser.getNom() + " " + authenticatedUser.getPrenom());
    }







    private void expandContainer(Event e) {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(ExpendObj.prefWidthProperty(), ExpendObj.getWidth()),
                        new KeyValue(ExpendObj.prefHeightProperty(), ExpendObj.getHeight())
                ),
                new KeyFrame(Duration.millis(500),
                        new KeyValue(ExpendObj.prefWidthProperty(), MainContainer.getWidth()),
                        new KeyValue(ExpendObj.prefHeightProperty(), MainContainer.getHeight())


                )
        );
        if (e.getSource().equals(btn_home)){
            timeline.setOnFinished(event ->{
                collapseContainer();
                pnprofile.setVisible(false);
                AnWelcome.setVisible(true);

            });} else if (e.getSource().equals(btn_settings)){
            timeline.setOnFinished(event ->{
                collapseContainer();
                pnprofile.setVisible(true);
                AnWelcome.setVisible(false);

            });}


        timeline.play();
    }

    private void collapseContainer() {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(ExpendObj.prefWidthProperty(), ExpendObj.getWidth()),
                        new KeyValue(ExpendObj.prefHeightProperty(), ExpendObj.getHeight())
                ),
                new KeyFrame(Duration.millis(500),
                        new KeyValue(ExpendObj.prefWidthProperty(), 81),
                        new KeyValue(ExpendObj.prefHeightProperty(), 74)
                )
        );
        timeline.play();
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

}
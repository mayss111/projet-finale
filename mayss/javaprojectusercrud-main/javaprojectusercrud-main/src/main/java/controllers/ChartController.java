package controllers;

import Utils.ConnectionManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChartController implements Initializable {
    @FXML
    private AnchorPane main_chart;
    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private String query = null;
    @FXML
    private Connection connection = ConnectionManager.getConnection();
    @FXML
    private PreparedStatement preparedStatement = null;
    @FXML
    private ResultSet resultSet = null;
    @FXML
    private String query1 = null;
    @FXML
    private PreparedStatement preparedStatement1 = null;
    @FXML
    private ResultSet resultSet1 = null;
    @FXML
    public void chart(){
        query = "SELECT adress, COUNT(*) AS user_count FROM user GROUP BY adress";
        try {

            XYChart.Series chartData = new XYChart.Series();

            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){

                chartData.getData().add(new XYChart.Data<>(resultSet.getString(1),resultSet.getInt(2)));
            }
            barChart.getData().add(chartData);
        } catch (SQLException ex) {
            Logger.getLogger(ChartController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        chart();
    }

}

package com.example.test2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class HelloController {
    public TextField username;
    public PasswordField password;
    @FXML
    private Label welcomeText;


    @FXML
    protected void onHelloButtonClick() {

         String uname=username.getText();
         String upass=password.getText();

         if(uname.equals("")&&upass.equals("")){
             welcomeText.setText("Please provide username and password");
        }else{
             String jdbcUrl = "jdbc:mysql://localhost:3306/db_test2";
             String dbUser = "root";
             String dbPassword = "";
             try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser,
                     dbPassword)) {
                 // Execute a SQL query to retrieve data from the database
                 String query = "SELECT * FROM tbl_user WHERE `email`= '"+uname+"' AND `password`= '"+upass+"' ";
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query);
                 // Populate the table with data from the database
                 if (resultSet.next()) {
                     try {
                         Parent secondScene = FXMLLoader.load(getClass().getResource("dashboard.fxml"));

                         Stage secondStage = new Stage();
                         secondStage.setTitle("User Scene");
                         secondStage.setScene(new Scene(secondScene));
                         Stage firstSceneStage = (Stage) username.getScene().getWindow();
                         firstSceneStage.close();


                         secondStage.show();
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                 }else {
                     welcomeText.setText("Invalid username or password");
                 }
             } catch (SQLException e) {
                 e.printStackTrace();
             }

         }

    }
}
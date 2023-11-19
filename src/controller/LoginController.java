/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import main.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.MyTimeTable;

public class LoginController implements Initializable {

    private MyTimeTable timeTable;
    @FXML
    private TextField usernameTF;
    @FXML
    private PasswordField passwordTF;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.timeTable = Main.timeTable;
    }

    @FXML
    private void login(ActionEvent event) {
        String un = usernameTF.getText();
        String pass = passwordTF.getText();
        if (timeTable.login(un, pass)) {
            try {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Home.fxml"))));
                stage.setTitle("Home");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            AlertMessage.showAlert(Alert.AlertType.ERROR, "Invalid Username or Password!");
        }
    }

    @FXML
    private void signup(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Signup.fxml"))));
            stage.setTitle("Signup");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

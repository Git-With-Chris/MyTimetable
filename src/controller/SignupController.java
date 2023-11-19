/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import model.MyTimeTable;
import model.User;


public class SignupController implements Initializable {

    private MyTimeTable timeTable;
    @FXML
    private TextField usernameTF;
    @FXML
    private TextField passwordTF;
    @FXML
    private TextField fnameTF;
    @FXML
    private TextField lnameTF;
    @FXML
    private TextField numberTF;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.timeTable = Main.timeTable;
    }

    @FXML
    private void close(ActionEvent event) {
        openLoginPage(event);
    }

    @FXML
    private void signup(ActionEvent event) {
        String fname = fnameTF.getText();
        String lname = lnameTF.getText();
        String number = numberTF.getText();
        String username = usernameTF.getText();
        String password = passwordTF.getText();
        if (fname.isEmpty() || lname.isEmpty() || number.isEmpty()
                || username.isEmpty() || password.isEmpty()) {
            AlertMessage.showAlert(Alert.AlertType.ERROR, "Empty Fields!");
        } else {
            User user = new User(fname, lname, number, username, password);
            if (timeTable.signup(user)) {
                AlertMessage.showAlert(Alert.AlertType.INFORMATION, "Successful!");
                openLoginPage(event);
            } else {
                AlertMessage.showAlert(Alert.AlertType.ERROR, "Username Already Taken!");
            }
        }
    }

    private void openLoginPage(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Login.fxml"))));
            stage.setTitle("Login");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

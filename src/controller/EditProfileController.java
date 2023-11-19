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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import model.MyTimeTable;
import model.User;


public class EditProfileController implements Initializable {

    private MyTimeTable timeTable;

    @FXML
    private Label fnameLBL;
    @FXML
    private Label passwordLBL;
    @FXML
    private TextField usernameTF;
    @FXML
    private TextField passwordTF;
    @FXML
    private Label usernameLBL;
    @FXML
    private TextField fnameTF;
    @FXML
    private Label lnameLBL;
    @FXML
    private Label numberLBL;
    @FXML
    private TextField lnameTF;
    @FXML
    private TextField numberTF;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        timeTable = Main.timeTable;

        fnameTF.setText(timeTable.getCurrentUser().getFname());
        lnameTF.setText(timeTable.getCurrentUser().getLname());
        numberTF.setText(timeTable.getCurrentUser().getNumber());
        usernameTF.setText(timeTable.getCurrentUser().getUsername());
        passwordTF.setText(timeTable.getCurrentUser().getPassword());

        applyStyle();
    }

    @FXML
    private void close(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Home.fxml"))));
            stage.setTitle("Home");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void update(ActionEvent event) {
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
            if (timeTable.updateUser(user)) {
                AlertMessage.showAlert(Alert.AlertType.INFORMATION, "Successful!");
            } else {
                AlertMessage.showAlert(Alert.AlertType.ERROR, "Username Already Taken!");
            }
        }
    }

    private void applyStyle() {
        fnameLBL.setStyle(timeTable.getFont());
        lnameLBL.setStyle(timeTable.getFont());
        numberLBL.setStyle(timeTable.getFont());
        usernameLBL.setStyle(timeTable.getFont());
        passwordLBL.setStyle(timeTable.getFont());
    }

}

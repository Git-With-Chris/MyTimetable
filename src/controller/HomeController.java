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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import model.MyTimeTable;


public class HomeController implements Initializable {

    private MyTimeTable timeTable;

    @FXML
    private Button editProfileButton;
    @FXML
    private Label fnameLBL;
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
    @FXML
    private Button courseButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Label colorLBL;
    @FXML
    private TextField sizeTF;
    @FXML
    private Label styleLBL;
    @FXML
    private ComboBox<String> styleCB;
    @FXML
    private TextField colorTF;
    @FXML
    private Label sizeLBL;
    @FXML
    private Label typeLBL;
    @FXML
    private TextField typeTF;
    @FXML
    private Button applyButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        timeTable = Main.timeTable;
        styleCB.getItems().addAll("Bold", "Italic");

        sizeTF.setText(timeTable.getSize() + "");
        styleCB.getSelectionModel().select(timeTable.getStyle());
        colorTF.setText(timeTable.getColor());
        typeTF.setText(timeTable.getType());

        fnameTF.setText(timeTable.getCurrentUser().getFname());
        lnameTF.setText(timeTable.getCurrentUser().getLname());
        numberTF.setText(timeTable.getCurrentUser().getNumber());

        applyStyle();
    }

    @FXML
    private void editProfile(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/EditProfile.fxml"))));
            stage.setTitle("Edit Profile");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void course(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Course.fxml"))));
            stage.setTitle("Course");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logout(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Login.fxml"))));
            stage.setTitle("Login");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void apply(ActionEvent event) {
        int size = timeTable.getSize();
        try {
            size = Integer.parseInt(sizeTF.getText());
        } catch (Exception e) {
        }
        timeTable.setFont(size, styleCB.getSelectionModel().getSelectedItem(),
                colorTF.getText(), typeTF.getText());
        applyStyle();

    }

    private void applyStyle() {
        fnameLBL.setStyle(timeTable.getFont());
        lnameLBL.setStyle(timeTable.getFont());
        numberLBL.setStyle(timeTable.getFont());
        sizeLBL.setStyle(timeTable.getFont());
        styleLBL.setStyle(timeTable.getFont());
        colorLBL.setStyle(timeTable.getFont());
        typeLBL.setStyle(timeTable.getFont());
    }

}

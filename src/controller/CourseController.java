/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.Main;
import model.Course;
import model.MyTimeTable;

public class CourseController implements Initializable {

    private MyTimeTable timeTable;
    @FXML
    private TableView<Course> table;
    @FXML
    private TableColumn<Course, String> nameCol;
    @FXML
    private TableColumn<Course, String> capacityCol;
    @FXML
    private TableColumn<Course, String> yearCol;
    @FXML
    private TableColumn<Course, String> deliveryCol;
    @FXML
    private TableColumn<Course, String> dayCol;
    @FXML
    private TableColumn<Course, String> timeCol;
    @FXML
    private TableColumn<Course, String> durationCol;
    @FXML
    private TableColumn<Course, String> availableCol;
    @FXML
    private TextField searchTF;
    @FXML
    private Label nameLBL;
    @FXML
    private ListView<Course> enrolledCourseLV;
    @FXML
    private Label enrolledCoursesLBL;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        timeTable = Main.timeTable;

        loadCourses("");

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        capacityCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
        deliveryCol.setCellValueFactory(new PropertyValueFactory<>("delivery"));
        dayCol.setCellValueFactory(new PropertyValueFactory<>("day"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        availableCol.setCellValueFactory(new PropertyValueFactory<>("available"));

        applyStyle();
    }

    @FXML
    private void search(ActionEvent event) {
        loadCourses(searchTF.getText());
    }

    @FXML
    private void enroll(ActionEvent event) {
        Course selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            AlertMessage.showAlert(Alert.AlertType.WARNING, "Select a course to enroll!");
        } else if (selectedItem.getAvailable().equals("No")) {
            AlertMessage.showAlert(Alert.AlertType.WARNING, "This course does not have enough capacity!");
        } else if (timeTable.isEnrolled(selectedItem)) {
            AlertMessage.showAlert(Alert.AlertType.WARNING, "You have already enrolled into this course!");
        } else {
            timeTable.enroll(selectedItem);
            AlertMessage.showAlert(Alert.AlertType.INFORMATION, "Successful!");
            searchTF.setText("");
            loadCourses(searchTF.getText());
        }
    }

    @FXML
    private void withdraw(ActionEvent event) {
        Course selectedItem = enrolledCourseLV.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            AlertMessage.showAlert(Alert.AlertType.WARNING, "Select a course to withdraw!");
        } else {
            timeTable.withdraw(selectedItem);
            AlertMessage.showAlert(Alert.AlertType.INFORMATION, "Successful!");
            searchTF.setText("");
            loadCourses(searchTF.getText());
        }
    }

    @FXML
    private void timetable(ActionEvent event) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/TimeTable.fxml"))));
            stage.setTitle("TimeTable");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void export(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("File name:");
        dialog.showAndWait();
        String name = dialog.getEditor().getText();
        timeTable.export(name);
        AlertMessage.showAlert(Alert.AlertType.INFORMATION, "Successful!");
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

    private void loadCourses(String filter) {
        table.getItems().clear();
        table.getItems().addAll(timeTable.getCourses(filter));
        enrolledCourseLV.getItems().clear();
        enrolledCourseLV.getItems().addAll(timeTable.getEnrolledCourses());
    }

    private void applyStyle() {
        nameLBL.setStyle(timeTable.getFont());
        enrolledCoursesLBL.setStyle(timeTable.getFont());
    }

}

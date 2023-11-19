/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import main.Main;
import model.Course;


public class TimeTableController implements Initializable {

    private ArrayList<Label> courses;

    @FXML
    private Label course1;
    @FXML
    private Label course4;
    @FXML
    private Label course7;
    @FXML
    private Label course2;
    @FXML
    private Label course5;
    @FXML
    private Label course6;
    @FXML
    private Label course3;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        courses = new ArrayList<>(Arrays.asList(course1, course2, course3, course4, course5, course6, course7));

        try {
            ArrayList<Course> enrolledCourses = Main.timeTable.getEnrolledCourses();
            for (Course enrolledCourse : enrolledCourses) {
                for (Label label : courses) {
                    if (label.getText().equals(enrolledCourse.getName())) {
                        label.setVisible(true);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        applyStyle();
    }

    private void applyStyle() {
        for (Label course : courses) {
            course.setStyle(Main.timeTable.getFont());
        }
    }

}

package controller;


import javafx.scene.control.Alert;

public class AlertMessage {

    public static void showAlert(Alert.AlertType type, String headerText) {
        Alert alert = new Alert(type);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
}

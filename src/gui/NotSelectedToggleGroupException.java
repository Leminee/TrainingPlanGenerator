package gui;

import javafx.scene.control.Alert;

public class NotSelectedToggleGroupException extends Exception{

    //Exception wird geworfen, wenn beim Erstellen eines Trainingsplans nicht alle Radiobuttons ausgewählt wurden
    public NotSelectedToggleGroupException() {

        Alert alertGroupTwo = new Alert(Alert.AlertType.ERROR);
        alertGroupTwo.setTitle("");
        alertGroupTwo.setContentText("Versuche erneut!");
        alertGroupTwo.showAndWait();

    }
}

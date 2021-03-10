package gui;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangeEmailController implements Validator {

    @FXML
    private TextField oldEmail;

    @FXML
    private TextField newEmail;

    @FXML
    private TextField password;


    @FXML
    public void editButton(ActionEvent event) {
        try {
            ConnectionToDB db = new ConnectionToDB();
            db.initialize();
            if (emailIsValid(newEmail.getText())) {
                String sqlCommand = "UPDATE users SET e_mail=? WHERE e_mail=? AND password=?";
                PreparedStatement ps = db.connection.prepareStatement(sqlCommand);
                ps.setString(1, newEmail.getText());
                ps.setString(2, oldEmail.getText());
                ps.setString(3, password.getText());
                int updatedRow = ps.executeUpdate();
                if( updatedRow!= 0) {

                    Alert success = new Alert(Alert.AlertType.INFORMATION);
                    success.setTitle("");
                    success.setContentText("E-mail wurde erfolgreich bearbeitet!");
                    success.showAndWait();

                    Node source = (Node) event.getSource();
                    Stage oldStage = (Stage) source.getScene().getWindow();
                    oldStage.close();
                }

                else {

                    Alert failure = new Alert(Alert.AlertType.WARNING);
                    failure.setTitle("");
                    failure.setContentText("falsche Eingaben!");
                    failure.showAndWait();

                }

            }
            else {

                Alert failure = new Alert(Alert.AlertType.WARNING);
                failure.setTitle("");
                failure.setContentText("Die E-mail und/oder Passwort sind nicht valid!");
                failure.showAndWait();
            }

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }

    @FXML
    public void cancelButton(ActionEvent event) {
        try {
            Node source = (Node) event.getSource();
            Stage oldStage = (Stage) source.getScene().getWindow();
            oldStage.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
package gui;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangeUsernameController implements Validator {

    @FXML
    private TextField oldUsername;

    @FXML
    private TextField newUsername;

    @FXML
    private TextField eMail;



    @FXML
    public void editButton(ActionEvent event) {
        try {
            ConnectionToDB db = new ConnectionToDB();
            db.initialize();

            if (usernameIsValid(oldUsername.getText()) &&usernameIsValid(newUsername.getText()) && emailIsValid(eMail.getText())) {
                String sqlCommand = "UPDATE users SET username=? WHERE username=? AND e_mail=?";
                PreparedStatement ps = db.connection.prepareStatement(sqlCommand);
                ps.setString(1, newUsername.getText());
                ps.setString(2, oldUsername.getText());
                ps.setString(3, eMail.getText());

                int updatedRow = ps.executeUpdate();
                if( updatedRow!= 0) {

                    Alert success = new Alert(Alert.AlertType.INFORMATION);
                    success.setTitle("");
                    success.setContentText("Benutzername wurde erfolgreich bearbeitet!");
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
                    failure.setContentText("Der Benutzername und/oder E-Mail sind nicht valid!");
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

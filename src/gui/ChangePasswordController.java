package gui;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangePasswordController implements Validator {

    @FXML
    private TextField oldPassword;

    @FXML
    private TextField newPassword;

    @FXML
    private TextField eMail;



    @FXML
    public void editButton(ActionEvent event) {
        try {
           ConnectionToDB db = new ConnectionToDB();
            db.initialize();

            if (passwordIsValid(newPassword.getText())) {
                String sqlCommand = "UPDATE users SET password=? WHERE password=? AND e_mail=?";
                PreparedStatement ps = db.connection.prepareStatement(sqlCommand);
                ps.setString(1, newPassword.getText());
                ps.setString(2, oldPassword.getText());
                ps.setString(3, eMail.getText());
                ps.executeUpdate();
                System.out.println("Passwort wurde erfolgreich geaendert!");
                db.connection.close();


                Node source = (Node) event.getSource();
                Stage oldStage = (Stage) source.getScene().getWindow();
                oldStage.close();
            }
            else {
                JOptionPane.showMessageDialog(null, "Passwort wurde nicht richtig eingetragen!");
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

package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class OptionsController {

    public static void changeUsername() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(OptionsController.class.getResource("ChangeUsernameController.fxml"));
            Parent root =  fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Benutzername ändern");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            stage.centerOnScreen();
        }catch (IOException iOException) {
            System.out.println(iOException.getMessage());
        }
    }

    public static void changePassword() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(OptionsController.class.getResource("ChangePasswordController.fxml"));
            Parent root =  fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Passwort ändern");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            stage.centerOnScreen();
        }catch (IOException iOException) {
            System.out.println(iOException.getMessage());
        }
    }
    public static void changeEmail() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(OptionsController.class.getResource("ChangeEmailController.fxml"));
            Parent root =  fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("E-Mail ändern");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            stage.centerOnScreen();
        }catch (IOException iOException) {
            System.out.println(iOException.getMessage());
        }
    }
}

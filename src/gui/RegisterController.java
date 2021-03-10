package gui;

import java.io.IOException;
import java.sql.*;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;



public class RegisterController implements Validator {
		@FXML
	    private TextField username;
		@FXML
	    private TextField firstname;
	    @FXML
	    private TextField name;
	    @FXML
	    private TextField email;
	    @FXML
	    private PasswordField password;

	    @FXML
	    private void createNewAccount(ActionEvent sendEvent) throws SQLException {

			boolean isAccepted = true;

			//Username Validierung
			if (!usernameIsValid(username.getText())) {
				isAccepted = false;

				Alert registerNotification = new Alert(Alert.AlertType.WARNING);
				registerNotification.setTitle("");
				registerNotification.setContentText("Der Benutzername ist nicht valid");
				registerNotification.showAndWait();
			}

			//Passwort Validierung
			if (!passwordIsValid(password.getText())) {
				isAccepted = false;

				Alert registerNotification = new Alert(Alert.AlertType.WARNING);
				registerNotification.setTitle("");
				registerNotification.setContentText("Passwort ist nicht valid");
				registerNotification.showAndWait();

			}
			//E-Mail Validierung

			if (!emailIsValid(email.getText())) {
				isAccepted = false;

				Alert registerNotification = new Alert(Alert.AlertType.WARNING);
				registerNotification.setTitle("");
				registerNotification.setContentText("E-Mail-Adresse ist nicht valid");
				registerNotification.showAndWait();
			}
			//Vorname Validierung
			if (!firstNameIsValid(firstname.getText())) {
				isAccepted = false;

				Alert registerNotification = new Alert(Alert.AlertType.WARNING);
				registerNotification.setTitle("");
				registerNotification.setContentText("Vorname ist zu kurz oder enthält nicht erlaubte Zeichen");
				registerNotification.showAndWait();
			}

			//Name Validierung
			if (!nameIsValid(name.getText())) {
            	isAccepted = false;

				Alert registerNotification = new Alert(Alert.AlertType.WARNING);
				registerNotification.setTitle("");
				registerNotification.setContentText("Name ist zu kurz oder enthält nicht erlaubte Zeichen");
				registerNotification.showAndWait();
			}
			if (isAccepted) {
				ConnectionToDB db = new ConnectionToDB();
				db.initialize();
				String insertAccountQuery = "INSERT INTO users (id_user, username, password, first_name, last_name, e_mail) VALUES (NULL, ?, ?, ?, ?, ?);";
				PreparedStatement userDataInputPStatement = db.connection.prepareStatement(insertAccountQuery);
				userDataInputPStatement.setString(1, username.getText());
				userDataInputPStatement.setString(2, password.getText());
				userDataInputPStatement.setString(3, firstname.getText());
				userDataInputPStatement.setString(4, name.getText());
				userDataInputPStatement.setString(5, email.getText());

				String verifyIfUsernameAlreadyExistsQuery = "SELECT username FROM users WHERE username= ? ";
				PreparedStatement usernameInputPStatement = db.connection.prepareStatement(verifyIfUsernameAlreadyExistsQuery);
				usernameInputPStatement.setString(1,username.getText());
				ResultSet rS = usernameInputPStatement.executeQuery();
				

				if (rS.next()) {
					Alert registerSuccess = new Alert(Alert.AlertType.ERROR);
					registerSuccess.setTitle("");
					registerSuccess.setContentText("Der Benutzername " + username.getText() + " ist bereits vergeben!");
					registerSuccess.showAndWait();
				} else {

					String verifyIfEmailAlreadyExistsQuery = "SELECT e_mail FROM users WHERE e_mail= ? ";
					PreparedStatement emailInputPStatement = db.connection.prepareStatement(verifyIfEmailAlreadyExistsQuery);
					emailInputPStatement.setString(1,email.getText());
					ResultSet rSet =emailInputPStatement.executeQuery();

					if(rSet.next()) {
						Alert registerSuccess = new Alert(Alert.AlertType.ERROR);
						registerSuccess.setTitle("");
						registerSuccess.setContentText("Mit der E-Mail-Adresse " + email.getText() + " ist bereits ein Benutzer registriert!");
						registerSuccess.showAndWait();

					}

					else {
						userDataInputPStatement.executeUpdate();
						emailInputPStatement.executeQuery();

						Node source = (Node) sendEvent.getSource();
						Stage oldStage = (Stage) source.getScene().getWindow();
						oldStage.close();


						try {
							FXMLLoader loadLogin = new FXMLLoader(getClass().getResource("Login.fxml"));
							Parent root = loadLogin.load();
							Stage loginStage = new Stage();
							loginStage.setTitle("Login".toUpperCase());
							loginStage.setScene(new Scene(root));
							loginStage.setResizable(false);
							loginStage.centerOnScreen();
							loginStage.show();

							AlertNotification registerSuccess = new AlertNotification();
							registerSuccess.setText(new Label("          Account wurde erfolgreich erstellt!"));
							Stage stageSuccess = new Stage();
							registerSuccess.start(stageSuccess);
							PauseTransition delay = new PauseTransition(Duration.seconds(5));
							delay.setOnFinished(event -> stageSuccess.close());
							delay.play();

						} catch (IOException iOException) {
							System.out.println(iOException.getMessage());
						}
					}
				}
			}
		}

	    @FXML
	    public void cancel() {
	    	System.exit(0);
	    }
	    

}


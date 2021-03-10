package gui;

import java.io.IOException;
import java.sql.*;
import java.sql.ResultSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	@FXML
	private TextField tfUsername;
	@FXML
	private PasswordField pfPassword;
	@FXML
	public Button btLogin;
	@FXML
	public Button btRegister;
	private static int userId;

	@FXML
	private void handleButtonRegisterAction(ActionEvent registerEvent) {
		Node source = (Node) registerEvent.getSource();
		Stage oldStage = (Stage) source.getScene().getWindow();
		oldStage.close();
		try {
			FXMLLoader loadRegister = new FXMLLoader(getClass().getResource("Register.fxml"));
			Parent root =  loadRegister.load();
			Stage registerStage = new Stage();
			registerStage.setTitle("Registrieren".toUpperCase());
			registerStage.setScene(new Scene(root));
			registerStage.setResizable(false);
			registerStage.show();
			registerStage.centerOnScreen();
		}catch (IOException iOException) {

			System.out.println(iOException.getMessage());
		}
	}

	@FXML
	private void handleButtonLoginAction(ActionEvent loginEvent) {

		try {
			ConnectionToDB db = new ConnectionToDB();
			db.initialize();
			String loginAuthenticationQuery = "SELECT username FROM users WHERE username = ? AND password = ?";
			PreparedStatement loginInputPStatement = db.connection.prepareStatement(loginAuthenticationQuery);

			loginInputPStatement.setString(1,tfUsername.getText());
			loginInputPStatement.setString(2, pfPassword.getText());

			ResultSet resultSet = loginInputPStatement.executeQuery();

			if(resultSet.next()) {
				Node source = (Node) loginEvent.getSource();
				Stage oldStage = (Stage) source.getScene().getWindow();
				oldStage.close();

				String getUserIdQuery = "SELECT users.id_user FROM users WHERE username = ?";
				PreparedStatement usernameInputPStatement = db.connection.prepareStatement(getUserIdQuery);
				usernameInputPStatement.setString(1, tfUsername.getText());
				ResultSet rS = usernameInputPStatement.executeQuery();

				if (rS.next()) {
					userId = (int) rS.getLong(1);

					String verifyIfPlanExistQuery = "SELECT username FROM users INNER JOIN training_plans  ON training_plans.id_user = users.id_user WHERE users.id_user = ?";
					PreparedStatement userIdInputStatement = db.connection.prepareStatement(verifyIfPlanExistQuery);
					userIdInputStatement.setInt(1, userId);
					ResultSet rSThree = userIdInputStatement.executeQuery();

					if (rSThree.next()) {

						Stage userProfileStage = new Stage();
						UserProfileWithTrainingPlanUI tPAE = new UserProfileWithTrainingPlanUI();
						tPAE.start(userProfileStage);
					} else {
						Stage userProfileStage = new Stage();
						UserProfileUI userProfileUI = new UserProfileUI();
						userProfileUI.start(userProfileStage);
					}
				}

			} else {
				Alert loginNotSuccess = new Alert(Alert.AlertType.ERROR);
				loginNotSuccess.setTitle("");
				loginNotSuccess.setContentText("Login nicht erfolgreich!");
				loginNotSuccess.showAndWait();

			}
		}catch(Exception e) {
			System.out.println(e.getMessage());

		}
		
	}


	public int getUserId() {
		return userId;
	}

}

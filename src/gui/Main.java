package gui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	@Override
	public void start(Stage homeStage) {
		try {
			homeStage.setTitle("Login");
			BorderPane root = FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene loginScene = new Scene(root,350,200);
			loginScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			homeStage.setResizable(false);
			homeStage.setScene(loginScene);
			homeStage.show();
			homeStage.centerOnScreen();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
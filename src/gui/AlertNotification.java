package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AlertNotification extends Application {
    Label textLabel;

    @Override
    public void start(Stage alertNotificationStage) throws FileNotFoundException {

        alertNotificationStage.setTitle("");

        //Ein Bild auf das Fenster setzen
        final FileInputStream firstInputStream = new FileInputStream("images/succes.png");
        javafx.scene.image.Image firstImage = new Image(firstInputStream);
        final ImageView firstImageView = new ImageView(firstImage);
        firstImageView.setX(210);
        firstImageView.setY(55);

        //Ein Button auf das Fenster setzen
        final Button okButton = new Button("OK");
        okButton.setFont(Font.font("Akkurat", 20));
        okButton.setPadding(new Insets(5,30,5,30));
        okButton.setTextFill(Color.web("#02075d"));
        okButton.setStyle("-fx-border-color:#02075d; -fx-background-color: #945151;");
        okButton.setLayoutX(199);
        okButton.setLayoutY(125);
        okButton.setOnMouseMoved(e-> okButton.setStyle("-fx-border-color:#02075d; -fx-background-color: #7d4646;"));
        okButton.setOnMouseExited(e-> okButton.setStyle("-fx-border-color:#02075d; -fx-background-color: #945151;"));

        //Fenster schließen, wenn auf "ok" geklickt wird
        okButton.setOnAction(e-> alertNotificationStage.close());

        final Group root = new Group();

        //Elemente auf Scene setzen
        root.getChildren().addAll(setText(textLabel),okButton, firstImageView);
        final Scene alertNotificationScene = new Scene(root, 500, 170);

        alertNotificationStage.setResizable(false);
        alertNotificationStage.setScene(alertNotificationScene);
        alertNotificationStage.show();
        alertNotificationStage.centerOnScreen();
    }


    //Ein beliebiger Text auf das Fenster setzen
    public Label setText(Label textLabel) {
        this.textLabel = textLabel;
        textLabel = new Label(textLabel.getText());
        textLabel.setFont(Font.font("Arial", 20));
        textLabel.setTextFill(Color.web("Green"));
        textLabel.setLayoutX(50);
        textLabel.setLayoutY(20);
        return textLabel;

    }
}
package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Options extends Application {

    @Override
    public void start(Stage optionsStage) throws FileNotFoundException {

        optionsStage.setTitle("Optionen".toUpperCase());

        VBox verticalBox = new VBox(15);
        verticalBox.setPadding(new Insets(150, 60, 60, 60));
        verticalBox.setAlignment(Pos.CENTER);


        final Label label = new Label("Deine Profil-Daten bearbeiten");
        label.setFont(Font.font("Pacifico", 29));
        label.setTextFill(Color.web("E66A6A"));
        label.setPadding(new Insets(-100, 10, 50, 10));

        final FileInputStream InputStreamFive = new FileInputStream("images/Username.png");
        Image imageFive = new Image(InputStreamFive);
        ImageView imageViewFive = new ImageView(imageFive);

        final FileInputStream inputStreamSix = new FileInputStream("images/Passwort.png");
        Image imageSix = new Image(inputStreamSix);
        ImageView imageViewSix = new ImageView(imageSix);

        final FileInputStream inputStreamSeven = new FileInputStream("images/E-mail.png");
        Image imageSeven = new Image(inputStreamSeven);
        ImageView imageViewSeven = new ImageView(imageSeven);

        final FileInputStream inputStreamEight = new FileInputStream("images/close.png");
        Image imageEight = new Image(inputStreamEight);
        ImageView imageViewEight = new ImageView(imageEight);

        final Button changeUsername = new Button("Username ändern        ", imageViewFive);
        changeUsername.setFont(Font.font("Akkurat", 18));
        changeUsername.setOnAction(e-> OptionsController.changeUsername());

        final Button changePassword = new Button("Passwort ändern            ", imageViewSix);
        changePassword.setFont(Font.font("Akkurat", 18));
        changePassword.setOnAction(e -> OptionsController.changePassword());

        final Button changeEmail = new Button("E-Mail-Adresse ändern", imageViewSeven);
        changeEmail.setFont(Font.font("Akkurat", 18));
        changeEmail.setOnAction(e -> OptionsController.changeEmail());

        final Button closeWindow = new Button("", imageViewEight);
        closeWindow.setFont(Font.font("Akkurat", 18));
        closeWindow.setOnAction(e -> optionsStage.close());

        verticalBox.getChildren().addAll(label, changeUsername, changePassword, changeEmail, closeWindow);
        Scene optionsScene = new Scene(verticalBox);

        optionsStage.setResizable(false);
        optionsStage.setScene(optionsScene);
        optionsStage.show();
        optionsStage.centerOnScreen();

    }

}


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
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.FileInputStream;

public class UserProfileWithTrainingPlanUI extends Application {

    public static Stage userProfileStage;
    @Override
    public void start(Stage userProfileStage) throws Exception {

        UserProfileWithTrainingPlanUI.userProfileStage = userProfileStage;

        userProfileStage.setTitle("Benutzerprofil".toUpperCase());

        VBox vBox = new VBox(20);
        vBox.setPadding(new Insets(160, 60, 30, 60));
        vBox.setAlignment(Pos.CENTER);

        final Label welcome = new Label("Willkommen auf Deinem Profil");
        welcome.setFont(Font.font("Pacifico", 29));
        welcome.setTextFill(Color.web("E66A6A"));
        welcome.setPadding(new Insets(-90, 10, 50, 10));

        final FileInputStream firstInputStream = new FileInputStream("images/Trainingsplan_erstellen.png");
        Image firstImage = new Image(firstInputStream);
        ImageView firstImageView = new ImageView(firstImage);

        final FileInputStream inputStreamTwo = new FileInputStream("images/AlleUebungen.png");
        Image imageTwo = new Image(inputStreamTwo);
        ImageView imageViewTwo = new ImageView(imageTwo);

        final FileInputStream inputStreamThree = new FileInputStream("images/config.png");
        Image imageThree = new Image(inputStreamThree);
        ImageView imageViewThree = new ImageView(imageThree);

        final FileInputStream inputStreamFour = new FileInputStream("images/Ausloggen.png");
        Image imageFour = new Image(inputStreamFour);
        ImageView imageViewFour = new ImageView(imageFour);

        final Button showTrainingPlan = new Button("Trainingsplan anzeigen", firstImageView);
        showTrainingPlan.setFont(Font.font("Akkurat", 20));
        showTrainingPlan.setOnAction(e-> UserProfileController.loadTrainingPlan());

        final Button allExercises = new Button("Alle Übungen anzeigen", imageViewTwo);
        allExercises.setFont(Font.font("Akkurat", 20));
        AllExcercisesController aec = new AllExcercisesController();
        allExercises.setOnAction(e -> aec.allExercises());

        final Button options = new Button("   Optionen                         ", imageViewThree);
        options.setFont(Font.font("Akkurat", 20));
        options.setOnAction(event -> {
            Options oP = new Options();
            Stage optionsStage = new Stage();
            optionsStage.initModality(Modality.WINDOW_MODAL);
            optionsStage.initOwner(userProfileStage);
            try {
                oP.start(optionsStage);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        final Button logout = new Button("   Ausloggen                      ", imageViewFour);
        logout.setOnAction(event -> { userProfileStage.close(); UserProfileController.logout();});
        logout.setFont(Font.font("Akkurat", 20));

        vBox.getChildren().addAll(welcome, showTrainingPlan, allExercises, options, logout);

        Scene userProfileScene = new Scene(vBox);

        userProfileStage.setResizable(false);
        userProfileStage.setScene(userProfileScene);
        userProfileStage.show();
        userProfileStage.centerOnScreen();


    }

}



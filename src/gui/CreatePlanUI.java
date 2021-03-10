package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class CreatePlanUI extends Application {

    static RadioButton firstRadioButton;
    static RadioButton radioButtonTwo;
    static RadioButton radioButtonThree;
    static RadioButton radioButtonFour;
    static RadioButton radioButtonFive;
    static RadioButton radioButtonSix;
    static RadioButton radioButtonSeven;
    static RadioButton radioButtonEight;
    static RadioButton radioButtonNine;
    static RadioButton radioButtonTen;
    static RadioButton radioButtonEleven;
    static RadioButton radioButtonTwelve;
    static RadioButton radioButtonThirteen;
    static RadioButton radioButtonFourteen;
    static RadioButton radioButtonFifteen;
    static Stage createTrainingPlanStage;



    @Override
    public void start(Stage createTrainingPlanStage) {
        CreatePlanUI.createTrainingPlanStage = createTrainingPlanStage;

        createTrainingPlanStage.setTitle("Erstellungsprozess".toUpperCase());

        VBox vBox = new VBox(17);
        vBox.setPadding(new Insets(15, 30, 15, 30));

        final Label firstText = new Label("Wähle dein Trainingsziel aus");
        firstText.setStyle("-fx-font: 24 arial;");
        ToggleGroup groupOne = new ToggleGroup();
        firstRadioButton = new RadioButton("Muskelaufbau");
        radioButtonTwo = new RadioButton("Ausdauer");
        firstRadioButton.setToggleGroup(groupOne);
        radioButtonTwo.setToggleGroup(groupOne);

        final Label textTwo = new Label("Wähle dein Trainingssplit aus");
        textTwo.setStyle("-fx-font: 24 arial;");
        ToggleGroup groupTwo = new ToggleGroup();
        radioButtonThree = new RadioButton("Ganzkörper");
        radioButtonFour = new RadioButton("Ober- und Unterkörper");
        radioButtonFive = new RadioButton("Push&Pull&Legs");
        radioButtonThree.setToggleGroup(groupTwo);
        radioButtonFive.setToggleGroup(groupTwo);
        radioButtonFour.setToggleGroup(groupTwo);

        final Label textThree = new Label("Wähle die Dauer des Trainings aus");
        textThree.setStyle("-fx-font: 24 arial;");
        ToggleGroup groupThree = new ToggleGroup();
        radioButtonSix = new RadioButton("30");
        radioButtonSeven = new RadioButton("60");
        radioButtonEight = new RadioButton("90");
        radioButtonSix.setToggleGroup(groupThree);
        radioButtonSeven.setToggleGroup(groupThree);
        radioButtonEight.setToggleGroup(groupThree);

        final Label textFour = new Label("Wähle die Anzahl der Trainingstage pro Woche aus");
        final ListView <RadioButton> choiceList = new ListView<>();
        textFour.setStyle("-fx-font: 24 arial;");
        ToggleGroup groupFour = new ToggleGroup();
        radioButtonNine = new RadioButton("1");
        radioButtonTen = new RadioButton("2");
        radioButtonEleven = new RadioButton("3");
        radioButtonTwelve = new RadioButton("4");
        radioButtonThirteen = new RadioButton("5");
        radioButtonFourteen = new RadioButton("6");
        radioButtonFifteen = new RadioButton("7");
        radioButtonNine.setToggleGroup(groupFour);
        radioButtonTen.setToggleGroup(groupFour);
        radioButtonEleven.setToggleGroup(groupFour);
        radioButtonTwelve.setToggleGroup(groupFour);
        radioButtonThirteen.setToggleGroup(groupFour);
        radioButtonFourteen.setToggleGroup(groupFour);
        radioButtonFifteen.setToggleGroup(groupFour);
        choiceList.getItems().addAll(radioButtonNine, radioButtonTen,radioButtonEleven,
                radioButtonTwelve, radioButtonThirteen, radioButtonFourteen, radioButtonFifteen);


        final Label recommendation = new Label("Wir empfehlen mindestens einen Pausetag zwischen jedem Trainingstag für die Muskelregeneration");
        recommendation.setStyle("-fx-font: 12 arial;");
        recommendation.setTextFill(Color.GRAY);

        final Button submitButton = new Button("Trainingsplan erstellen");
        submitButton.setStyle("-fx-font: 24 Akkurat;");
        submitButton.setPadding(new Insets(5));
        submitButton.setOnAction(e-> CreatePlanUIController.submit());

        vBox.getChildren().addAll(firstText,firstRadioButton,radioButtonTwo, textTwo, radioButtonThree,
                radioButtonFour, radioButtonFive, textThree, radioButtonSix, radioButtonSeven, radioButtonEight, textFour,
                choiceList, recommendation, submitButton);

        Scene createTrainingPlanScene = new Scene(vBox, 750, 750);

        createTrainingPlanStage.setScene(createTrainingPlanScene);
        createTrainingPlanStage.setResizable(false);
        createTrainingPlanStage.show();
        createTrainingPlanStage.centerOnScreen();

    }


}

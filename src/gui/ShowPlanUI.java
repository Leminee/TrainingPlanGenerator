package gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowPlanUI extends Application {
    TableView<ShowPlanController> trainingPlanDataTable;

    @Override
    public void start(Stage trainingPlanViewStage) throws FileNotFoundException, SQLException {

        trainingPlanViewStage.setTitle("Trainingsplan".toUpperCase());

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);


        final FileInputStream InputStreamOne = new FileInputStream("images/AlleUebungen.png");
        Image ImageFive = new Image(InputStreamOne);
        ImageView imageViewFive = new ImageView(ImageFive);

        final Button allExercises = new Button("", imageViewFive);
        allExercises.setFont(Font.font("Akkurat", 1));
        AllExcercisesController aec = new AllExcercisesController();
        allExercises.setOnAction(e -> aec.allExercises());


        final FileInputStream InputStreamTwo = new FileInputStream("images/goBack.png");
        Image ImageSix = new Image(InputStreamTwo);
        ImageView imageViewTwo = new ImageView(ImageSix);

        final Button goBack = new Button("", imageViewTwo);
        goBack.setFont(Font.font("Akkurat", 1));
        UserProfileWithTrainingPlanUI uPWTP = new UserProfileWithTrainingPlanUI();
        Stage userProfileStage = new Stage();
        goBack.setOnAction(e -> {
            try {
                trainingPlanViewStage.close();
                uPWTP.start(userProfileStage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        TableColumn<ShowPlanController, String> muscleGroupColumn = new TableColumn<>("Muskelgruppe");
        muscleGroupColumn.setMinWidth(145);
        muscleGroupColumn.setStyle("-fx-font-size: 17px; -fx-border-color:#02075d;");
        muscleGroupColumn.setCellValueFactory(new PropertyValueFactory<>("muscleGroup"));


        TableColumn<ShowPlanController, String> exerciseColumn = new TableColumn<>("Übung");
        exerciseColumn.setMinWidth(235);
        exerciseColumn.setStyle("-fx-font-size: 17px; -fx-border-color:#02075d;");
        exerciseColumn.setCellValueFactory(new PropertyValueFactory<>("exercise"));

        TableColumn<ShowPlanController, Integer> numberSetColumn = new TableColumn<>("Anzahl Sätze");
        numberSetColumn.setMinWidth(110);
        numberSetColumn.setStyle("-fx-font-size: 17px; -fx-border-color:#02075d; ");
        numberSetColumn.setCellValueFactory(new PropertyValueFactory<>("numberSet"));

        TableColumn<ShowPlanController, Integer> numberRepColumn = new TableColumn<>("Anzahl Wiederholungen");
        numberRepColumn.setMinWidth(179);
        numberRepColumn.setStyle("-fx-font-size: 17px; -fx-border-color:#02075d;");
        numberRepColumn.setCellValueFactory(new PropertyValueFactory<>("numberRepetition"));



        final ObservableList<ShowPlanController> data = FXCollections.observableArrayList();

        ConnectionToDB db = new ConnectionToDB();
        db.initialize();
        LoginController lC = new LoginController();


        String selectPlanQuery = "SELECT name ,titel, number_set, number_repetition  FROM users\n" +
                "Inner join training_plans on users.id_user = training_plans.id_user\n" +
                "Inner join training_plan_exercises on training_plans.id_training_plan = training_plan_exercises.id_training_plan\n" +
                "Inner join exercises on training_plan_exercises.id_exercise = exercises.id_exercise INNER JOIN groups_exercises on groups_exercises.id_group = exercises.id_group\n" +
                "Where users.id_user = ? ORDER BY name ASC";
        PreparedStatement userIdInputPStatement = db.connection.prepareStatement(selectPlanQuery);
        userIdInputPStatement.setInt(1, lC.getUserId());
        ResultSet resultSet = userIdInputPStatement.executeQuery();

            while (resultSet.next()) {

                ShowPlanController tPUIC = new ShowPlanController();
                tPUIC.setMuscleGroup(resultSet.getString("name"));
                tPUIC.setExercise(resultSet.getString("titel"));
                tPUIC.setNumberSet(resultSet.getInt("number_set"));
                tPUIC.setNumberRepetition(resultSet.getInt("number_repetition"));
                data.add(tPUIC);
            }


            trainingPlanDataTable = new TableView<>();
            trainingPlanDataTable.setItems(data);
            trainingPlanDataTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            trainingPlanDataTable.getColumns().addAll(muscleGroupColumn, exerciseColumn, numberSetColumn, numberRepColumn);


        vBox.getChildren().addAll(allExercises, trainingPlanDataTable, goBack);


            Scene scene = new Scene(vBox, 671, 571);
            trainingPlanViewStage.setResizable(false);
            trainingPlanViewStage.setScene(scene);
            trainingPlanViewStage.show();
            trainingPlanViewStage.centerOnScreen();


        }
    }



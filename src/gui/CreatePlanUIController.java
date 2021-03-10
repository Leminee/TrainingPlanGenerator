package gui;

import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreatePlanUIController extends CreatePlanUI {

    //Trainingsplan erstellen
    public static void submit() {
        try {
            createTrainingPlan("INSERT INTO training_plans (id_training_plan, id_user, goal, split, duration, training_session) VALUES (NULL, ?, ?, ?, ?,?);");
            createAllPlanData();

            //Fenster schließen und Benutzerprofil öffen
            createTrainingPlanStage.close();
            UserProfileWithTrainingPlanUI userProfileWithTrainingPlanUI = new UserProfileWithTrainingPlanUI();
            Stage stage = new Stage();
            userProfileWithTrainingPlanUI.start(stage);


            AlertNotification aN= new AlertNotification();
            aN.setText(new Label("Dein Trainingsplan wurde erfolgreich erstellt!"));
            Stage stageSuccess = new Stage();
            stageSuccess.initModality(Modality.WINDOW_MODAL);
            stageSuccess.initOwner(stage);
            aN.start(stageSuccess);

            //Fenster automatisch nach 10 Sekunden schließen
            PauseTransition delay = new PauseTransition(Duration.seconds(10));
            delay.setOnFinished( event -> stageSuccess.close() );
            delay.play();

        } catch (Exception eX) {
            System.out.println(eX.getMessage());
        }

    }

    /*ID des Users, sein Trainingsziel, sein Traininhssplit, die Dauer des Trainings und die Anzahl der Trainingseinheiten
     in die Datenbank schreiben*/
    public static void createTrainingPlan(String createPlanQuery) throws Exception{
        ConnectionToDB db = new ConnectionToDB();
        db.initialize();

        PreparedStatement createPlanPStatement = db.connection.prepareStatement(createPlanQuery);

        LoginController lC = new LoginController();
        createPlanPStatement.setString(1, String.valueOf(lC.getUserId()));


        if (firstRadioButton.isSelected()) {
            createPlanPStatement.setString(2, firstRadioButton.getText());
        }
        else if (radioButtonTwo.isSelected()) {
            createPlanPStatement.setString(2,radioButtonTwo.getText());
        }
        else {
            Alert alertGroupOne = new Alert(Alert.AlertType.WARNING);
            alertGroupOne.setTitle("Auswahl Traininsziel");
            alertGroupOne.setContentText("Du musst ein Trainingsziel auswählen");
            alertGroupOne.showAndWait();
        }

        if (radioButtonThree.isSelected()) {
            createPlanPStatement.setString(3, radioButtonThree.getText());
        }
        else if(radioButtonFour.isSelected()) {
            createPlanPStatement.setString(3, radioButtonFour.getText());
        }
        else if (radioButtonFive.isSelected()) {
            createPlanPStatement.setString(3, radioButtonFive.getText());
        }
        else  {
            Alert alertGroupTwo = new Alert(Alert.AlertType.WARNING);
            alertGroupTwo.setTitle("Auswahl Split");
            alertGroupTwo.setContentText("Du musst en Trainingsplit auswählen ");
            alertGroupTwo.showAndWait();

        }
        if (radioButtonSix.isSelected()) {
            createPlanPStatement.setString(4,"30");
        }
        else  if (radioButtonSeven.isSelected()) {
            createPlanPStatement.setString(4,"60");
        }
        else if (radioButtonEight.isSelected()) {
            createPlanPStatement.setString(4,"90");
        }
        else {
            Alert alertGroupTwo = new Alert(Alert.AlertType.WARNING);
            alertGroupTwo.setTitle("Auswahl Trainingsdauer");
            alertGroupTwo.setContentText("Du müsst eine der Auswahlmöglichkeiten treffen");
            alertGroupTwo.showAndWait();

        }

        if (radioButtonNine.isSelected()) {
            createPlanPStatement.setString(5,"1");
        }
        else  if (radioButtonTen.isSelected()) {
            createPlanPStatement.setString(5,"2");
        }
        else if (radioButtonEleven.isSelected()) {
            createPlanPStatement.setString(5,"3");
        }

        else  if (radioButtonTwelve.isSelected()) {
            createPlanPStatement.setString(5,"4");
        }
        else if (radioButtonThirteen.isSelected()) {
            createPlanPStatement.setString(5,"5");
        }
        else  if (radioButtonFourteen.isSelected()) {
            createPlanPStatement.setString(5,"6");
        }
        else if (radioButtonFourteen.isSelected()) {
            createPlanPStatement.setString(5,"7");
        }
        else {
            Alert alertGroupTwo = new Alert(Alert.AlertType.WARNING);
            alertGroupTwo.setTitle("Auswahl Trainingseinheit");
            alertGroupTwo.setContentText("Du musst eine Anzahl an Trainingstage pro Woche auswählen");
            alertGroupTwo.showAndWait();

        }

        if ((firstRadioButton.isSelected() || radioButtonTwo.isSelected()) && (radioButtonThree.isSelected()
        || radioButtonFour.isSelected() || radioButtonFive.isSelected()) && (radioButtonSix.isSelected() ||
                radioButtonSeven.isSelected() ||radioButtonEight.isSelected()) && (radioButtonNine.isSelected() ||
                radioButtonTen.isSelected() ||radioButtonEleven.isSelected() || radioButtonTwelve.isSelected() ||
                radioButtonThirteen.isSelected() ||radioButtonFourteen.isSelected()) ) {

        createPlanPStatement.executeUpdate();

        } else {

            throw new NotSelectedToggleGroupException();

        }

    }

    //Übungen, Anzahl der Sätze und Anzahl der Wiederholungen in die Datenbank schreiben
    public static void createAllPlanData() throws  SQLException {
        ConnectionToDB db = new ConnectionToDB();
        db.initialize();

        int trainingPlanID;

        LoginController lC = new LoginController();

        String getPlanIdQuery = "SELECT training_plans.id_training_plan FROM training_plans INNER JOIN users on training_plans.id_user = users.id_user WHERE users.id_user = ?";
        PreparedStatement iDInputPStatement = db.connection.prepareStatement(getPlanIdQuery);
        iDInputPStatement.setInt(1, lC.getUserId());
        ResultSet rS = iDInputPStatement.executeQuery();

        if (firstRadioButton.isSelected() && radioButtonThree.isSelected() && radioButtonSix.isSelected()) {


            if (rS.next()) {
                trainingPlanID = (int) rS.getLong(1);

                String insertPlanDataQuery = "INSERT INTO training_plan_exercises (id_training_plan_exercises, id_training_plan, id_exercise, number_set, number_repetition)  " +
                        "VALUES (NULL, ?, '1', '3', '10'), (NULL, ?, '10', '3', '10'), (NULL, ?, '21', '3', '10'), (NULL, ?, '25', '3', '10'), (NULL, ?, '40', '3', '10')";
                PreparedStatement createPlanDataPStatement = db.connection.prepareStatement(insertPlanDataQuery);
                createPlanDataPStatement.setInt(1, trainingPlanID);
                createPlanDataPStatement.setInt(2, trainingPlanID);
                createPlanDataPStatement.setInt(3, trainingPlanID);
                createPlanDataPStatement.setInt(4, trainingPlanID);
                createPlanDataPStatement.setInt(5, trainingPlanID);
                createPlanDataPStatement.executeUpdate();

            }

        } else if (radioButtonTwo.isSelected() && radioButtonThree.isSelected() && radioButtonSix.isSelected()) {

            if (rS.next()) {
                trainingPlanID = (int) rS.getLong(1);

                String insertPlanDataQuery = "INSERT INTO training_plan_exercises (id_training_plan_exercises, id_training_plan, id_exercise, number_set, number_repetition)  " +
                        "VALUES (NULL, ?, '1', '3', '20'), (NULL, ?, '10', '3', '20'), (NULL, ?, '21', '3', '20'), (NULL, ?, '25', '3', '20'), (NULL, ?, '40', '3', '20')";
                PreparedStatement createPlanDataPStatement = db.connection.prepareStatement(insertPlanDataQuery);
                createPlanDataPStatement.setInt(1, trainingPlanID);
                createPlanDataPStatement.setInt(2, trainingPlanID);
                createPlanDataPStatement.setInt(3, trainingPlanID);
                createPlanDataPStatement.setInt(4, trainingPlanID);
                createPlanDataPStatement.setInt(5, trainingPlanID);
                createPlanDataPStatement.executeUpdate();

            }


        } else if (firstRadioButton.isSelected() && radioButtonThree.isSelected() && radioButtonSeven.isSelected()) {

            if (rS.next()) {
                trainingPlanID = (int) rS.getLong(1);

                String insertPlanDataQuery = "INSERT INTO `training_plan_exercises` (`id_training_plan_exercises`, `id_training_plan`, `id_exercise`, `number_set`, `number_repetition`)\n" +
                        "            VALUES (NULL, ?, '1', '3', '10'), (NULL, ?, '3', '3', '10'), (NULL, ?, '11', '3', '10'), (NULL, ?, '10', '3', '10'), (NULL, ?, '25', '3', '10'),  " +
                        "(NULL, ?, '24', '3', '10'), (NULL, ?, '28', '3', '10'), (NULL, ?, '40', '3', '10'), (NULL, ?, '33', '3', '10')";
                PreparedStatement createPlanDataPStatement = db.connection.prepareStatement(insertPlanDataQuery);
                createPlanDataPStatement.setInt(1, trainingPlanID);
                createPlanDataPStatement.setInt(2, trainingPlanID);
                createPlanDataPStatement.setInt(3, trainingPlanID);
                createPlanDataPStatement.setInt(4, trainingPlanID);
                createPlanDataPStatement.setInt(5, trainingPlanID);
                createPlanDataPStatement.setInt(6, trainingPlanID);
                createPlanDataPStatement.setInt(7, trainingPlanID);
                createPlanDataPStatement.setInt(8, trainingPlanID);
                createPlanDataPStatement.setInt(9, trainingPlanID);
                createPlanDataPStatement.executeUpdate();



            }
        } else if (radioButtonTwo.isSelected() && radioButtonThree.isSelected() && radioButtonSeven.isSelected()) {

            if (rS.next()) {
                trainingPlanID = (int) rS.getLong(1);

                String insertPlanDataQuery = "INSERT INTO `training_plan_exercises` (`id_training_plan_exercises`, `id_training_plan`, `id_exercise`, `number_set`, `number_repetition`)\n" +
                        "            VALUES (NULL, ?, '1', '3', '20'), (NULL, ?, '3', '3', '20'), (NULL, ?, '11', '3', '20'), (NULL, ?, '10', '3', '20'), (NULL, ?, '25', '3', '20'),  " +
                        "(NULL, ?, '24', '3', '20'), (NULL, ?, '28', '3', '20'), (NULL, ?, '40', '3', '20'), (NULL, ?, '33', '3', '20')";
                PreparedStatement createPlanDataPStatement = db.connection.prepareStatement(insertPlanDataQuery);
                createPlanDataPStatement.setInt(1, trainingPlanID);
                createPlanDataPStatement.setInt(2, trainingPlanID);
                createPlanDataPStatement.setInt(3, trainingPlanID);
                createPlanDataPStatement.setInt(4, trainingPlanID);
                createPlanDataPStatement.setInt(5, trainingPlanID);
                createPlanDataPStatement.setInt(6, trainingPlanID);
                createPlanDataPStatement.setInt(7, trainingPlanID);
                createPlanDataPStatement.setInt(8, trainingPlanID);
                createPlanDataPStatement.setInt(9, trainingPlanID);
                createPlanDataPStatement.executeUpdate();
        }

     } else if (firstRadioButton.isSelected() && radioButtonThree.isSelected() && radioButtonEight.isSelected()) {

            if (rS.next()) {
                trainingPlanID = (int) rS.getLong(1);

                String insertPlanDataQuery = "INSERT INTO `training_plan_exercises` (`id_training_plan_exercises`, `id_training_plan`, `id_exercise`, `number_set`, `number_repetition`)  " +
                        "VALUES (NULL, ?, '1', '3', '10'), (NULL, ?, '3', '3', '10'), (NULL, ?, '11', '3', '10'), (NULL, ?, '10', '3', '10'), (NULL, ?, '25', '3', '10'),  " +
                        "(NULL, ?, '32', '3', '10'), (NULL, ?, '2', '3', '10'), (NULL, ?, '36', '3', '10'), (NULL, ?, '19', '3', '10'), (NULL, ?, '29', '3', '10'), (NULL, ?, '24', '3', '10')";
                PreparedStatement createPlanDataPStatement = db.connection.prepareStatement(insertPlanDataQuery);
                createPlanDataPStatement.setInt(1, trainingPlanID);
                createPlanDataPStatement.setInt(2, trainingPlanID);
                createPlanDataPStatement.setInt(3, trainingPlanID);
                createPlanDataPStatement.setInt(4, trainingPlanID);
                createPlanDataPStatement.setInt(5, trainingPlanID);
                createPlanDataPStatement.setInt(6, trainingPlanID);
                createPlanDataPStatement.setInt(7, trainingPlanID);
                createPlanDataPStatement.setInt(8, trainingPlanID);
                createPlanDataPStatement.setInt(9, trainingPlanID);
                createPlanDataPStatement.setInt(10, trainingPlanID);
                createPlanDataPStatement.setInt(11, trainingPlanID);
                createPlanDataPStatement.executeUpdate();
            }

        }else if(radioButtonTwo.isSelected() && radioButtonThree.isSelected() && radioButtonEight.isSelected()) {

                if (rS.next()) {
                    trainingPlanID = (int) rS.getLong(1);

                    String insertPlanDataQuery = """
                            INSERT INTO `training_plan_exercises` (`id_training_plan_exercises`, `id_training_plan`, `id_exercise`, `number_set`, `number_repetition`) VALUES (NULL, ?, '1', '3', '20'), (NULL, ?, '3', '3', '20'), (NULL, ?, '11', '3', '20'), (NULL, ?, '10', '3', '20'), (NULL, ?, '25', '3', '20'),
                                                    (NULL, ?, '32', '3', '20'), (NULL, ?, '2', '3', '20'), (NULL, ?, '36', '3', '20'), (NULL, ?, '19', '3', '20'), (NULL, ?, '29', '3', '20'), (NULL, ?, '24', '3', '20')""";
                    PreparedStatement createPlanDataPStatement = db.connection.prepareStatement(insertPlanDataQuery);
                    createPlanDataPStatement.setInt(1, trainingPlanID);
                    createPlanDataPStatement.setInt(2, trainingPlanID);
                    createPlanDataPStatement.setInt(3, trainingPlanID);
                    createPlanDataPStatement.setInt(4, trainingPlanID);
                    createPlanDataPStatement.setInt(5, trainingPlanID);
                    createPlanDataPStatement.setInt(6, trainingPlanID);
                    createPlanDataPStatement.setInt(7, trainingPlanID);
                    createPlanDataPStatement.setInt(8, trainingPlanID);
                    createPlanDataPStatement.setInt(9, trainingPlanID);
                    createPlanDataPStatement.setInt(10, trainingPlanID);
                    createPlanDataPStatement.setInt(11, trainingPlanID);
                    createPlanDataPStatement.executeUpdate();

                }

            }

            else if(firstRadioButton.isSelected() && radioButtonFour.isSelected() && radioButtonSix.isSelected()) {

                if (rS.next()) {
                    trainingPlanID = (int) rS.getLong(1);

                    String insertPlanDataQuery = "INSERT INTO `training_plan_exercises` (`id_training_plan_exercises`, `id_training_plan`, `id_exercise`, `number_set`, `number_repetition`)  " +
                            "VALUES (NULL, ?, '1', '3', '10'), (NULL, ?, '3', '3', '10'), (NULL, ?, '11', '3', '10'), (NULL, ?, '10', '3', '10'), (NULL, ?, '40', '3', '10'), (NULL, ?, '25', '3', '10'),  " +
                            "(NULL, ?, '19', '3', '10'), (NULL, ?, '15', '3', '10'), (NULL, ?, '44', '3', '10'), (NULL, ?, '16', '3', '10')";
                    PreparedStatement createPlanDataPStatement = db.connection.prepareStatement(insertPlanDataQuery);
                    createPlanDataPStatement.setInt(1, trainingPlanID);
                    createPlanDataPStatement.setInt(2, trainingPlanID);
                    createPlanDataPStatement.setInt(3, trainingPlanID);
                    createPlanDataPStatement.setInt(4, trainingPlanID);
                    createPlanDataPStatement.setInt(5, trainingPlanID);
                    createPlanDataPStatement.setInt(6, trainingPlanID);
                    createPlanDataPStatement.setInt(7, trainingPlanID);
                    createPlanDataPStatement.setInt(8, trainingPlanID);
                    createPlanDataPStatement.setInt(9, trainingPlanID);
                    createPlanDataPStatement.setInt(10, trainingPlanID);
                    createPlanDataPStatement.executeUpdate();

                }
            }

            else if(radioButtonTwo.isSelected() && radioButtonFour.isSelected() && radioButtonSix.isSelected()) {
            if (rS.next()) {
                trainingPlanID = (int) rS.getLong(1);

                String insertPlanDataQuery = "INSERT INTO `training_plan_exercises` (`id_training_plan_exercises`, `id_training_plan`, `id_exercise`, `number_set`, `number_repetition`)  " +
                        "VALUES (NULL, ?, '1', '3', '20'), (NULL, ?, '3', '3', '20'), (NULL, ?, '11', '3', '20'), (NULL, ?, '10', '3', '20'), (NULL, ?, '40', '3', '20'), (NULL, ?, '25', '3', '20'),  " +
                        "(NULL, ?, '19', '3', '20'), (NULL, ?, '15', '3', '20'), (NULL, ?, '44', '3', '20'), (NULL, ?, '16', '3', '20')";
                PreparedStatement createPlanDataPStatement = db.connection.prepareStatement(insertPlanDataQuery);
                createPlanDataPStatement.setInt(1, trainingPlanID);
                createPlanDataPStatement.setInt(2, trainingPlanID);
                createPlanDataPStatement.setInt(3, trainingPlanID);
                createPlanDataPStatement.setInt(4, trainingPlanID);
                createPlanDataPStatement.setInt(5, trainingPlanID);
                createPlanDataPStatement.setInt(6, trainingPlanID);
                createPlanDataPStatement.setInt(7, trainingPlanID);
                createPlanDataPStatement.setInt(8, trainingPlanID);
                createPlanDataPStatement.setInt(9, trainingPlanID);
                createPlanDataPStatement.setInt(10, trainingPlanID);
                createPlanDataPStatement.executeUpdate();
            }

            }
        else if(firstRadioButton.isSelected() && radioButtonFour.isSelected() && radioButtonSeven.isSelected()) {
            if (rS.next()) {
                trainingPlanID = (int) rS.getLong(1);

                String insertPlanDataQuery = "INSERT INTO `training_plan_exercises` (`id_training_plan_exercises`, `id_training_plan`, `id_exercise`, `number_set`, `number_repetition`)  " +
                        "VALUES (NULL, ?, '1', '3', '10'), (NULL, ?, '3', '3', '10'), (NULL, ?, '11', '3', '10'), (NULL, ?, '10', '3', '10'), (NULL, ?, '2', '3', '10'), (NULL, ?, '29', '3', '10'),  " +
                        "(NULL, ?, '28', '3', '10'), (NULL, ?, '40', '3', '10'), (NULL, ?, '39', '3', '10'), (NULL, ?, '25', '3', '10'), (NULL, ?, '16', '3', '10'), (NULL, ?, '44', '3', '10'),  " +
                        "(NULL, ?, '9', '3', '10'), (NULL, ?, '17', '3', '10'), (NULL, ?, '33', '3', '10'), (NULL, ?, '38', '3', '10'), (NULL, ?, '34', '3', '10')";
                PreparedStatement createPlanDataPStatement = db.connection.prepareStatement(insertPlanDataQuery);
                createPlanDataPStatement.setInt(1, trainingPlanID);
                createPlanDataPStatement.setInt(2, trainingPlanID);
                createPlanDataPStatement.setInt(3, trainingPlanID);
                createPlanDataPStatement.setInt(4, trainingPlanID);
                createPlanDataPStatement.setInt(5, trainingPlanID);
                createPlanDataPStatement.setInt(6, trainingPlanID);
                createPlanDataPStatement.setInt(7, trainingPlanID);
                createPlanDataPStatement.setInt(8, trainingPlanID);
                createPlanDataPStatement.setInt(9, trainingPlanID);
                createPlanDataPStatement.setInt(10, trainingPlanID);
                createPlanDataPStatement.setInt(11, trainingPlanID);
                createPlanDataPStatement.setInt(12, trainingPlanID);
                createPlanDataPStatement.setInt(13, trainingPlanID);
                createPlanDataPStatement.setInt(14, trainingPlanID);
                createPlanDataPStatement.setInt(15, trainingPlanID);
                createPlanDataPStatement.setInt(16, trainingPlanID);
                createPlanDataPStatement.setInt(17, trainingPlanID);
                createPlanDataPStatement.executeUpdate();
            }

        }
        else if(radioButtonTwo.isSelected() && radioButtonFour.isSelected() && radioButtonSeven.isSelected()) {
            if (rS.next()) {
                trainingPlanID = (int) rS.getLong(1);

                String insertPlanDataQuery = "INSERT INTO `training_plan_exercises` (`id_training_plan_exercises`, `id_training_plan`, `id_exercise`, `number_set`, `number_repetition`)  " +
                        "VALUES (NULL, ?, '1', '3', '20'), (NULL, ?, '3', '3', '20'), (NULL, ?, '11', '3', '20'), (NULL, ?, '10', '3', '20'), (NULL, ?, '2', '3', '20'), " +
                        " (NULL, ?, '29', '3', '20'), (NULL, ?, '28', '3', '20'), (NULL, ?, '40', '3', '20'), (NULL, ?, '39', '3', '20'), (NULL, ?, '25', '3', '20'), (NULL, ?, '16', '3', '20'),  " +
                        "(NULL, ?, '44', '3', '20'), (NULL, ?, '9', '3', '20'), (NULL, ?, '17', '3', '20'), (NULL, ?, '33', '3', '20'), (NULL, ?, '38', '3', '20'), (NULL, ?, '34', '3', '20')";
                PreparedStatement createPlanDataPStatement = db.connection.prepareStatement(insertPlanDataQuery);
                createPlanDataPStatement.setInt(1, trainingPlanID);
                createPlanDataPStatement.setInt(2, trainingPlanID);
                createPlanDataPStatement.setInt(3, trainingPlanID);
                createPlanDataPStatement.setInt(4, trainingPlanID);
                createPlanDataPStatement.setInt(5, trainingPlanID);
                createPlanDataPStatement.setInt(6, trainingPlanID);
                createPlanDataPStatement.setInt(7, trainingPlanID);
                createPlanDataPStatement.setInt(8, trainingPlanID);
                createPlanDataPStatement.setInt(9, trainingPlanID);
                createPlanDataPStatement.setInt(10, trainingPlanID);
                createPlanDataPStatement.setInt(11, trainingPlanID);
                createPlanDataPStatement.setInt(12, trainingPlanID);
                createPlanDataPStatement.setInt(13, trainingPlanID);
                createPlanDataPStatement.setInt(14, trainingPlanID);
                createPlanDataPStatement.setInt(15, trainingPlanID);
                createPlanDataPStatement.setInt(16, trainingPlanID);
                createPlanDataPStatement.setInt(17, trainingPlanID);
                createPlanDataPStatement.executeUpdate();
            }

        }

        else if(firstRadioButton.isSelected() && radioButtonFour.isSelected() && radioButtonEight.isSelected()) {
            if (rS.next()) {
                trainingPlanID = (int) rS.getLong(1);

                String insertPlanDataQuery = "INSERT INTO `training_plan_exercises` (`id_training_plan_exercises`, `id_training_plan`, `id_exercise`, `number_set`, `number_repetition`)  " +
                        "VALUES (NULL, ?, '1', '3', '10'), (NULL, ?, '2', '3', '10'), (NULL, ?, '3', '3', '10'), (NULL, ?, '11', '3', '10'), (NULL, ?, '10', '3', '10'), (NULL, ?, '35', '3', '10'),  " +
                        "(NULL,?, '6', '3', '10'), (NULL, ?, '20', '3', '10'), (NULL, ?, '29', '3', '10'), (NULL, ?, '21', '3', '10'), (NULL, ?, '39', '3', '10'), (NULL, ?, '12', '3', '10'),  " +
                        "(NULL, ?, '40', '3', '10'), (NULL, ?, '25', '3', '10'), (NULL, ?, '42', '3', '10'), (NULL, ?, '16', '3', '10'), (NULL, ?, '17', '3', '10'), (NULL, ?, '18', '3', '10'),  " +
                        "(NULL, ?, '26', '3', '10'), (NULL, ?, '32', '3', '10'), (NULL, ?, '28', '3', '10'), (NULL, ?, '27', '3', '10'), (NULL, ?, '23', '3', '10'), (NULL, ?, '7', '3', '10'),  " +
                        "(NULL, ?, '37', '3', '10'), (NULL, ?, '22', '3', '10')";
                PreparedStatement createPlanDataPStatement = db.connection.prepareStatement(insertPlanDataQuery);
                createPlanDataPStatement.setInt(1, trainingPlanID);
                createPlanDataPStatement.setInt(2, trainingPlanID);
                createPlanDataPStatement.setInt(3, trainingPlanID);
                createPlanDataPStatement.setInt(4, trainingPlanID);
                createPlanDataPStatement.setInt(5, trainingPlanID);
                createPlanDataPStatement.setInt(6, trainingPlanID);
                createPlanDataPStatement.setInt(7, trainingPlanID);
                createPlanDataPStatement.setInt(8, trainingPlanID);
                createPlanDataPStatement.setInt(9, trainingPlanID);
                createPlanDataPStatement.setInt(10, trainingPlanID);
                createPlanDataPStatement.setInt(11, trainingPlanID);
                createPlanDataPStatement.setInt(12, trainingPlanID);
                createPlanDataPStatement.setInt(13, trainingPlanID);
                createPlanDataPStatement.setInt(14, trainingPlanID);
                createPlanDataPStatement.setInt(15, trainingPlanID);
                createPlanDataPStatement.setInt(16, trainingPlanID);
                createPlanDataPStatement.setInt(17, trainingPlanID);
                createPlanDataPStatement.setInt(18, trainingPlanID);
                createPlanDataPStatement.setInt(19, trainingPlanID);
                createPlanDataPStatement.setInt(20, trainingPlanID);
                createPlanDataPStatement.setInt(21, trainingPlanID);
                createPlanDataPStatement.setInt(22, trainingPlanID);
                createPlanDataPStatement.setInt(23, trainingPlanID);
                createPlanDataPStatement.setInt(24, trainingPlanID);
                createPlanDataPStatement.setInt(25, trainingPlanID);
                createPlanDataPStatement.setInt(26, trainingPlanID);
                createPlanDataPStatement.executeUpdate();
            }

        }
        else if(radioButtonTwo.isSelected() && radioButtonFour.isSelected() && radioButtonEight.isSelected()) {
            if (rS.next()) {
                trainingPlanID = (int) rS.getLong(1);

                String insertPlanDataQuery = "INSERT INTO `training_plan_exercises` (`id_training_plan_exercises`, `id_training_plan`, `id_exercise`, `number_set`, `number_repetition`)  " +
                        "VALUES (NULL, ?, '1', '3', '20'), (NULL, ?, '2', '3', '20'), (NULL, ?, '3', '3', '20'), (NULL, ?, '11', '3', '20'), (NULL, ?, '10', '3', '20'), (NULL, ?, '35', '3', '20'),  " +
                        "(NULL,?, '6', '3', '20'), (NULL, ?, '20', '3', '20'), (NULL, ?, '29', '3', '20'), (NULL, ?, '21', '3', '20'), (NULL, ?, '39', '3', '20'), (NULL, ?, '12', '3', '20'),  " +
                        "(NULL, ?, '40', '3', '20'), (NULL, ?, '25', '3', '20'), (NULL, ?, '42', '3', '20'), (NULL, ?, '16', '3', '20'), (NULL, ?, '17', '3', '20'), (NULL, ?, '18', '3', '20'),  " +
                        "(NULL, ?, '26', '3', '20'), (NULL, ?, '32', '3', '20'), (NULL, ?, '28', '3', '20'), (NULL, ?, '27', '3', '20'), (NULL, ?, '23', '3', '20'), (NULL, ?, '7', '3', '20'),  " +
                        "(NULL, ?, '37', '3', '20'), (NULL, ?, '22', '3', '20')";
                PreparedStatement createPlanDataPStatement = db.connection.prepareStatement(insertPlanDataQuery);
                createPlanDataPStatement.setInt(1, trainingPlanID);
                createPlanDataPStatement.setInt(2, trainingPlanID);
                createPlanDataPStatement.setInt(3, trainingPlanID);
                createPlanDataPStatement.setInt(4, trainingPlanID);
                createPlanDataPStatement.setInt(5, trainingPlanID);
                createPlanDataPStatement.setInt(6, trainingPlanID);
                createPlanDataPStatement.setInt(7, trainingPlanID);
                createPlanDataPStatement.setInt(8, trainingPlanID);
                createPlanDataPStatement.setInt(9, trainingPlanID);
                createPlanDataPStatement.setInt(10, trainingPlanID);
                createPlanDataPStatement.setInt(11, trainingPlanID);
                createPlanDataPStatement.setInt(12, trainingPlanID);
                createPlanDataPStatement.setInt(13, trainingPlanID);
                createPlanDataPStatement.setInt(14, trainingPlanID);
                createPlanDataPStatement.setInt(15, trainingPlanID);
                createPlanDataPStatement.setInt(16, trainingPlanID);
                createPlanDataPStatement.setInt(17, trainingPlanID);
                createPlanDataPStatement.setInt(18, trainingPlanID);
                createPlanDataPStatement.setInt(19, trainingPlanID);
                createPlanDataPStatement.setInt(20, trainingPlanID);
                createPlanDataPStatement.setInt(21, trainingPlanID);
                createPlanDataPStatement.setInt(22, trainingPlanID);
                createPlanDataPStatement.setInt(23, trainingPlanID);
                createPlanDataPStatement.setInt(24, trainingPlanID);
                createPlanDataPStatement.setInt(25, trainingPlanID);
                createPlanDataPStatement.setInt(26, trainingPlanID);
                createPlanDataPStatement.executeUpdate();
            }

        }

        else if(firstRadioButton.isSelected() && radioButtonFive.isSelected() && radioButtonSix.isSelected()) {
            if (rS.next()) {
                trainingPlanID = (int) rS.getLong(1);

                String insertPlanDataQuery = "INSERT INTO `training_plan_exercises` (`id_training_plan_exercises`, `id_training_plan`, `id_exercise`, `number_set`, `number_repetition`)  " +
                        "VALUES (NULL, ?, '1', '3', '10'), (NULL, ?, '3', '3', '10'), (NULL, ?, '29', '3', '10'), (NULL, ?, '32', '3', '10'), (NULL, ?, '39', '3', '10'), " +
                        " (NULL, ?, '11', '3', '10'), (NULL, ?, '10', '3', '10'), (NULL, ?, '36', '3', '10'), (NULL, ?, '43', '3', '10'), (NULL, ?, '12', '3', '10'), (NULL, ?, '2', '3', '10'),  " +
                        "(NULL, ?, '4', '3', '10'), (NULL, ?, '21', '3', '10'), (NULL, ?, '44', '3', '10'), (NULL, ?, '16', '3', '10'), (NULL, ?, '25', '3', '10')";
                PreparedStatement createPlanDataPStatement = db.connection.prepareStatement(insertPlanDataQuery);
                createPlanDataPStatement.setInt(1, trainingPlanID);
                createPlanDataPStatement.setInt(2, trainingPlanID);
                createPlanDataPStatement.setInt(3, trainingPlanID);
                createPlanDataPStatement.setInt(4, trainingPlanID);
                createPlanDataPStatement.setInt(5, trainingPlanID);
                createPlanDataPStatement.setInt(6, trainingPlanID);
                createPlanDataPStatement.setInt(7, trainingPlanID);
                createPlanDataPStatement.setInt(8, trainingPlanID);
                createPlanDataPStatement.setInt(9, trainingPlanID);
                createPlanDataPStatement.setInt(10, trainingPlanID);
                createPlanDataPStatement.setInt(11, trainingPlanID);
                createPlanDataPStatement.setInt(12, trainingPlanID);
                createPlanDataPStatement.setInt(13, trainingPlanID);
                createPlanDataPStatement.setInt(14, trainingPlanID);
                createPlanDataPStatement.setInt(15, trainingPlanID);
                createPlanDataPStatement.setInt(16, trainingPlanID);
                createPlanDataPStatement.executeUpdate();
            }

        }

        else if(radioButtonTwo.isSelected() && radioButtonFive.isSelected() && radioButtonSix.isSelected()) {
            if (rS.next()) {
                trainingPlanID = (int) rS.getLong(1);

                String insertPlanDataQuery = "INSERT INTO `training_plan_exercises` (`id_training_plan_exercises`, `id_training_plan`, `id_exercise`, `number_set`, `number_repetition`)  " +
                        "VALUES (NULL, ?, '1', '3', '20'), (NULL, ?, '3', '3', '20'), (NULL, ?, '29', '3', '20'), (NULL, ?, '32', '3', '20'), (NULL, ?, '39', '3', '20'), (NULL, ?, '11', '3', '20'),  " +
                        "(NULL, ?, '10', '3', '20'), (NULL, ?, '36', '3', '20'), (NULL, ?, '43', '3', '20'), (NULL, ?, '12', '3', '20'), (NULL, ?, '2', '3', '20'), (NULL, ?, '4', '3', '20'),  " +
                        "(NULL, ?, '21', '3', '20'), (NULL, ?, '44', '3', '20'), (NULL, ?, '16', '3', '20'), (NULL, ?, '25', '3', '20')";
                PreparedStatement createPlanDataPStatement = db.connection.prepareStatement(insertPlanDataQuery);
                createPlanDataPStatement.setInt(1, trainingPlanID);
                createPlanDataPStatement.setInt(2, trainingPlanID);
                createPlanDataPStatement.setInt(3, trainingPlanID);
                createPlanDataPStatement.setInt(4, trainingPlanID);
                createPlanDataPStatement.setInt(5, trainingPlanID);
                createPlanDataPStatement.setInt(6, trainingPlanID);
                createPlanDataPStatement.setInt(7, trainingPlanID);
                createPlanDataPStatement.setInt(8, trainingPlanID);
                createPlanDataPStatement.setInt(9, trainingPlanID);
                createPlanDataPStatement.setInt(10, trainingPlanID);
                createPlanDataPStatement.setInt(11, trainingPlanID);
                createPlanDataPStatement.setInt(12, trainingPlanID);
                createPlanDataPStatement.setInt(13, trainingPlanID);
                createPlanDataPStatement.setInt(14, trainingPlanID);
                createPlanDataPStatement.setInt(15, trainingPlanID);
                createPlanDataPStatement.setInt(16, trainingPlanID);

                createPlanDataPStatement.executeUpdate();
            }

        }


        else if(firstRadioButton.isSelected() && radioButtonFive.isSelected() && radioButtonSeven.isSelected()) {
            if (rS.next()) {
                trainingPlanID = (int) rS.getLong(1);

                String insertPlanDataQuery = "INSERT INTO `training_plan_exercises` (`id_training_plan_exercises`, `id_training_plan`, `id_exercise`, `number_set`, `number_repetition`)  " +
                        "VALUES (NULL, ?, '8', '3', '10'), (NULL, ?, '2', '3', '10'), (NULL, ?, '31', '3', '10'), (NULL, ?, '4', '3', '10'), (NULL, ?, '3', '3', '10'), (NULL, ?, '29', '3', '10'), " +
                        " (NULL, ?, '21', '3', '10'), (NULL, ?, '40', '3', '10'), (NULL, ?, '32', '3', '10'), (NULL, ?, '9', '3', '10'), (NULL, ?, '11', '3', '10'), (NULL, ?, '10', '3', '10'),  " +
                        "(NULL, ?, '36', '3', '10'), (NULL, ?, '1', '3', '10'), (NULL, ?, '15', '3', '10'), (NULL, ?, '33', '3', '10'), (NULL, ?, '20', '3', '10'), (NULL, ?, '22', '3', '10'),  " +
                        "(NULL, ?, '18', '3', '10'), (NULL, ?, '44', '3', '10'), (NULL, ?, '17', '3', '10'), (NULL, ?, '19', '3', '10'), (NULL, ?, '28', '3', '10'), (NULL, ?, '26', '3', '10'),  " +
                        "(NULL, ?, '41', '3', '10'), (NULL, ?, '23', '3', '10'), (NULL, ?, '5', '3', '10')";
                PreparedStatement createPlanDataPStatement = db.connection.prepareStatement(insertPlanDataQuery);
                createPlanDataPStatement.setInt(1, trainingPlanID);
                createPlanDataPStatement.setInt(2, trainingPlanID);
                createPlanDataPStatement.setInt(3, trainingPlanID);
                createPlanDataPStatement.setInt(4, trainingPlanID);
                createPlanDataPStatement.setInt(5, trainingPlanID);
                createPlanDataPStatement.setInt(6, trainingPlanID);
                createPlanDataPStatement.setInt(7, trainingPlanID);
                createPlanDataPStatement.setInt(8, trainingPlanID);
                createPlanDataPStatement.setInt(9, trainingPlanID);
                createPlanDataPStatement.setInt(10, trainingPlanID);
                createPlanDataPStatement.setInt(11, trainingPlanID);
                createPlanDataPStatement.setInt(12, trainingPlanID);
                createPlanDataPStatement.setInt(13, trainingPlanID);
                createPlanDataPStatement.setInt(14, trainingPlanID);
                createPlanDataPStatement.setInt(15, trainingPlanID);
                createPlanDataPStatement.setInt(16, trainingPlanID);
                createPlanDataPStatement.setInt(17, trainingPlanID);
                createPlanDataPStatement.setInt(18, trainingPlanID);
                createPlanDataPStatement.setInt(19, trainingPlanID);
                createPlanDataPStatement.setInt(20, trainingPlanID);
                createPlanDataPStatement.setInt(21, trainingPlanID);
                createPlanDataPStatement.setInt(22, trainingPlanID);
                createPlanDataPStatement.setInt(23, trainingPlanID);
                createPlanDataPStatement.setInt(24, trainingPlanID);
                createPlanDataPStatement.setInt(25, trainingPlanID);
                createPlanDataPStatement.setInt(26, trainingPlanID);
                createPlanDataPStatement.setInt(27, trainingPlanID);
                createPlanDataPStatement.executeUpdate();
            }

        } else if(radioButtonTwo.isSelected() && radioButtonFive.isSelected() && radioButtonSeven.isSelected()) {
            if (rS.next()) {
                trainingPlanID = (int) rS.getLong(1);

                String insertPlanDataQuery = "INSERT INTO `training_plan_exercises` (`id_training_plan_exercises`, `id_training_plan`, `id_exercise`, `number_set`, `number_repetition`) " +
                        "VALUES (NULL, ?, '8', '3', '20'), (NULL, ?, '2', '3', '20'), (NULL, ?, '31', '3', '20'), (NULL, ?, '4', '3', '20'), (NULL, ?, '3', '3', '20'), (NULL, ?, '29', '3', '20'),  " +
                        "(NULL, ?, '21', '3', '20'), (NULL, ?, '40', '3', '20'), (NULL, ?, '32', '3', '20'), (NULL, ?, '9', '3', '20'), (NULL, ?, '11', '3', '20'), (NULL, ?, '10', '3', '20'),  " +
                        "(NULL, ?, '36', '3', '20'), (NULL, ?, '1', '3', '20'), (NULL, ?, '15', '3', '20'), (NULL, ?, '33', '3', '20'), (NULL, ?, '20', '3', '20'), (NULL, ?, '22', '3', '20'),  " +
                        "(NULL, ?, '18', '3', '20'), (NULL, ?, '44', '3', '20'), (NULL, ?, '17', '3', '20'), (NULL, ?, '19', '3', '20'), (NULL, ?, '28', '3', '20'), (NULL, ?, '26', '3', '20'),  " +
                        "(NULL, ?, '41', '3', '20'), (NULL, ?, '23', '3', '20'), (NULL, ?, '5', '3', '20')";
                PreparedStatement createPlanDataPStatement = db.connection.prepareStatement(insertPlanDataQuery);
                createPlanDataPStatement.setInt(1, trainingPlanID);
                createPlanDataPStatement.setInt(2, trainingPlanID);
                createPlanDataPStatement.setInt(3, trainingPlanID);
                createPlanDataPStatement.setInt(4, trainingPlanID);
                createPlanDataPStatement.setInt(5, trainingPlanID);
                createPlanDataPStatement.setInt(6, trainingPlanID);
                createPlanDataPStatement.setInt(7, trainingPlanID);
                createPlanDataPStatement.setInt(8, trainingPlanID);
                createPlanDataPStatement.setInt(9, trainingPlanID);
                createPlanDataPStatement.setInt(10, trainingPlanID);
                createPlanDataPStatement.setInt(11, trainingPlanID);
                createPlanDataPStatement.setInt(12, trainingPlanID);
                createPlanDataPStatement.setInt(13, trainingPlanID);
                createPlanDataPStatement.setInt(14, trainingPlanID);
                createPlanDataPStatement.setInt(15, trainingPlanID);
                createPlanDataPStatement.setInt(16, trainingPlanID);
                createPlanDataPStatement.setInt(17, trainingPlanID);
                createPlanDataPStatement.setInt(18, trainingPlanID);
                createPlanDataPStatement.setInt(19, trainingPlanID);
                createPlanDataPStatement.setInt(20, trainingPlanID);
                createPlanDataPStatement.setInt(21, trainingPlanID);
                createPlanDataPStatement.setInt(22, trainingPlanID);
                createPlanDataPStatement.setInt(23, trainingPlanID);
                createPlanDataPStatement.setInt(24, trainingPlanID);
                createPlanDataPStatement.setInt(25, trainingPlanID);
                createPlanDataPStatement.setInt(26, trainingPlanID);
                createPlanDataPStatement.setInt(27, trainingPlanID);
                createPlanDataPStatement.executeUpdate();
            }

        }
        else if(firstRadioButton.isSelected() && radioButtonFive.isSelected() && radioButtonEight.isSelected()) {
            if (rS.next()) {
                trainingPlanID = (int) rS.getLong(1);

                String insertPlanDataQuery = "INSERT INTO `training_plan_exercises` (`id_training_plan_exercises`, `id_training_plan`, `id_exercise`, `number_set`, `number_repetition`)  " +
                        "VALUES (NULL, ?, '1', '3', '10'), (NULL, ?, '2', '3', '10'), (NULL, ?, '3', '3', '10'), (NULL, ?, '4', '3', '10'), (NULL, ?, '5', '3', '10'), (NULL, ?, '6', '3', '10'), " +
                        " (NULL, ?, '7', '3', '10'), (NULL, ?, '8', '3', '10'), (NULL, ?, '9', '3', '10'), (NULL, ?, '10', '3', '10'), (NULL, ?, '11', '3', '10'), (NULL, ?, '12', '3', '10'), (NULL, ?, '13', '3', '10'), " +
                        " (NULL, ?, '14', '3', '10'), (NULL, ?, '15', '3', '10'), (NULL, ?, '16', '3', '10'), (NULL, ?, '17', '3', '10'), (NULL, ?, '18', '3', '10'), (NULL, ?, '19', '3', '10'), (NULL, ?, '20', '3', '10'),  " +
                        "(NULL, ?, '21', '3', '10'), (NULL, ?, '22', '3', '10'), (NULL, ?, '23', '3', '10'), (NULL, ?, '24', '3', '10'), (NULL,?, '25', '3', '10'), (NULL, ?, '26', '3', '10'), (NULL, ?, '27', '3', '10'), " +
                        " (NULL, ?, '28', '3', '10'), (NULL, ?, '29', '3', '10'), (NULL, ?, '30', '3', '10'), (NULL, ?, '31', '3', '10'), (NULL, ?, '32', '3', '10'), (NULL, ?, '33', '3', '10'), (NULL, ?, '34', '3', '10'), (NULL, ?, '35', '3', '10'),   " +
                        "(NULL, ?, '36', '3', '10'), (NULL, ?, '37', '3', '10'), (NULL, ?, '38', '3', '10'), (NULL, ?, '39', '3', '10'), (NULL, ?, '40', '3', '10')";
                PreparedStatement createPlanDataPStatement = db.connection.prepareStatement(insertPlanDataQuery);
                createPlanDataPStatement.setInt(1, trainingPlanID);
                createPlanDataPStatement.setInt(2, trainingPlanID);
                createPlanDataPStatement.setInt(3, trainingPlanID);
                createPlanDataPStatement.setInt(4, trainingPlanID);
                createPlanDataPStatement.setInt(5, trainingPlanID);
                createPlanDataPStatement.setInt(6, trainingPlanID);
                createPlanDataPStatement.setInt(7, trainingPlanID);
                createPlanDataPStatement.setInt(8, trainingPlanID);
                createPlanDataPStatement.setInt(9, trainingPlanID);
                createPlanDataPStatement.setInt(10, trainingPlanID);
                createPlanDataPStatement.setInt(11, trainingPlanID);
                createPlanDataPStatement.setInt(12, trainingPlanID);
                createPlanDataPStatement.setInt(13, trainingPlanID);
                createPlanDataPStatement.setInt(14, trainingPlanID);
                createPlanDataPStatement.setInt(15, trainingPlanID);
                createPlanDataPStatement.setInt(16, trainingPlanID);
                createPlanDataPStatement.setInt(17, trainingPlanID);
                createPlanDataPStatement.setInt(18, trainingPlanID);
                createPlanDataPStatement.setInt(19, trainingPlanID);
                createPlanDataPStatement.setInt(20, trainingPlanID);
                createPlanDataPStatement.setInt(21, trainingPlanID);
                createPlanDataPStatement.setInt(22, trainingPlanID);
                createPlanDataPStatement.setInt(23, trainingPlanID);
                createPlanDataPStatement.setInt(24, trainingPlanID);
                createPlanDataPStatement.setInt(25, trainingPlanID);
                createPlanDataPStatement.setInt(26, trainingPlanID);
                createPlanDataPStatement.setInt(27, trainingPlanID);
                createPlanDataPStatement.setInt(28, trainingPlanID);
                createPlanDataPStatement.setInt(29, trainingPlanID);
                createPlanDataPStatement.setInt(30, trainingPlanID);
                createPlanDataPStatement.setInt(31, trainingPlanID);
                createPlanDataPStatement.setInt(32, trainingPlanID);
                createPlanDataPStatement.setInt(33, trainingPlanID);
                createPlanDataPStatement.setInt(34, trainingPlanID);
                createPlanDataPStatement.setInt(35, trainingPlanID);
                createPlanDataPStatement.setInt(36, trainingPlanID);
                createPlanDataPStatement.setInt(37, trainingPlanID);
                createPlanDataPStatement.setInt(38, trainingPlanID);
                createPlanDataPStatement.setInt(39, trainingPlanID);
                createPlanDataPStatement.setInt(40, trainingPlanID);
                createPlanDataPStatement.executeUpdate();
            }

        }

        else if(radioButtonTwo.isSelected() && radioButtonFive.isSelected() && radioButtonEight.isSelected()) {
            if (rS.next()) {
                trainingPlanID = (int) rS.getLong(1);

                String insertPlanDataQuery = "INSERT INTO `training_plan_exercises` (`id_training_plan_exercises`, `id_training_plan`, `id_exercise`, `number_set`, `number_repetition`)  " +
                        "VALUES (NULL, ?, '1', '3', '20'), (NULL, ?, '2', '3', '20'), (NULL, ?, '3', '3', '20'), (NULL, ?, '4', '3', '20'), (NULL, ?, '5', '3', '20'), (NULL, ?, '6', '3', '20'), " +
                        " (NULL, ?, '7', '3', '20'), (NULL, ?, '8', '3', '20'), (NULL, ?, '9', '3', '20'), (NULL, ?, '10', '3', '20'), (NULL, ?, '11', '3', '20'), (NULL, ?, '12', '3', '20'),  " +
                        "(NULL, ?, '13', '3', '20'), (NULL, ?, '14', '3', '20'), (NULL, ?, '15', '3', '20'), (NULL, ?, '16', '3', '20'), (NULL, ?, '17', '3', '20'), (NULL, ?, '18', '3', '20'),  " +
                        "(NULL, ?, '19', '3', '20'), (NULL, ?, '20', '3', '20'), (NULL, ?, '21', '3', '20'), (NULL, ?, '22', '3', '20'), (NULL, ?, '23', '3', '20'), (NULL, ?, '24', '3', '20'),  " +
                        "(NULL,?, '25', '3', '20'), (NULL, ?, '26', '3', '20'), (NULL, ?, '27', '3', '20'), (NULL, ?, '28', '3', '20'), (NULL, ?, '29', '3', '20'), (NULL, ?, '30', '3', '20'),  " +
                        "(NULL, ?, '31', '3', '20'), (NULL, ?, '32', '3', '20'), (NULL, ?, '33', '3', '20'), (NULL, ?, '34', '3', '20'), (NULL, ?, '35', '3', '20'), (NULL, ?, '36', '3', '20'),  " +
                        "(NULL, ?, '37', '3', '20'), (NULL, ?, '38', '3', '20'), (NULL, ?, '39', '3', '20'), (NULL, ?, '40', '3', '20')";
                PreparedStatement createPlanDataPStatement = db.connection.prepareStatement(insertPlanDataQuery);
                createPlanDataPStatement.setInt(1, trainingPlanID);
                createPlanDataPStatement.setInt(2, trainingPlanID);
                createPlanDataPStatement.setInt(3, trainingPlanID);
                createPlanDataPStatement.setInt(4, trainingPlanID);
                createPlanDataPStatement.setInt(5, trainingPlanID);
                createPlanDataPStatement.setInt(6, trainingPlanID);
                createPlanDataPStatement.setInt(7, trainingPlanID);
                createPlanDataPStatement.setInt(8, trainingPlanID);
                createPlanDataPStatement.setInt(9, trainingPlanID);
                createPlanDataPStatement.setInt(10, trainingPlanID);
                createPlanDataPStatement.setInt(11, trainingPlanID);
                createPlanDataPStatement.setInt(12, trainingPlanID);
                createPlanDataPStatement.setInt(13, trainingPlanID);
                createPlanDataPStatement.setInt(14, trainingPlanID);
                createPlanDataPStatement.setInt(15, trainingPlanID);
                createPlanDataPStatement.setInt(16, trainingPlanID);
                createPlanDataPStatement.setInt(17, trainingPlanID);
                createPlanDataPStatement.setInt(18, trainingPlanID);
                createPlanDataPStatement.setInt(19, trainingPlanID);
                createPlanDataPStatement.setInt(20, trainingPlanID);
                createPlanDataPStatement.setInt(21, trainingPlanID);
                createPlanDataPStatement.setInt(22, trainingPlanID);
                createPlanDataPStatement.setInt(23, trainingPlanID);
                createPlanDataPStatement.setInt(24, trainingPlanID);
                createPlanDataPStatement.setInt(25, trainingPlanID);
                createPlanDataPStatement.setInt(26, trainingPlanID);
                createPlanDataPStatement.setInt(27, trainingPlanID);
                createPlanDataPStatement.setInt(28, trainingPlanID);
                createPlanDataPStatement.setInt(29, trainingPlanID);
                createPlanDataPStatement.setInt(30, trainingPlanID);
                createPlanDataPStatement.setInt(31, trainingPlanID);
                createPlanDataPStatement.setInt(32, trainingPlanID);
                createPlanDataPStatement.setInt(33, trainingPlanID);
                createPlanDataPStatement.setInt(34, trainingPlanID);
                createPlanDataPStatement.setInt(35, trainingPlanID);
                createPlanDataPStatement.setInt(36, trainingPlanID);
                createPlanDataPStatement.setInt(37, trainingPlanID);
                createPlanDataPStatement.setInt(38, trainingPlanID);
                createPlanDataPStatement.setInt(39, trainingPlanID);
                createPlanDataPStatement.setInt(40, trainingPlanID);
                createPlanDataPStatement.executeUpdate();
            }

        }
        }
    }


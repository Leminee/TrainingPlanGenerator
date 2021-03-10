package gui;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.sql.SQLException;


public class UserProfileController extends UserProfileWithTrainingPlanUI {


    //User ausloggen
    public static void logout() {
        Main home = new Main();
        Stage loginStage = new Stage();
        home.start(loginStage);

    }
    //Trainingsplan anzeigen
    public static void loadTrainingPlan() {
        ShowPlanUI tPW = new ShowPlanUI();
        Stage trainingPlanViewStage = new Stage();
        try {
            userProfileStage.close();
            tPW.start(trainingPlanViewStage);
        } catch (FileNotFoundException | SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    //Optionen-Fenster öffnen
    public static void openOptionsWindow() {
        Options oP = new Options();
        Stage optionsStage = new Stage();
        Stage userProfileStage = new Stage();
        optionsStage.initModality(Modality.WINDOW_MODAL);
        optionsStage.initOwner(userProfileStage);

        try {
            oP.start(optionsStage);


        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println(fileNotFoundException.getMessage());
        }

    }

}

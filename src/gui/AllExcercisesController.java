package gui;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import database.AllExcersisesList;
import database.TestExcersises;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class AllExcercisesController {
	
	 
	 static ObservableList<TestExcersises> exerciseList = FXCollections.observableArrayList();
	 @FXML
	 private TreeView<String> tree;
	 private final TreeItem<String> rooth = new TreeItem<>("�bungen");
	 @FXML
	 private Text titleExcersise;
	 @FXML
	 private Text descriptionExcersise;
	 @FXML
	 private ImageView pictures;
	 @FXML
	 private VBox box;
	 

	 
	 
	 public void initialize() {

		 		exerciseList = AllExcersisesList.getAllExcersiesList();
		 
	            rooth.setExpanded(true);
	            
	            for(TestExcersises item: exerciseList) {
	            	rooth.getChildren().add(new TreeItem<>(item.getTitle()));
	            }
	            tree.setRoot(rooth);
	 }

	 
	 @FXML
	 public void fillData() throws FileNotFoundException {
		 
		 TreeItem<String> selectedTreeItem = tree.getSelectionModel().getSelectedItem();
		 if(tree.getTreeItemLevel(selectedTreeItem) == 1) {
			 titleExcersise.setText(selectedTreeItem.getValue());
			 	for(TestExcersises item2: exerciseList) {
			 		if(selectedTreeItem.getValue().equals(item2.getTitle())) {
			 			descriptionExcersise.setText(item2.getDescription());
			 			FileInputStream firstInputStream = new FileInputStream("excersisePictures/" + item2.getTitle() + ".gif");
			 	        Image firstImage = new Image(firstInputStream);
			 			
			 			pictures.setImage(firstImage);

			 			
			 		}
			 	}
		 }
	 }
	 
	 
	 public void allExercises() {
	        try {
	            FXMLLoader loadAllExercises = new FXMLLoader(AllExcercisesController.class.getResource("AllExercises.fxml"));
	            Parent root =  loadAllExercises.load();
	            Stage allExercises = new Stage();
	            allExercises.setTitle("Alle �bungen".toUpperCase());
	            allExercises.setScene(new Scene(root, 700,650));
	            allExercises.setResizable(true);
	            allExercises.show();
	            allExercises.centerOnScreen();

	            
	        }catch (IOException iOException) {

	            System.out.println(iOException.getMessage());
	        }
	    }
	 
	 
}
package database;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import gui.ConnectionToDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class AllExcersisesList {


	public static ObservableList<TestExcersises> getAllExcersiesList() {
		ObservableList<TestExcersises> exerciseList = FXCollections.observableArrayList();
		try {
		ConnectionToDB db = new ConnectionToDB();
		db.initialize();
		Statement stm;
		stm = db.connection.createStatement();
		ResultSet rst;
		rst = stm.executeQuery("SELECT * FROM exercises");
		
		while(rst.next()) {
			TestExcersises exercises = new TestExcersises();
			exercises.setIdExercise(rst.getInt("id_exercise"));
			exercises.setIdGroup(rst.getInt("id_group"));
			exercises.setTitle(rst.getString("titel"));
			exercises.setDescription(rst.getString("description"));
			exerciseList.add(exercises);
		}
		exerciseList.forEach(TestExcersises::getTitle);
		} catch (SQLException test) {
			test.printStackTrace();
		}
		return exerciseList;
	}
}

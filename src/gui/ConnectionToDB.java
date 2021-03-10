package gui;

import java.sql.*;

public class ConnectionToDB {
    public Connection connection;

    public void initialize() {
        try {

            String dbUrl = "jdbc:mysql://localhost:3306/training_plan_generator?autoReconnect=true&serverTimezone=UTC";
            String dbUsername = "root";
            String dbPassword = "admin";
            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

        }
        catch(SQLException sqlEx) {
            System.out.println(sqlEx.getMessage());
        }
    }

}

package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbConnection {
	private static DbConnection instance = null;
    private Connection connection;
    
    private DbConnection() {
    	connectToDb();
    }
    
    private void connectToDb() {
    	String url = "jdbc:mysql://localhost:3306/mysticgrilldb";
        String user = "root";
        String password = "";
        try {
			this.connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			throw new RuntimeException("Error connecting to the database", e);
		}
    }
    public static DbConnection getInstance() {
        if (instance == null) {
            synchronized (DbConnection.class) {
                if (instance == null) {
                    instance = new DbConnection();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connectToDb();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking connection status", e);
        }
        return connection;
    }
}

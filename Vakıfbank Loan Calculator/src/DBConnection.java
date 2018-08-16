import java.sql.*;


public class DBConnection {
	
public static Connection dbConnector() {
		
		try {
			
			String driver = "com.mysql.cj.jdbc.Driver";
			String URL = "jdbc:mysql://127.0.0.1:3306/vakifbank";
			String username = "root";
			String password = "1994elif";
			Class.forName(driver);
			
			Connection connect = DriverManager.getConnection(URL, username, password);
			System.out.println("Succeeded DB Connection.");
			return connect;
		} catch (Exception e) {
			System.out.println(e);
		} 
		
		return null;
	}

}

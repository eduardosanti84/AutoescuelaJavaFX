package dad.autoescuela.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDconnection {

	private static Connection conn; 
	private static final String URL = "jdbc:mysql://localhost:3306/autoescuelajavafx";  
	private static final String USER = "root";
	private static final String PASS = "";
	
	public static Connection connect() throws SQLException{  

		try{  
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();  
			
		}catch(ClassNotFoundException | IllegalAccessException | InstantiationException e){  
			System.err.println("Error: "+e.getMessage());  
		}
		
		conn = DriverManager.getConnection(URL, USER, PASS);  
		return conn;  
	}  
   
	public static Connection getConnection() throws SQLException, ClassNotFoundException{  
		
		if(conn !=null && !conn.isClosed())  
			return conn;
		
		connect();  
		return conn;  
	}  
}

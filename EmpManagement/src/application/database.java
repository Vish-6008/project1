package application;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class database {
	
	public static Connection connectDb() {
		try {
			Class.forName("org.postgresql.Driver");
			
			Connection connect=DriverManager.getConnection("jdbc:postgresql://localhost:5433/employee","postgres","Vishwa123");
			return connect;
		}catch(Exception e) 
		{
			e.printStackTrace();
			
		
		}
		return null;
		
	}

}

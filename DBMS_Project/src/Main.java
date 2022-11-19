import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Main {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://dbms-1102.chvhqliqi07d.ap-northeast-1.rds.amazonaws.com:3306/Bookstore?useSSL false&allowPublicKeyRetrieval true&serverTimezone =";
	//userid&password
	static final String USER = "admin";
	static final String PASS = "DBMSGroup2";
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Statement statement = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
			System.out.println("Can't find driver");
			e.printStackTrace();
		}		
		  try {
			  Connection conn =  DriverManager.getConnection(DB_URL, USER, PASS);
			  System.out.println("mysql Connection Success");
			  statement= conn.createStatement();  
			  new HomeFrame(statement);
		  } catch (SQLException e) {
			  e.printStackTrace();
		  }
		
	}

}

package worldpeace;
import java.sql.*;

public class ConnectSQL{

	Connection conn;
	String url;
    String dbName;
    String driver;
    String userName;
	String password;

	ConnectSQL(){
		conn = null;
                url = "jdbc:mysql://localhost:3306/";
                dbName = "immigration";
                driver = "com.mysql.jdbc.Driver";
                userName = "root";
                password = "Xy891204";
	}

	public void setConn(){
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url+dbName,userName,password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConn(){
		return conn;
	}

	public void closeConnection(){
		try {
                        conn.close();
                } catch (Exception e) {
                        e.printStackTrace();   // what does printStackTrace mean???
                }
	}

 	public static void main(String[] args) {
        
		System.out.println("MySQL Connect Example.");
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "immigration";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "root"; 
		String password = "Xy891204";
		
		try {
			Class.forName(driver).newInstance();
			
			System.out.println("COME ON!");
			conn = DriverManager.getConnection(url+dbName,userName,password); // Does not call getConn()?
			System.out.println("Connected to the database");
			conn.close();
			System.out.println("Disconnected from database");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

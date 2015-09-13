package worldpeace;
import java.sql.*;
import java.util.ArrayList;
import java.util.*;

public class Login {
        public String isValidUser(ArrayList<String> items)
        {
                int UserID = -1 ;
                 System.out.println("isValid USER");
				 // Try catch statement.
                 try {
                         ConnectSQL connect = new ConnectSQL();
                         connect.setConn();
                        // the mysql insert statement
                        // get User ID
                        String search_query = "select UserID from User where UserName=\""+items.get(1)+"\" and Password=\""+items.get(2)+"\"";
                        Statement stmt = connect.getConn().createStatement();
                        ResultSet rs;
                        rs = stmt.executeQuery(search_query);
                        if(rs.next())
                                UserID = rs.getInt("UserID");
                        System.out.println("UserID:"+UserID);
                        connect.closeConnection();
                 } catch (Exception e) {
                         e.printStackTrace();
                 }
                return Integer.toString(UserID);
		}

	public static void main(String[] args) {
		ArrayList<String> items = new ArrayList<String>();
		items.add("2");
		items.add("tao");
		items.add("1234567");
		Login test = new Login();
		System.out.println(test.isValidUser(items));
	}	

}

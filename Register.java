package worldpeace;
import java.sql.*;
import java.util.ArrayList;
import java.util.*;

public class Register {
        public String insertToUser(ArrayList<String> items)
        {
                 try {
                        ConnectSQL connect = new ConnectSQL();
                        connect.setConn();
                        // the mysql insert statement
                        //String insert_query = "INSERT INTO User (UserName, Password, Gender, Email, Phone, Living_years, English_level, Chinese_level) VALUES (\""+items.get(1)+"\", \""+items.get(2)+"\",\""+items.get(3)+"\", \""+items.get(4)+"\",\""+items.get(5)+"\"," +items.get(6)+", "+items.get(7)+", "+items.get(8)+")\"";
                        /*insert data to User table*/
			String insert_query = "INSERT INTO User (UserName, Password, Gender, Email, Phone, Living_years, English_level, Chinese_level) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStmt = connect.getConn().prepareStatement(insert_query);
			preparedStmt.setString (1, items.get(1));
			preparedStmt.setString (2, items.get(2));
			preparedStmt.setString (3, items.get(3));
			preparedStmt.setString (4, items.get(4));
			preparedStmt.setString (5, items.get(5));
			preparedStmt.setDouble (6, Double.parseDouble(items.get(6)));
			preparedStmt.setInt (7, Integer.parseInt(items.get(7)));
			preparedStmt.setInt (8, Integer.parseInt(items.get(8)));
			preparedStmt.execute();
			/*
			Statement stmt = connect.getConn().createStatement();
                        stmt.execute(insert_query);
                        */
			/*insert data to Tag_User*/
			String search_query = "select UserID from User where UserName=\""+items.get(1)+"\" and Password=\""+items.get(2)+"\"";
                        Statement stmt = connect.getConn().createStatement();
                        ResultSet rs;
			int UserID = -1;
                        rs = stmt.executeQuery(search_query);
                        if(rs.next())
                                UserID = rs.getInt("UserID");

			for(int i = 9;i<items.size();i++)
			{
				String temp_query = "INSERT INTO Tag_User (TagID, UserID, Times_help, Times_vote) values(?, ?, ?, ?)";
				PreparedStatement temp_Stmt = connect.getConn().prepareStatement(temp_query);
				temp_Stmt.setInt (1, Integer.parseInt(items.get(i)));
				temp_Stmt.setInt (2, UserID);
				temp_Stmt.setInt (3, 0);
				temp_Stmt.setInt (4, 0);
				temp_Stmt.execute();
			}
			connect.closeConnection();
                 } catch (Exception e) {
                         e.printStackTrace();
                 }
                return Integer.toString(1);
        }

	public static void main(String[] args) {
		ArrayList<String> items = new ArrayList<String>();
		items.add("1");
		items.add("piano");
		items.add("123456");
		items.add("male");
		items.add("piano@auburn.edu");
		items.add("134567890");
		items.add("3.5");
		items.add("2");
		items.add("3");
		items.add("5");
		items.add("1");
		items.add("6");
		//1, oceanperch, auburn999, male, dzd4325@auburn.edu, 3343335691, 3.5, 2, 2, 3, 5, 6	
		Register test = new Register();
		System.out.println(test.insertToUser(items));
	}	

}

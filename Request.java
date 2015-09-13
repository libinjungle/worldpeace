package worldpeace;
import java.sql.*;
import java.util.ArrayList;
import java.util.*;
import java.lang.Math.*;

public class Request {
	

	/*calculate distance*/

	double EARTH_RADIUS;
	Request() {
		EARTH_RADIUS = 6378.137;
	}
	public double rad(double d)
	{
	    return d * Math.PI / 180.0;
	}

	public double GetDistance(double lat1, double lng1, double lat2, double lng2)
	{
    		double radLat1 = rad(lat1);
    		double radLat2 = rad(lat2);
    		double a = radLat1 - radLat2;
    		double b = rad(lng1) - rad(lng2);
    		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
    		s = s * EARTH_RADIUS;
    		s = Math.round(s * 10000) / 10000;
    		return s;
	}
	public void QuickSort(int s, int e, ArrayList<Double> weight, ArrayList<Integer> UserID) {
		int m = (s+e)/2;
		double pivot = weight.get(m);
		int i = s, j = e;
		while(i<=j) {
			while(weight.get(i) < pivot)
				i++;
			while(weight.get(j) > pivot)
				j--;

			if(i<=j)
			{
				double temp = weight.get(i);
				weight.set(i, weight.get(j));
				weight.set(j, temp);
				int temp_int = UserID.get(i);
				UserID.set(i, UserID.get(j));
				UserID.set(j, temp_int);
				i++;
				j--;
			}
			if(i<e)
				QuickSort(i, e, weight, UserID);
			if(s<j)
				QuickSort(s, j, weight, UserID);
		}
	}
	/*result string fomat: request content, UserName, Phone, LocationX, LocationY, ...*/
        public String findHelp(ArrayList<String> items)
        {
		String result = ""+items.get(1);
        ArrayList<Integer> validUserID = new ArrayList<Integer>(); 
		ArrayList<Integer> neighborUserID = new ArrayList<Integer>();
		ArrayList<Double> neighborWeight = new ArrayList<Double>();
		/* Based on the TagID provided by the user who needs help, all other valid users are retrieved from the database and then
		 * stored in validUserID. In validUserID list, all meaningful neigbors are located by lat and lng and stored in neighborUserID.
		 * Calculate the weight of all neighbors. Sort them and get the first 5 neighbors that are the closest to the user_needs_help. Return them.
		 */
			try {
                        ConnectSQL connect = new ConnectSQL();
                        connect.setConn();
                        // get User ID
                        String search_query = "select UserID from Tag_User where TagID="+items.get(2);
                        Statement stmt = connect.getConn().createStatement();
                        ResultSet rs;
                        rs = stmt.executeQuery(search_query);
                        while(rs.next())
                                validUserID.add(rs.getInt("UserID"));
                        //for(int i=0;i<validUserID.size();i++)
			//	System.out.println(validUserID.get(i));
                        /*filter any points which are too far away from current target point, right now the threshold is set to 7 km*/
                        for(int i=0;i<validUserID.size();i++) {
				String temp_query = "select LocationX, LocationY from Location where UserID="+validUserID.get(i);
				Statement temp_stmt = connect.getConn().createStatement();
				ResultSet temp_rs = temp_stmt.executeQuery(temp_query);
				temp_rs.next();
				if(GetDistance(Double.parseDouble(items.get(3)),Double.parseDouble(items.get(4)), temp_rs.getDouble("LocationX"), temp_rs.getDouble("LocationY"))<=1000000.0) {
					neighborUserID.add(validUserID.get(i));
				}
			}
                        for(int i=0;i<neighborUserID.size();i++) {
				String temp_query = "select English_level, Living_years, Times_help, Times_vote from User u, Tag_User tu where u.UserID = tu.UserID and u.UserID = "+neighborUserID.get(i);
                                Statement temp_stmt = connect.getConn().createStatement();
                                ResultSet temp_rs = temp_stmt.executeQuery(temp_query);
                                temp_rs.next();
				CalWeight temp = new CalWeight(neighborUserID.get(i),temp_rs.getInt("English_level"),temp_rs.getDouble("Living_years"),temp_rs.getInt("Times_help"),temp_rs.getInt("Times_vote"));
				neighborWeight.add(temp.getWeight());
				System.out.println(temp.getWeight()+"	"+neighborUserID.get(i));
			}
			/*quick sort*/
			QuickSort(0, neighborUserID.size()-1, neighborWeight, neighborUserID);
			/*choose the first five closet user*/
			for(int i=0;i<5&&i<neighborUserID.size();i++)
			{
				String temp_query = " select UserName, Phone, LocationX, LocationY from User u, Location l where u.UserID = l.UserID and u.UserID ="+neighborUserID.get(i);
                                Statement temp_stmt = connect.getConn().createStatement();
                                ResultSet temp_rs = temp_stmt.executeQuery(temp_query);
                                temp_rs.next();
				result = result+","+temp_rs.getString("UserName")+","+temp_rs.getString("Phone")+","+temp_rs.getDouble("LocationX")+","+temp_rs.getDouble("LocationY");
			}
			connect.closeConnection();
                 } catch (Exception e) {
                         e.printStackTrace();
                 }
		System.out.println(result);
                return result;
        }
	/*3, my car is towed away. I need help, 0, 58.23, 171.24, 2*/
	public static void main(String[] args) {
		ArrayList<String> items = new ArrayList<String>();
		items.add("3");
		items.add("my car is towed away");
		items.add("1");
		items.add("200.001");
		items.add("200.001");
		items.add("2");
		Request test1 = new Request();
		test1.findHelp(items);

		/*QuickSort*/
		/*
		ArrayList<Double> test = new ArrayList<Double>();
		test.add(1.2);
		test.add(0.6);
		test.add(0.1);
		test.add(100.6);
		test.add(7.6);
		test.add(0.12);

		test1.QuickSort(0, test.size()-1, test);
		double[] array = new double[100];
		for(int i=0;i<test.size();i++)
			array[i] = test.get(i);
		//test1.QuickSort(0, test.size()-1, array);


		for(int i = 0;i<test.size();i++)
			System.out.println(test.get(i));		
		*/

	}	

}

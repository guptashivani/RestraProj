package SolutionREST.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtils {
	
	private final static String URL="jdbc:mysql://localhost:3306/emp_db";
	private final static String USER="root";
	private final static String PASSWORD="";
	
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
public static Connection connectToDB(){
	Connection con=null;
	try {
		
		con=DriverManager.getConnection(URL, USER, PASSWORD);
		System.out.println("Connection established");
		System.out.println(con);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("Error in establishing connection"+e.getMessage());
	}
	
	
	return con;
}

public static void closeResources(PreparedStatement ps, Connection con, ResultSet rs){
	
	try {
		if(ps!=null)
		ps.close();
	
	
		if(rs!=null)
		rs.close();
	
	
		if(con!=null)
		con.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public static void main(String args[])
{
	DBUtils.connectToDB();
	}
}

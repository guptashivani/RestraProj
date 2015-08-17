package SolutionApp.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import SolutionApp.Exception.AppException;
import SolutionApp.model.Auth;
import SolutionApp.model.Employee;
import SolutionREST.Utils.DBUtils;

public class RecordDAO {
	
	public List<Employee> getAll()throws AppException
		{
			List<Employee> empList=new ArrayList<Employee>();
			
			Connection con=DBUtils.connectToDB();
			PreparedStatement ps=null;
			ResultSet rs=null;
			
			try {
				ps=con.prepareStatement("SELECT * from emp_db.employee");
				
				rs=ps.executeQuery();
				
				//printing db rows
				
				while(rs.next())
				{
					Employee emp=new Employee();
					emp.setCC(rs.getInt("CC"));
					emp.setEmail(rs.getString("EMAIL"));
					emp.setName(rs.getString("NAME"));
					emp.setPhone(rs.getString("PHONE"));
					
					empList.add(emp);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new AppException("Error in fetching records from db",e.getCause());
			}
			finally{
				DBUtils.closeResources(ps, con, rs);
			}
			
			return empList;
		}

	public Employee getReservation(int code)throws AppException
	{
		Employee emp=new Employee();
		
		Connection con=DBUtils.connectToDB();
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			ps=con.prepareStatement("SELECT * from emp_db.employee WHERE CC=?");
			ps.setInt(1,code);
			
			rs=ps.executeQuery();
			
			//printing db rows
			
			if(rs.next())
			{
				//Employee emp=new Employee();
				emp.setCC(rs.getInt("CC"));
				emp.setEmail(rs.getString("EMAIL"));
				emp.setName(rs.getString("NAME"));
				emp.setPhone(rs.getString("PHONE"));
				
				//em.add(emp);
			}
			else{
				throw new AppException("record with code"+code+" does not exist");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppException("Error in fetching records from db",e.getCause());
		}
		finally{
			DBUtils.closeResources(ps, con, rs);
		}
		
		return emp;
	}
	public Employee addRecord(Employee em)throws AppException
	{
				
		Connection con=DBUtils.connectToDB();
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			ps=con.prepareStatement("INSERT INTO emp_db.employee (NAME, EMAIL,PHONE) VALUES(?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, em.getName());
			ps.setString(2, em.getEmail());
			ps.setString(3, em.getPhone());
			
			ps.executeUpdate();
			
			rs=ps.getGeneratedKeys();
			
			if(rs.next())
			{
				em.setCC(rs.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppException("Error in fetching records from db",e.getCause());
		}
		finally{
			DBUtils.closeResources(ps, con, rs);
		}
		return em;
		
	}
	
	//method to authenticate admin
	/*public boolean authenticate(Auth obj)throws AppException{
		Connection con=DBUtils.connectToDB();
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			ps=con.prepareStatement("SELECT * FROM emp_db.admin",PreparedStatement.RETURN_GENERATED_KEYS);
			ps.executeUpdate();
			
			rs=ps.getGeneratedKeys();
			
			if(rs.next())
			{
				if(obj.getEmail().equals(rs.getString(1)) && obj.getPassword().equals(rs.getString(2)))
				return true;
				else
					return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppException("Error in authentication",e.getCause());
		}
		finally{
			DBUtils.closeResources(ps, con, rs);
		}
		return false;
		
	}*/
}

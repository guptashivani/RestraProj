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
import SolutionApp.model.Record;
import SolutionApp.model.Seating;
import SolutionREST.Utils.DBUtils;
/*
 * CREATE TABLE `emp_db`.`reservation` (
  `name` VARCHAR(45) NULL ,
  `table_id` INT NULL ,
  `size` INT NULL ,
  `email` VARCHAR(45) NULL ,
  `phone` VARCHAR(45) NULL,
  `date` VARCHAR(45) NULL ,
  `time` VARCHAR(45) NULL ,
  `code` INT NOT NULL AUTO_INCREMENT ,
  `status` VARCHAR(45) NULL,
  PRIMARY KEY (`code`)  );
 * */
public class EmployeeDAO {
	
	public List<Record> getAll()throws AppException
		{
			List<Record> resList=new ArrayList<Record>();
			
			Connection con=DBUtils.connectToDB();
			PreparedStatement ps=null;
			ResultSet rs=null;
			
			try {
				ps=con.prepareStatement("SELECT * from emp_db.reservation");
				
				rs=ps.executeQuery();
				
				//printing db rows
				
				while(rs.next())
				{
					Record rec=new Record();
					rec.setCode(rs.getInt("code"));
					rec.setDate(rs.getString("date"));
					rec.setEmail(rs.getString("email"));
					rec.setPhone(rs.getString("phone"));
					rec.setSize(rs.getInt("size"));
					rec.setTime(rs.getString("time"));
					rec.setName(rs.getString("name"));
					rec.setStatus(rs.getString("status"));
					rec.setTable_id(rs.getInt("table_id"));
					
					
					resList.add(rec);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new AppException("Error in fetching records from db",e.getCause());
			}
			finally{
				DBUtils.closeResources(ps, con, rs);
			}
			
			return resList;
		}
	public List<Seating> getTables()throws AppException
	{
		List<Seating> tabList=new ArrayList<Seating>();
		
		Connection con=DBUtils.connectToDB();
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			ps=con.prepareStatement("SELECT * from emp_db.seating");
			
			rs=ps.executeQuery();
			
			//printing db rows
			
			while(rs.next())
			{
				Seating rec=new Seating();
				
				rec.setTable_id(rs.getInt("table_id"));
				rec.setSize(rs.getInt("size"));
				
				
				tabList.add(rec);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppException("Error in fetching records from db",e.getCause());
		}
		finally{
			DBUtils.closeResources(ps, con, rs);
		}
		
		return tabList;
	}

	public Record getReservation(int code)throws AppException
	{
		
		Record rec=new Record();
		
		Connection con=DBUtils.connectToDB();
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			ps=con.prepareStatement("SELECT * from emp_db.reservation WHERE code=?");
			ps.setInt(1,code);
			
			rs=ps.executeQuery();
			
			//printing db rows
			
			if(rs.next())
			{
				rec.setName(rs.getString("name"));
				rec.setCode(rs.getInt("code"));
				rec.setSize(rs.getInt("size"));
				rec.setDate(rs.getString("date"));
				rec.setEmail(rs.getString("email"));
				rec.setPhone(rs.getString("phone"));
				rec.setTime(rs.getString("time"));
				rec.setStatus(rs.getString("status"));
				
				
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
		
		return rec;
	}
	//method to cancel an existing reservation
	public boolean cancelReservation(int code)throws AppException
	{
		//Employee emp=new Employee();
		//Record rec=new Record();
		
		Connection con=DBUtils.connectToDB();
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			ps=con.prepareStatement("DELETE FROM emp_db.reservation WHERE code=?");
			ps.setInt(1,code);
			
			ps.executeUpdate();
			
			//printing db rows
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppException("Error in fetching records from db",e.getCause());
		}
		finally{
			DBUtils.closeResources(ps, con, rs);
		}
		
		return true;
	}
	
	//method to edit an existing reservation
		public boolean editReservation(Record rec)throws AppException
		{
			//Employee emp=new Employee();
			//Record rec=new Record();
			
			Connection con=DBUtils.connectToDB();
			PreparedStatement ps=null;
			ResultSet rs=null;
			
			try {
				ps=con.prepareStatement("UPDATE emp_db.reservation SET name=?,size=?,email=?,phone=?,date=?,time=? WHERE code=?");
				ps.setString(1,rec.getName());
				ps.setInt(2, rec.getSize());
				ps.setString(3, rec.getEmail());
				ps.setString(4, rec.getPhone());
				ps.setString(5, rec.getDate());
				ps.setString(6, rec.getTime());
				
				ps.executeUpdate();
				
				//printing db rows
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new AppException("Error in fetching records from db",e.getCause());
			}
			finally{
				DBUtils.closeResources(ps, con, rs);
			}
			
			return true;
		}
	
	public Record addRecord(Record rec)throws AppException
	{
				
		Connection con=DBUtils.connectToDB();
		PreparedStatement ps1=null;
		ResultSet rs=null;
		PreparedStatement ps2=null;
		
		
		try {
			ps1=con.prepareStatement("SELECT table_id FROM emp_db.seating WHERE size=?");
			ps1.setInt(1, rec.getSize());
			rs=ps1.executeQuery();
			
			System.out.println("first query executed:answer: "+rs.next());
			System.out.println("first query executed:answer: "+rs.getInt("table_id"));
			int val=rs.getInt("table_id");
			String s= "confirmed";
			rec.setTable_id(val);
			ps2=con.prepareStatement("INSERT IGNORE INTO emp_db.reservation (name,table_id,size,email,phone,date,time,status) VALUES(?,?,?,?,?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
			ps2.setString(1, rec.getName());
			System.out.println("1");
			ps2.setInt(2, val);
			System.out.println("2");
			ps2.setInt(3, rec.getSize());
			ps2.setString(4, rec.getEmail());
			System.out.println("3");
			ps2.setString(5, rec.getPhone());
			System.out.println("4");
			ps2.setString(6, rec.getDate());
			System.out.println("5");
			ps2.setString(7, rec.getTime());
			ps2.setString(8,s);
			
			if(ps2.executeUpdate()!=0){
				rs=ps2.getGeneratedKeys();
				System.out.println("statement2 executed:reservation confirmed");
				if(rs.next())
				{
					rec.setCode(rs.getInt(1));
					rec.setStatus(s);
					System.out.println("code is:"+rec.getCode());
					System.out.println(rec.getStatus());
				}
			}
			//
			
			else{
				System.out.println("first query 2 executed:answer: ");
				ps2=con.prepareStatement("INSERT IGNORE INTO emp_db.reservation (name,table_id,size,email,phone,date,time,status) VALUES(?,?,?,?,?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
				ps2.setString(1, rec.getName());
				System.out.println("1");
				ps2.setInt(2, rs.getInt("table_id"));
				System.out.println("2");
				ps2.setInt(3, rec.getSize());
				ps2.setString(4, rec.getEmail());
				System.out.println("3");
				ps2.setString(5, rec.getPhone());
				System.out.println("4");
				ps2.setString(6, rec.getDate());
				System.out.println("5");
				ps2.setString(7, rec.getTime());
				ps2.setString(8, "waiting");
				
				ps2.executeUpdate();
				//System.out.println("statement executed");
				rs=ps2.getGeneratedKeys();
				if(rs.next())
				{
					rec.setCode(rs.getInt(1));
					 rec.setStatus("waiting");
					System.out.println(rec.getStatus());
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppException("Error in inserting records in db",e.getCause());
		}
		finally{
			DBUtils.closeResources(ps1, con, rs);
		}
		return rec;
		
	}
	
	//method to confirm the reservation by admin
	//method to edit an existing reservation
	public boolean confirmReservation(Auth obj)throws AppException
	{
		//Employee emp=new Employee();
		//Record rec=new Record();
		
		Connection con=DBUtils.connectToDB();
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			System.out.println("trying to update sql query");
			ps=con.prepareStatement("UPDATE emp_db.reservation SET table_id=?,status=? WHERE code=?");
			ps.setInt(1,obj.getTable_id());
			ps.setString(2, "waiting");
			ps.setInt(3, obj.getCode());
			
			
			ps.executeUpdate();
			
			//printing db rows
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppException("Error in fetching records from db",e.getCause());
		}
		finally{
			DBUtils.closeResources(ps, con, rs);
		}
		
		return true;
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

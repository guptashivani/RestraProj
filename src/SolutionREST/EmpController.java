package SolutionREST;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import SolutionApp.Dao.EmployeeDAO;
import SolutionApp.Exception.AppException;
import SolutionApp.Rest.AppResponse;
import SolutionApp.model.Auth;
import SolutionApp.model.Employee;
import SolutionApp.model.Record;
import SolutionApp.model.Seating;

@Path("/employee")
public class EmpController {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all")
	public AppResponse getAll(){
		
		AppResponse resp=new AppResponse();
		
		try {
			EmployeeDAO dao=new EmployeeDAO();
			List<Record> empList = dao.getAll();
			//resp.setStatus("success");
			resp.setPayload(empList);
			
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.setStat(AppResponse.ERROR);
			resp.setMessage(e.getMessage());
			
		}
		return resp;
		//return empList;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/tables")
	public AppResponse getTables(){
		
		AppResponse resp=new AppResponse();
		
		try {
			EmployeeDAO dao=new EmployeeDAO();
			List<Seating> tabList = dao.getTables();
			//resp.setStatus("success");
			resp.setPayload(tabList);
			
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.setStat(AppResponse.ERROR);
			resp.setMessage(e.getMessage());
			
		}
		return resp;
		//return empList;
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/get/{CC}")
	public AppResponse getEmployee(@PathParam("CC") int code){
		
		AppResponse resp=new AppResponse();
		
		try {
			EmployeeDAO dao=new EmployeeDAO();
			Record emp = dao.getReservation(code);
			//resp.setStatus("success");
			resp.setPayload(emp);
			System.out.println(emp.getStatus());
			System.out.println(resp.getPayload());
			
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.setStat(AppResponse.ERROR);
			resp.setMessage(e.getMessage());
			
		}
		return resp;
		//return empList;
	}
	
	
	@DELETE
	
	@Path("/cancel/{CC}")
	public AppResponse cancelRecord(@PathParam("CC") int code){
		
		AppResponse resp=new AppResponse();
		
		try {
			EmployeeDAO dao=new EmployeeDAO();
			boolean val = dao.cancelReservation(code);
			System.out.println("Record deleted");
			//resp.setStatus("success");
			resp.setPayload(val);
			
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.setStat(AppResponse.ERROR);
			resp.setMessage(e.getMessage());
			
		}
		return resp;
		//return empList;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/book/{CC}")
	public AppResponse bookTable(@PathParam("CC") int code){
		AppResponse resp=new AppResponse();
		
		try {
			EmployeeDAO dao=new EmployeeDAO();
			Record rec=dao.getReservation(code);
			boolean val = dao.editReservation(rec);
			resp.setStat("Reservation has been successfully made!");
			resp.setPayload(code);
			
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.setStat(AppResponse.ERROR);
			resp.setMessage(e.getMessage());
			
		}
		return resp;
	}
	
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/edit/{CC}")
	public AppResponse addRecord(@PathParam("CC") int code){
		AppResponse resp=new AppResponse();
		
		try {
			EmployeeDAO dao=new EmployeeDAO();
			Record rec=dao.getReservation(code);
			boolean val = dao.editReservation(rec);
			resp.setStat("Reservation has been successfully made!");
			resp.setPayload(code);
			
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.setStat(AppResponse.ERROR);
			resp.setMessage(e.getMessage());
			
		}
		return resp;
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/add")
	public AppResponse addRecord(Record rec){
		AppResponse resp=new AppResponse();
		
		try {
			EmployeeDAO dao=new EmployeeDAO();
			Record c = dao.addRecord(rec);
			resp.setStat("Reservation has been successfully made!");
			System.out.println(c.getStatus());
			resp.setPayload(c);
			
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.setStat(AppResponse.ERROR);
			resp.setMessage(e.getMessage());
			
		}
		return resp;
	}
	
	//method to confirm a reservation by admin
	//create another class for admin controller
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/confirm")
	public AppResponse login(Auth auth){
		AppResponse resp=new AppResponse();
		System.out.println("in confirmation method");
		try {
			EmployeeDAO dao=new EmployeeDAO();
			boolean added=dao.confirmReservation(auth);
			System.out.println("done!");
			//session maintenance
			
			if(added){
			resp.setMessage(" successful!");
			}
			else{
				resp.setMessage(" Unsuccessful");
				resp.setStat(AppResponse.ERROR);
			}
			//resp.setPayload();
			
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.setStat(AppResponse.ERROR);
			resp.setMessage(e.getMessage());
			
		}
		return resp;
	}
	
	
	//create another class for admin controller
	/*@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/login")
	public AppResponse login(Auth auth,@Context HttpServletRequest request){
		AppResponse resp=new AppResponse();
		
		try {
			EmployeeDAO dao=new EmployeeDAO();
			boolean authenticated=dao.authenticate(auth);
			//session maintenance
			HttpSession session=request.getSession(true);
			session.setAttribute("UESR", auth);
			if(authenticated){
			resp.setMessage("Authentication successful!");
			}
			else{
				resp.setMessage("Authentication Unsuccessful");
				resp.setStat(AppResponse.ERROR);
			}
			//resp.setPayload();
			
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.setStat(AppResponse.ERROR);
			resp.setMessage(e.getMessage());
			
		}
		return resp;
	}*/
	
//for checking login
	@GET
	@Path("/checkLogin")
	public AppResponse checkLogin(@Context HttpServletRequest request){
		AppResponse resp=new AppResponse();
		
		
			
			HttpSession session=request.getSession(false);
			//session.setAttribute("UESR", auth);
			if(session!=null && session.getAttribute("USER")!=null)
			{
			resp.setMessage("Session is running;User is already logged in!");
			}
			else{
				resp.setMessage("No user logged in!");
				resp.setStat(AppResponse.ERROR);
			//resp.setPayload();
			}
		return resp;
	}
}

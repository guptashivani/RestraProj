package SolutionApp.Rest;

public class AppResponse {
	
	public static final String ERROR="error";
	private String stat;
	private String message;
	private Object payload;
	
	public AppResponse(){
		this.stat="Success";
	}
	
	public String getStat() {
		return stat;
	}
	public void setStat(String status) {
		this.stat = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getPayload() {
		return payload;
	}
	public void setPayload(Object payload) {
		this.payload = payload;
	}
	
	

}

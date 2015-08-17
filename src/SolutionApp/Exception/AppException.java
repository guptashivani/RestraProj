package SolutionApp.Exception;

public class AppException extends Exception{
	
	private static final long serialVersionUID = 3392127323172716354L;

	public AppException(String msg){
		
		super(msg);
	}
	
	public AppException(String msg,Throwable cause){
		
		super(msg,cause);
	}

}

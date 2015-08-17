package SolutionApp;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class RESTAppConfig extends ResourceConfig{
	
	public RESTAppConfig(){
		
		packages("SolutionApp");
	}

}

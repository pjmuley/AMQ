import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.UUID;

import javax.ws.rs.core.MultivaluedMap;




public class TestAPI {
	public static void main(String[] args){
	try {
		 
		String client_id="#";
		String client_secret="#";
		String queueName="myQueue";
		String queueMessage="This is my message 10";
		String authURI="https://mq-us-west-2.anypoint.mulesoft.com/api/v1/authorize";
		String brokerURI="https://mq-us-west-2.anypoint.mulesoft.com/api/v1/organizations/";
	
		// Get access token required for publish/subscribe API's
		
		Client client = Client.create();
		
		WebResource webResource = client.resource(authURI);
		String input = "client_id="+client_id+"&client_secret="+client_secret+"&grant_type=client_credentials";		
		ClientResponse response = webResource.header("Content-Type", "application/x-www-form-urlencoded")
											 .accept("application/json")
											 .post(ClientResponse.class, input);
		
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		String authResponse = response.getEntity(String.class);
		System.out.println("\n============Platform Reponse============");
		System.out.println(authResponse);
		
		// Retrieve access token, organization id & environment id from the response
		JSONParser jsonParser=new JSONParser();
		JSONObject jsonObject= (JSONObject) jsonParser.parse(authResponse);
		String access_token=(String)jsonObject.get("access_token");
		
		JSONObject structure = (JSONObject) jsonObject.get("simple_client");
		String orgId=(String)structure.get("orgId");
		String envId=(String)structure.get("envId");
		System.out.println("Access Token:"+access_token);
		System.out.println("Organization Id:"+orgId);
		System.out.println("EnvironmentId:"+envId);
		
		

	} catch (Exception e) {
		e.printStackTrace();
	}
	}
}

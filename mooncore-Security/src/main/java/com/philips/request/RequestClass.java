package com.philips.request;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.apache.http.client.methods.HttpRequestBase;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.philips.restservices.GenericObjectMapper;
import com.philips.restservices.JsonPayloadCreator;
import com.philips.restservices.MdsLoginResponse;
import com.philips.restservices.ResponseObject;
import com.philips.restservices.RestService;
import com.philips.utilities.TestUtil;
import com.philips.utilities.Xls_Reader;
import com.philips.utilities.dataConstants;

public class RequestClass {
	
	public RestService restService = new RestService();
	static ResponseObject Loginresponse;
	static ResponseObject postreposnse;
	static ResponseObject delResponse;
	public static MdsLoginResponse user;
	private static Xls_Reader MUS_xls;
	private int requestCount = 0;
	
	private ArrayList<String> fuzzingfull;
	private ArrayList<String> HTTPVerb;
	
	private static RequestClass requestShared = new RequestClass( );

	   /* A private Constructor prevents any other
	    * class from instantiating.
	    */
	   private RequestClass() { }

	   /* Static 'instance' method */
	   public static RequestClass getInstance( ) {
	      return requestShared;
	   }
	   
	   @BeforeClass()
		public void configure() {
			//summaryLogger.createFile(logFile);
		   MUS_xls = new Xls_Reader(System.getProperty("user.dir") + dataConstants.MUS_excel);
			//cardHelper = new HH_CardsHelper(calorieIntakeDataSheet);
		   fuzzingfull = new ArrayList<String>();
		  // fuzzingfull.add("!");
		  // fuzzingfull.add("!!");
		   //fuzzingfull.add("-");
		   
		   HTTPVerb = new ArrayList<String>();
		  
		   
		   HTTPVerb.add("GET");
		   HTTPVerb.add("POST");
		   HTTPVerb.add("PUT");
		   HTTPVerb.add("PATCH");
		   HTTPVerb.add("DELETE");
		   
		   //request = new ArrayList<HttpRequestBase>();
		  // fuzzingfull.add("!=!");
		}
	   
	   @DataProvider
		public Object[][] getLoginDetails() {
			return TestUtil.getData("Login", MUS_xls);
		}
	   
	   @DataProvider
		public Object[][] getprofileDetails() {
			return TestUtil.getData("Profile", MUS_xls);
		}
	   
	   @DataProvider
			public Object[][] getlogoutDetails() {
				return TestUtil.getData("Logout", MUS_xls);
			}
	   
	   @DataProvider
		public Object[][] getchangePassword() {
			return TestUtil.getData("ChangePassword", MUS_xls);
		}
	   
	   
		public HashMap<String, String> generateHeader(Boolean isPublic){
			HashMap<String, String> headersMap;
			headersMap = new HashMap<String, String>();
			headersMap.put("Content-Type", "application/json");
			headersMap.put("Accept", "application/json");
			if(user != null && user.getAccessToken() != null)
			{
				headersMap.put("Authorization",isPublic?"bearer "+ user.getAccessToken():"signature "+ user.getAccessToken());
			}
			//System.out.println("\nHeader : " + headersMap );
		return headersMap;
	}
	   
		//@Test(dataProvider = "getprofileDetails" ,priority = 1)
		   public ArrayList<HttpRequestBase> configureRequest(Hashtable<String, String> data)throws Exception {  
				
			   ArrayList<HttpRequestBase> request = new ArrayList<HttpRequestBase>(); 

			   try {				
					
					//Payload data
					JSONObject  json = null;
					if(data.containsKey("Payload"))
					{
					JSONParser parser = new JSONParser(); 
					json = (JSONObject) parser.parse(data.get("Payload"));
					}
					//String[] attackparameters = data.get("BodyParameters").split(",");

					//Attack details
					String[] attacktype = data.get("AttackType").split(",");
					String[] attackparts = data.get("AttackParameter").split(",");
					
					for(int attacktypecount = 0 ; attacktypecount < attacktype.length ; attacktypecount++)
					{	
						System.out.println("Attack Type :" + attacktype[attacktypecount]);
						ArrayList<String> attacktypepayload = new ArrayList<String>();
						
						//Read the attack payload from a file (Fuzzing full, SQL, XSS)
						switch (attacktype[attacktypecount]) {
						case "FUZZFULL":
							attacktypepayload = fuzzingfull;
							break;
						case "HTTPVERB":
							attacktypepayload = HTTPVerb;
							break;
						default:
							break;
						}
						
						//if()
						
						//MethodType
						String methodType = data.get("methodType");
						
						for(int attackparamcount = 0 ; attackparamcount < attackparts.length; attackparamcount++)
						{
							//Header Data
							HashMap<String, String> headers = new HashMap<String,String>();
							headers = generateHeader(true);
							
							//Url endpoint
							String url = String.format(data.get("Url"), "mars",user.getUserId());
							System.out.println("change password url " + url);
							
							//Payload
							String payload = "";
							if(data.containsKey("Payload"))
							{
							 payload = data.get("Payload");
							}
							
							System.out.println("attackparameter :" + attackparts[attackparamcount]);

							
							
							
							for (int attacktypepayloadcount = 0 ; attacktypepayloadcount < attacktypepayload.size(); attacktypepayloadcount++)
							{
							
							System.out.println("attackpayload :" + attacktypepayload.get(attacktypepayloadcount));
							
							switch (attackparts[attackparamcount]) {
								case "userid":
								if(user != null && user.getAccessToken() != null)
								{
								 url = String.format(data.get("Url"), "mars", attacktypepayload.get(attacktypepayloadcount));

								}
								break;
								case "auth":
								if(user != null && user.getAccessToken() != null)
								{
									headers.put("Authorization", "bearer " + attacktypepayload.get(attacktypepayloadcount) );
									//System.out.println("Header :" + headers);
								}
								break;
								case "methodType":
									if(attacktype[attacktypecount].equals("HTTPVERB"))
									{
									methodType = attacktypepayload.get(attacktypepayloadcount) ;
									}
									break;
							default:
								break;
							}
							
							if(json != null && json.keySet().contains(attackparts[attackparamcount]))
							{
								JSONObject tempjson= (JSONObject) json.clone();
								tempjson.put(attackparts[attackparamcount],attacktypepayload.get(attacktypepayloadcount));
								//System.out.println("Payload: " + tempjson.toJSONString());
								payload = tempjson.toJSONString();
							}
							
							//Make Request
							System.out.println("******");
							System.out.println("Make Request");
							System.out.println("url: " + url);
							System.out.println("headers: " + headers);
							System.out.println("payload: " + payload);
							request.add(restService.generateRequest(url, headers  , payload, methodType));
							//Loginresponse = restService.sendRequest(url, headers  , payload, data.get("methodType") );
							//System.out.println(Loginresponse.statusCode);
							System.out.println("******");
							
							
							}
						}	
		
					}
					
					/*
					System.out.println("\nStart Hitting");
					System.out.println("Request : "+ request);
					System.out.println("Request Count : "+ request.size());
					for(int requestcount = 0; requestcount < request.size() ; requestcount++)
					{
						ResponseObject respone = restService.doRequest(request.get(requestcount));
						System.out.println("Response:" + respone.statusCode);
					}
					*/
				}catch (Error e) {
					e.getStackTrace();
					
				}
				return request;
				  
			} 
		
	  @Test(dataProvider = "getLoginDetails" ,priority = 1)
	   public void login(Hashtable<String, String> data)throws Exception {  
			try {
				requestCount ++;
				System.out.println("\n******** loginHHGW*******\n");
			// MUS_xls = new Xls_Reader(System.getProperty("user.dir") + dataConstants.MUS_excel);

				// Object [][] data =  TestUtil.getData("Login", MUS_xls);
				//Hashtable<String, String> datamap = new Hashtable<String, String>(data.size());
				//System.out.println(datamap);
				//Object local[] =  data[0];
				//datamap =  (HashMap<String, String>) local[0] ;
				//String headers =datamap.get("Headers"); */
				

	        	//URL propertiesURL = getClass().getResource("submodule.properties");
				//System.out.println(propertiesURL.getPath());

				//prop.loadProperties(propertiesURL.getPath());
				//HashMap<String, String> header = restService.getHeadersMapFromJSONString(headers);
				
				
				HashMap<String, String> headers = new HashMap<String,String>();
				headers = generateHeader(true);
				
				
				String payload = "";
				
				/*String[] attacktype = data.get("AttackType").split(",");
				String[] attackparameter = data.get("AttackParameter").split(",");
				
				for(int attacktypecount = 0 ; attacktypecount < attacktype.length ; attacktypecount++)
				{	
				if(Arrays.asList(attackparameter).contains("HEADER"))
				{
					
				}
				if(Arrays.asList(attackparameter).contains("AUTH"))
				{
				
				}
				if(Arrays.asList(attackparameter).contains("BODY"))
				{
				
						//System.out.println("body" + payload);
				
				}
				*/
				payload = JsonPayloadCreator.createLoginJsonPayload(data.get("username"), data.get("password"));

				System.out.println("Headers" + headers);
				System.out.println("Payload" + payload);
				//Loginresponse = restService.sendPostRequest(String.format(data.get("Url"),"mars"), generateHeader() , payload);
				Loginresponse = restService.sendRequest(String.format(data.get("Url"),"mars"), headers  , payload, data.get("methodType") );

				System.out.println(Loginresponse.statusCode);
				//Assert.assertEquals(Loginresponse.statusCode, 200);
				//summaryLogger.log("IntroAEE_Cards_13_14", count++, "Login to user account", "PASS");

				user = GenericObjectMapper.readJSONToJavaObject(Loginresponse.responseBody, MdsLoginResponse.class);
				System.out.println("UserId :" + user.getUserId());
				
				
//				if(Loginresponse.statusCode==200)
//				{
//					pss_getDetails();
//				}
//				} // For
			} catch (Error | IOException e) {
				e.getStackTrace();
			
			}
			  
		} 
	  
	  
	  @Test(dataProvider = "getprofileDetails" ,priority = 2)
	   public void getProfile(Hashtable<String, String> data)throws Exception {  
			try {
				/*
				requestCount ++;
				System.out.println("\n******** getProfile*******\n");
				
				if(data.get("methodType").equals("POST") || data.get("methodType").equals("PUT") || data.get("methodType").equals("PATCH"))
				{
				String payload = JsonPayloadCreator.createLoginJsonPayload(data.get("username"), data.get("password"));
				System.out.println("body" + payload);
				}
				
				//Loginresponse = restService.sendPostRequest(String.format(data.get("Url"),"mars"), generateHeader() , payload);
				String url = String.format(data.get("Url"), "mars",user.getUserId());
				System.out.println("getProfile url " + url);

				ResponseObject response = restService.sendRequest(url , generateHeader(true) , null , data.get("methodType") );
				System.out.println(response.statusCode);
				*/
				ArrayList<HttpRequestBase> request  = configureRequest(data);
				System.out.println("\nStart Hitting");
				System.out.println("Request : "+ request);
				System.out.println("Request Count : "+ request.size());
				for(int requestcount = 0; requestcount < request.size() ; requestcount++)
				{
					ResponseObject respone = restService.doRequest(request.get(requestcount));
					System.out.println("Response:" + respone.statusCode);
				}
				
				
				//Assert.assertEquals(Loginresponse.statusCode, 200);
				//summaryLogger.log("IntroAEE_Cards_13_14", count++, "Login to user account", "PASS");

				//user = GenericObjectMapper.readJSONToJavaObject(Loginresponse.responseBody, MdsLoginResponse.class);
				//System.out.println("Response Code :" + user.getUserId());
				
				
//				if(Loginresponse.statusCode==200)
//				{
//					pss_getDetails();
//				}
				
			} catch (Error | IOException e) {
				e.getStackTrace();
			
			}
			  
		} 
	  
	  
	  @Test(dataProvider = "getlogoutDetails" ,priority = 3)
	   public void logout(Hashtable<String, String> data)throws Exception {  
			try {
				requestCount ++;
				System.out.println("\n******** logout *******\n");
				
				if(data.get("methodType").equals("POST") || data.get("methodType").equals("PUT") || data.get("methodType").equals("PATCH"))
				{
				String payload = JsonPayloadCreator.createLoginJsonPayload(data.get("username"), data.get("password"));
				System.out.println("body" + payload);
				}
				
				//Loginresponse = restService.sendPostRequest(String.format(data.get("Url"),"mars"), generateHeader() , payload);
				String url = String.format(data.get("Url"), "mars",user.getUserId());
				System.out.println("logout url " + url);

				ResponseObject response = restService.sendRequest(url , generateHeader(true) , null , data.get("methodType") );

				System.out.println(response.statusCode);
				//Assert.assertEquals(Loginresponse.statusCode, 200);
				//summaryLogger.log("IntroAEE_Cards_13_14", count++, "Login to user account", "PASS");

				//user = GenericObjectMapper.readJSONToJavaObject(Loginresponse.responseBody, MdsLoginResponse.class);
				//System.out.println("Response Code :" + user.getUserId());
				
				
//				if(Loginresponse.statusCode==200)
//				{
//					pss_getDetails();
//				}
				
			} catch (Error | IOException e) {
				e.getStackTrace();
			
			}
			  
		} 
	   
	   
}

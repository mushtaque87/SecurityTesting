package com.philips.request;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Hashtable;

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
		}
	   
	   @DataProvider
		public Object[][] getLoginDetails() {
			return TestUtil.getData("Login", MUS_xls);
		}
	   
		public HashMap<String, String> generateHeader(){
			HashMap<String, String> headersMap;
			headersMap = new HashMap<String, String>();
			headersMap.put("Content-Type", "application/json");
			headersMap.put("Accept", "application/json");
			if(user != null && user.getAccessToken() != null)
			{
				headersMap.put("Authorisation", user.getAccessToken());

			}
		return headersMap;
	}
	   
	  @Test(dataProvider = "getLoginDetails" ,priority = 1)
	   public void loginHHGW(Hashtable<String, String> data)throws Exception {  
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
				
				/*
				HashMap<String, String> header = new HashMap<String,String>();
				header.put("content-type", "application/json");
				System.out.println("login_params_1" + header);
				*/
				String payload = JsonPayloadCreator.createLoginJsonPayload(data.get("username"), data.get("password"));
				System.out.println("body" + payload);
				
				
				//Loginresponse = restService.sendPostRequest(String.format(data.get("Url"),"mars"), generateHeader() , payload);
				Loginresponse = restService.sendRequest(String.format(data.get("Url"),"mars"), generateHeader() , payload, data.get("methodType") );

				System.out.println(Loginresponse.statusCode);
				//Assert.assertEquals(Loginresponse.statusCode, 200);
				//summaryLogger.log("IntroAEE_Cards_13_14", count++, "Login to user account", "PASS");

				user = GenericObjectMapper.readJSONToJavaObject(Loginresponse.responseBody, MdsLoginResponse.class);
				System.out.println("UserId :" + user.getUserId());
				
				
//				if(Loginresponse.statusCode==200)
//				{
//					pss_getDetails();
//				}
				
			} catch (Error | IOException e) {
				e.getStackTrace();
			
			}
			  
		} 
	  
	  
	  @Test(dataProvider = "getLoginDetails" ,priority = 2)
	   public void getProfile(Hashtable<String, String> data)throws Exception {  
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
				
				/*
				HashMap<String, String> header = new HashMap<String,String>();
				header.put("content-type", "application/json");
				System.out.println("login_params_1" + header);
				*/
				String payload = JsonPayloadCreator.createLoginJsonPayload(data.get("username"), data.get("password"));
				System.out.println("body" + payload);
				
				
				//Loginresponse = restService.sendPostRequest(String.format(data.get("Url"),"mars"), generateHeader() , payload);
				Loginresponse = restService.sendRequest(String.format(data.get("Url"),"mars"), generateHeader() , payload, data.get("methodType") );

				System.out.println(Loginresponse.statusCode);
				//Assert.assertEquals(Loginresponse.statusCode, 200);
				//summaryLogger.log("IntroAEE_Cards_13_14", count++, "Login to user account", "PASS");

				user = GenericObjectMapper.readJSONToJavaObject(Loginresponse.responseBody, MdsLoginResponse.class);
				System.out.println("UserId :" + user.getUserId());
				
				
//				if(Loginresponse.statusCode==200)
//				{
//					pss_getDetails();
//				}
				
			} catch (Error | IOException e) {
				e.getStackTrace();
			
			}
			  
		} 
	   
	   
}

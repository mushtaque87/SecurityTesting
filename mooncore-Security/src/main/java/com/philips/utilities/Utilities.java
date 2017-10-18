package com.philips.utilities;

import java.util.HashMap;

import org.testng.Assert;

public class Utilities {
	
	   private static String OS = null;
	  
	   public static String getOsName()
	   {
	      if(OS == null) { OS = System.getProperty("os.name"); }
	      return OS;
	   }
	   public static boolean isWindows()
	   {
	      return getOsName().startsWith("Windows");  //Mac OS X
	   }
	   
	   public static String path(String resourcePath)
	   {
		   try{
			   if(isWindows())
			   {
				   return resourcePath;
			   }
			   else{
				   resourcePath = resourcePath.replace("\\", "/");
				   return resourcePath;
			   }
		   }
		   catch (Error e) { 
				Assert.fail("Path can not be formatted" + resourcePath);
			}
		return resourcePath;
		   
	   }
	   
	   private static final String SHARED_SECRET = "bUVyYWJoYXJhdG1AaGFO";
	   private static SignatureGenerator sg;
	   
	   public static String generateSignature(String url , String requestType){
		     sg = new SignatureGenerator();
			return sg.generate(requestType, url, SHARED_SECRET);
		}
	   
	 
	
	
	
}

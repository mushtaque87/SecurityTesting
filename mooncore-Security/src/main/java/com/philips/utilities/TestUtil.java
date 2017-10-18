package com.philips.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;

public class TestUtil {
	// true - Y
	// false - N
	public static boolean isTestCaseExecutable(String testCase, Xls_Reader xls){
		
		for(int rNum=2;rNum<=xls.getRowCount("Test Cases");rNum++){
			if(testCase.equals(xls.getCellData("Test Cases", "TCID", rNum))){
				// check runmode
				if(xls.getCellData("Test Cases", "Runmode", rNum).equals("Y"))
					return true;
				else
					return false;
			}
				
		}
		
		return false;
		
	}
	
	
	
	public static Object[][] getData(String testCase,Xls_Reader xls){
		System.out.println("*************");
		// find the test in xls
		// find number of cols in test
		// number of rows in test
		// put the data in hashtable and put hashtable in object array
		// return object array
		
		
		int testCaseStartRowNum=0;
		for(int rNum=1;rNum<=xls.getRowCount(testCase);rNum++){
			if(testCase.equals(xls.getCellData(testCase, 0, rNum))){
				testCaseStartRowNum = rNum;
				break;
			}
		}
		System.out.println("Test Starts from row -> "+ testCaseStartRowNum);
		
		
		// total cols
		int colStartRowNum=testCaseStartRowNum+1;
		int cols=0;
		while(!xls.getCellData(testCase, cols, colStartRowNum).equals("")){
			cols++;
		}
		System.out.println("Total cols in test -> "+ cols);
		

		// rows
		int rowStartRowNum=testCaseStartRowNum+2;
		int rows=0;
		while(!xls.getCellData(testCase, 0, (rowStartRowNum+rows)).equals("")){
			rows++;
		}
		//System.out.println("Total rows in test -> "+ rows);
		Object[][] data = new Object[rows][1];
		Hashtable<String,String> table=null;
		
		// print the test data
		for(int rNum=rowStartRowNum;rNum<(rows+rowStartRowNum);rNum++){
		table=new Hashtable<String,String>();
			for(int cNum=0;cNum<cols;cNum++){
				table.put(xls.getCellData(testCase, cNum, colStartRowNum),xls.getCellData(testCase, cNum, rNum));
				System.out.print(xls.getCellData(testCase, cNum, rNum)+" - ");
			}
			data[rNum-rowStartRowNum][0]=table;
			//System.out.println();
		}

		return data;
		
		
		
		
	}
	public static void logDebug(HttpResponse httpResponse, String strResponse, String filename, String path) throws ParseException, IOException {
		FileWriter httplogWriter = null;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		String strCurrentDate = fm.format(cal.getTime());
		path = path + "\\" + strCurrentDate;
		String strStatus = httpResponse.getStatusLine().toString();
		try {
			File dir = new File(path);
			if(dir.mkdirs() || dir.isDirectory()) {
				File httplogs = new File(dir, "http_logs_" + filename + ".txt");
				httplogWriter = new FileWriter(httplogs,true);
				
			}
			httplogWriter.write(getCurrentTimestamp() + " : " + "DEBUG:<<" + "\"" + strStatus + "\"" + "\n");
//			System.out.println("DEBUG:<<" + "\"" + strStatus + "\"");
			Header[] headers = httpResponse.getAllHeaders();
			
			if(null != headers) {
				int l = headers.length;
				for(int i = 0 ; i < l ; ++i) {
					Header h = headers[i];
					httplogWriter.write(getCurrentTimestamp() + " : " + "DEBUG:<<" + "\"" + h.toString() + "\"" + "\n");
				}
			}
			
			httplogWriter.write(getCurrentTimestamp() + " : " + "DEBUG:<<" + "\"" + strResponse + "\"" + "\n");
			httplogWriter.close();
			
		} catch (IOException e ) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public static String getCurrentTimestamp() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return dateFormat.format(new Date());
		}
	
	
	 public static DateTime createTimeStamp(String time, String offset) {

	    	DateTime dateTime_m = DateTime.parse(time).withZone(DateTimeZone.forID("UTC"));
	    	DateTime new_time = dateTime_m.plusMinutes(Double.valueOf(offset).intValue());
	    	
	    	return new_time;
	    	
	    }
	    
	    public static String createObservationJsonPayload(String Observation_Type, String value, String duration, String time, String offset) {
	    	DateTime time1 = createTimeStamp(time, offset);

	        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm.SSS'Z'");
	        String dateStr = fmt.withZone(DateTimeZone.UTC).print(time1);

	        JSONObject manual = new JSONObject();
	        JSONObject observation = new JSONObject();
	        JSONObject date = new JSONObject();
	        JSONObject vd = new JSONObject();
	        vd.put("v", Double.valueOf(value).intValue());
	        vd.put("d", Double.valueOf(duration).intValue());
	        date.put(dateStr, date);
	        observation.put(Observation_Type, observation);
	        manual.put("manual", manual);
	        
	        return(manual.toString());
	        

	        
	        
	    	
	    }

}

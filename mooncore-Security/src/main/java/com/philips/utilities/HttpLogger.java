package com.philips.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;

public class HttpLogger {
	
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

}

package com.philips.restservices;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpHost;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.HttpPatch;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.philips.utilities.HttpLogger;
import com.philips.utilities.Utilities;

import org.apache.http.impl.client.CloseableHttpClient;

import com.fasterxml.jackson.databind.DeserializationFeature;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.params.HttpParams;




public class RestService {

    private final String logDirectory = System.getProperty("user.dir") + Utilities.path("\\Logs_Reports");
    private  CloseableHttpClient httpClient;
    private ObjectMapper mapper = new ObjectMapper().
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public RestService() {
        httpClient = createCloseableHttpClient();
    }

    private CloseableHttpClient createCloseableHttpClient() {

        try {
            String proxyHost = System.getenv("proxyHost");
            int proxyPort = Integer.parseInt(System.getenv("proxyPort"));
            String proxyScheme = System.getenv("proxyScheme");
            HttpHost proxyHttp = new HttpHost(proxyHost, proxyPort, proxyScheme);
            DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxyHttp);
            return HttpClients.custom().setRoutePlanner(routePlanner).build();
        } catch (Exception e) {
            return HttpClients.createDefault();
        }
    }

    public void setProxy(String proxyhost , int proxyport , String proxyscheme) {

        try {
            String proxyHost = proxyhost;
            int proxyPort = proxyport;
            String proxyScheme = proxyscheme;
            HttpHost proxyHttp = new HttpHost(proxyHost, proxyPort, proxyScheme);
            DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxyHttp);
            httpClient = HttpClients.custom().setRoutePlanner(routePlanner).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public ResponseObject doRequest(HttpRequestBase request) throws IOException {
        StackTraceElement[] stackTraceElement = Thread.currentThread().getStackTrace();
        String strClassName = stackTraceElement[3].getClassName();
        String strMethodName = stackTraceElement[3].getMethodName();
        String logFileName = strClassName + "_" + strMethodName;

        try { //Mahesh - Commenting this change as it returns a null for all delete requests which is INCORRECT!
        	/*if(request.getMethod().equalsIgnoreCase("delete")){
            	this.httpClient.execute(request);
            	return null;
            }*/
            ResponseObject responseObject = new ResponseObject(this.httpClient.execute(request));
            HttpLogger.logDebug(responseObject.rawHttpResponse, responseObject.responseBody, logFileName, logDirectory);
            return responseObject;
        } catch (IOException e) {
            try {
                HttpLogger.logDebug(null, e.toString(), logFileName, logDirectory); // HttpLogger only has logDebug :(
            } catch (Exception e1) {
                // too bad we couldn't log anything
            }
            throw e;
        }
    }

    public ResponseObject sendGetRequest(String URL, HashMap<String, String> headers) throws IOException {
        HttpGet request = new HttpGet(URL);
        headers.entrySet().forEach(e -> request.setHeader(e.getKey(), e.getValue()));

        return doRequest(request);
    }

    public ResponseObject sendPostRequest(String URL, HashMap<String, String> headers, String payload) throws IOException {
        HttpPost request = new HttpPost(URL);
        request.setEntity(EntityBuilder.create().setText(payload).build());
        headers.entrySet().forEach(e -> request.setHeader(e.getKey(), e.getValue()));

        return doRequest(request);
    }

    public ResponseObject sendPutRequest(String URL, HashMap<String, String> headers, String payload) throws IOException {
        HttpPut request = new HttpPut(URL);
        request.setEntity(EntityBuilder.create().setText(payload).build());
        headers.entrySet().forEach(e -> request.setHeader(e.getKey(), e.getValue()));

        return doRequest(request);
    }

    public ResponseObject sendPatchRequest(String URL, HashMap<String, String> headers, String payload) throws IOException {
        HttpPatch request = new HttpPatch(URL);
        request.setEntity(EntityBuilder.create().setText(payload).build());
        headers.entrySet().forEach(e -> request.setHeader(e.getKey(), e.getValue()));

        return doRequest(request);
    }

    public ResponseObject sendDeleteRequest(String URL, HashMap<String, String> headers) throws IOException {
        HttpDelete request = new HttpDelete(URL);
        headers.entrySet().forEach(e -> request.setHeader(e.getKey(), e.getValue()));

        return doRequest(request);
    }
    
    public ResponseObject sendDeleteRequest(String URL, HashMap<String, String> headers, String payload) throws IOException {
        HttpDelete request = new HttpDelete(URL);
        headers.entrySet().forEach(e -> request.setHeader(e.getKey(), e.getValue()));

        return doRequest(request);
    }

    public HashMap<String, String> getHeadersMapFromJSONString(String headers)
            throws IOException, JsonParseException, JsonMappingException {
        HashMap<String, String> map = new HashMap<String, String>();
        ObjectMapper mapper = new ObjectMapper();
        // convert JSON string to Map
        map = mapper.readValue(headers, new TypeReference<Map<String, String>>() {
        });
        return map;
    }
    
    public ResponseObject sendRequest(String URL, HashMap<String, String> headers, String payload, String methodType) throws IOException
    {
    	switch (methodType) {
    	case "GET":
    		 HttpGet getrequest = new HttpGet(URL);
    		 headers.entrySet().forEach(e -> getrequest.setHeader(e.getKey(), e.getValue()));

    	      return doRequest(getrequest);
			
    	case "POST":
    		 HttpPost postrequest = new HttpPost(URL);
    		 postrequest.setEntity(EntityBuilder.create().setText(payload).build());
    	        headers.entrySet().forEach(e -> postrequest.setHeader(e.getKey(), e.getValue()));

    	        return doRequest(postrequest);
    		
    	case "PUT":
    		HttpPut putrequest = new HttpPut(URL);
    		putrequest.setEntity(EntityBuilder.create().setText(payload).build());
            headers.entrySet().forEach(e -> putrequest.setHeader(e.getKey(), e.getValue()));

            return doRequest(putrequest);
    		
	
		case "PATCH":
			HttpPatch patchrequest = new HttpPatch(URL);
			patchrequest.setEntity(EntityBuilder.create().setText(payload).build());
	        headers.entrySet().forEach(e -> patchrequest.setHeader(e.getKey(), e.getValue()));

	        return doRequest(patchrequest);
			
		case "DELETE":
			HttpDelete deleterequest = new HttpDelete(URL);
	        headers.entrySet().forEach(e -> deleterequest.setHeader(e.getKey(), e.getValue()));

	        return doRequest(deleterequest);
		}
    	return null;
    }
    
    public HttpRequestBase generateRequest(String URL, HashMap<String, String> headers, String payload, String methodType) throws IOException
    {
    	
    	switch (methodType) {
    	case "GET":
    		 HttpGet getrequest = new HttpGet(URL);
    		 headers.entrySet().forEach(e -> getrequest.setHeader(e.getKey(), e.getValue()));
    		 return getrequest;
    	     // return doRequest(getrequest);
			
    	case "POST":
    		 HttpPost postrequest = new HttpPost(URL);
    		 postrequest.setEntity(EntityBuilder.create().setText(payload).build());
    	        headers.entrySet().forEach(e -> postrequest.setHeader(e.getKey(), e.getValue()));
       		 return postrequest;

    	      //  return doRequest(postrequest);
    		
    	case "PUT":
    		HttpPut putrequest = new HttpPut(URL);
    		putrequest.setEntity(EntityBuilder.create().setText(payload).build());
            headers.entrySet().forEach(e -> putrequest.setHeader(e.getKey(), e.getValue()));
            return putrequest;

            // return doRequest(putrequest);
    		
	
		case "PATCH":
			HttpPatch patchrequest = new HttpPatch(URL);
			patchrequest.setEntity(EntityBuilder.create().setText(payload).build());
	        headers.entrySet().forEach(e -> patchrequest.setHeader(e.getKey(), e.getValue()));
	        return patchrequest;

	       // return doRequest(patchrequest);
			
		case "DELETE":
			HttpDelete deleterequest = new HttpDelete(URL);
	        headers.entrySet().forEach(e -> deleterequest.setHeader(e.getKey(), e.getValue()));
	        return deleterequest;
	        
	    default:
	    HttpRequestBase request = new HttpRequestBase() {
	    	
			@Override
			public String getMethod() {
				// TODO Auto-generated method stub
				return methodType;
			}
		};
		 request.setURI(URI.create(URL));
   		 headers.entrySet().forEach(e -> request.setHeader(e.getKey(), e.getValue()));
		 return request;
	        // return doRequest(deleterequest);
		}
    }
    
}

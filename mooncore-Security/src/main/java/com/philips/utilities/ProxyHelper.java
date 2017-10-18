package com.philips.utilities;

/**
 * Created by mhadimani on 03/03/17.
 */

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ProxyHelper {

	private static Properties props;
	private static Map<String, String> mapProxy;
		
	private static void loadProperties() throws IOException{
		InputStream is = ProxyHelper.class.getClassLoader().getResourceAsStream("proxyConfig.properties");
		if (props == null){
			props = new Properties();
			try{
				props.load(is);
				is.close();
			}catch(IOException e){
				throw new IOException("IO error while loading file - proxyConfig.properties");
			}
		}
	}

	public static Map<String, String> getProxyMap() throws IOException{
		mapProxy = new HashMap<String, String>();
		loadProperties();
		mapProxy.put("useProxy", props.getProperty("useProxy"));
		mapProxy.put("proxyHost", props.getProperty("proxyHost"));
		mapProxy.put("proxyPort", props.getProperty("proxyPort"));
		mapProxy.put("proxyScheme", props.getProperty("proxyScheme"));
		return mapProxy;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void setEnv(Map<String, String> newenv){
		try
		{
			Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
			Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
			theEnvironmentField.setAccessible(true);
			Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
			env.putAll(newenv);
			Field theCaseInsensitiveEnvironmentField = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
			theCaseInsensitiveEnvironmentField.setAccessible(true);
			Map<String, String> cienv = (Map<String, String>)     theCaseInsensitiveEnvironmentField.get(null);
			cienv.putAll(newenv);
		}catch (NoSuchFieldException e){
			try {
				Class[] classes = Collections.class.getDeclaredClasses();
				Map<String, String> env = System.getenv();
				for(Class cl : classes) {
					if("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
						Field field = cl.getDeclaredField("m");
						field.setAccessible(true);
						Object obj = field.get(env);
						Map<String, String> map = (Map<String, String>) obj;
						map.clear();
						map.putAll(newenv);
					}
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
	}

	public static void getProxySettings(){
		try {
			loadProperties();
			if (props.getProperty("useProxy").equals("true")){		
				setEnv(getProxyMap());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

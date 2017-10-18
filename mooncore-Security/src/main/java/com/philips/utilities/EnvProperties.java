package com.philips.utilities;

import java.io.IOException;


public class EnvProperties {

	public static final String MOONSHOTDEV = System.getProperty("user.dir") + "/src/main/resources/com/philips/SubModuleDataSheets/moonshotdev.properties";
	public static final String MOONCOREDEV = System.getProperty("user.dir") + "/src/main/resources/com/philips/SubModuleDataSheets/mooncoredev.properties";
	public static final String SCARIF = System.getProperty("user.dir") + "/src/main/resources/com/philips/SubModuleDataSheets/scarif.properties";

	private static PropertiesLoader prop;
	private static String space;

	public static void setSpace(String sp){
		space = sp;
	}

	public static PropertiesLoader getAllProperties() throws IOException{
		if (prop == null){
			prop = new PropertiesLoader();
		}else{
			return prop;
		}
		if (space == null){
			System.out.println("Space not specified - "+space);
			space = "";
		}
		switch (space.toUpperCase()){
		case "MOONSHOTDEV":
			prop.loadProperties(MOONSHOTDEV);
			System.out.print("Loaded properties - MOONSHOTDEV.properites");
			break;
		case "MOONCOREDEV":
			prop.loadProperties(MOONCOREDEV);
			System.out.print("Loaded properties - MOONCOREDEV.properites");
			break;
		case "SCARIF":
			prop.loadProperties(SCARIF);
			System.out.print("Loaded properties - SCARIF.properites");
			break;
		
		default:
			prop.loadProperties(MOONSHOTDEV);
			System.out.println("Loaded default properties - MOONSHOTDEV.properites");
		}
		return prop;
	}

}

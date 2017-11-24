package com.philips.request;

import org.testng.annotations.DataProvider;

import com.philips.utilities.TestUtil;
import com.philips.utilities.Xls_Reader;

public class MUS {

	private static Xls_Reader MUS_xls;
	
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
		   
		   
		   
		   
}

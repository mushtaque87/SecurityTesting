package com.philips.restservices;

public class MdsLoginResponse {
	
	
	    private String tokenType;

	    private String accessToken;

	    private String userId;

	    private String refreshToken;

	    public MdsLoginResponse(){
	    	
	    }
	    
	    public String getTokenType ()
	    {
	        return tokenType;
	    }

	    public void setTokenType (String tokenType)
	    {
	        this.tokenType = tokenType;
	    }

	    public String getAccessToken ()
	    {
	        return accessToken;
	    }

	    public void setAccessToken (String accessToken)
	    {
	        this.accessToken = accessToken;
	    }

	    public String getUserId ()
	    {
	        return userId;
	    }

	    public void setUserId (String userId)
	    {
	        this.userId = userId;
	    }

	    public String getRefreshToken ()
	    {
	        return refreshToken;
	    }

	    public void setRefreshToken (String refreshToken)
	    {
	        this.refreshToken = refreshToken;
	    }

	    @Override
	    public String toString()
	    {
	        return "ClassPojo [tokenType = "+tokenType+", accessToken = "+accessToken+", userId = "+userId+", refreshToken = "+refreshToken+"]";
	    }

}

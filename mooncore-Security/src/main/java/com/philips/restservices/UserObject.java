package com.philips.restservices;

public class UserObject {


    public String tokenType;

    public String accessToken;

    public String userId;

    public String refreshToken;

    public UserObject() {

    }

    @Override
    public String toString() {
        return "ClassPojo [tokenType = " + tokenType + ", accessToken = " + accessToken + ", userId = " + userId + ", refreshToken = " + refreshToken + "]";
    }

}

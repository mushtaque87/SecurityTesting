package com.philips.restservices;

public class LoginResponseObject extends ResponseObject {
    public UserObject userObject;

    public LoginResponseObject(ResponseObject ro) {
        super();

        this.statusCode = ro.statusCode;
        this.responseBody = ro.responseBody;
        this.rawHttpResponse = ro.rawHttpResponse;
    }

    public void updateUserObject() {
        try {
            userObject = GenericObjectMapper.readJSONToJavaObject(this.responseBody, UserObject.class);
        } catch (RuntimeException re) {
            userObject = null;
        }
    }
}
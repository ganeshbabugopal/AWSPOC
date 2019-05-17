package com.amazonaws.lambda.demo;



	public class TokenAuthorizer {

	    String type;
	    String authorizationToken;
	    String methodArn;

	    /**
	     * JSON input is deserialized into this object representation
	     * @param type Static value - TOKEN
	     * @param authorizationToken - Incoming bearer token sent by a client
	     * @param methodArn - The API Gateway method ARN that a client requested
	     */
	    public TokenAuthorizer(String type, String authorizationToken, String methodArn) {
	        this.type = type;
	        this.authorizationToken = authorizationToken;
	        this.methodArn = methodArn;
	    }

	    public TokenAuthorizer() {

	    }

	    public String getType() {
	        return type;
	    }

	    public void setType(String type) {
	        this.type = type;
	    }

	    

	    public String getMethodArn() {
	        return methodArn;
	    }

	    public void setMethodArn(String methodArn) {
	        this.methodArn = methodArn;
	    }

	    public String getAuthorizationToken() {
	      return authorizationToken;
	    }

	    public void setAuthorizationToken(String authorizationToken) {
	      this.authorizationToken = authorizationToken;
	    }

	
}

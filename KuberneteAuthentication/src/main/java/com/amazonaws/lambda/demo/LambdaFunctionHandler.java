package com.amazonaws.lambda.demo;

import java.security.NoSuchAlgorithmException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nimbusds.jose.JOSEException;

public class LambdaFunctionHandler implements RequestHandler<RequestModel, ResponseModel> {

    @Override
    public ResponseModel handleRequest(RequestModel input, Context context) {
        context.getLogger().log("Input: " + input);
        if ("username".equals(input.getUsername()) && "password".equals(input.getPassword())) {
            try {
              return getSuccessfulResponse();
            } catch (NoSuchAlgorithmException | JOSEException e) {
              throw new RuntimeException("500 Error");
            }
          } else {
            throw new RuntimeException("Error");
          }
        }

        private ResponseModel getSuccessfulResponse() throws NoSuchAlgorithmException, JOSEException {
          String token = TokenGenerator.generateToken();
          return new ResponseModel(token);
        }    
    

}

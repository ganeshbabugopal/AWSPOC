package com.amazonaws.lambda.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import redis.clients.jedis.Jedis;

public class LambdaFunctionHandler implements RequestHandler<Object, String> {

    @Override
    public String handleRequest(Object input, Context context) {
        context.getLogger().log("Input: " + input);
        Jedis jedis = new Jedis("sessioncluster.xs3jmq.ng.0001.use1.cache.amazonaws.com",6379);
        jedis.set("key1", "key1value");
        String returnValue = jedis.get("key1");
jedis.close();
        // TODO: implement your handler
        return "Hello from Lambda!  " + returnValue;
    }

}

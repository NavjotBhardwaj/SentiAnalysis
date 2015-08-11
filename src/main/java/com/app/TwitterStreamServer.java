package com.app;

import static spark.Spark.*;
import spark.*;

public class TwitterStreamServer {
    public static void main(String[] args) throws java.io.IOException{

        final TwitterStreamConsumer streamConsumer = new TwitterStreamConsumer();
        streamConsumer.start();

        //setPort(Integer.parseInt(System.getenv("PORT"))); uncomment for Heroku
        get(new Route("/hello") {
            @Override
            public Object handle(Request request, Response response) {
                return "<h1>Tweet Count: " + Integer.toString(streamConsumer.getTweetCount()) + "</h1>" +
                        "<h2>Latest Payload: " + streamConsumer.getLatestTweet() + "</h2>";
            }
        });
    }
}
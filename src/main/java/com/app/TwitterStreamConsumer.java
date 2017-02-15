package com.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.scribe.builder.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;

public class TwitterStreamConsumer extends Thread {

    private static final String STREAM_URI = "https://stream.twitter.com/1.1/statuses/filter.json";
    private String latestTweet;
    private int tweetCount;

    public String getLatestTweet(){
        return latestTweet;
    }

    public int getTweetCount(){
        return tweetCount;
    }
    public void run(){
        try{
            System.out.println("Starting Twitter public stream consumer thread.");

            // Enter your consumer key and secret below
            OAuthService service = new ServiceBuilder()
                    .provider(TwitterApi.class)
                    .apiKey("")
                    .apiSecret("")
                    .build();

            // Set your access token
            Token accessToken = new Token("", "");

            // Let's generate the request
            System.out.println("Connecting to Twitter Public Stream");
            OAuthRequest request = new OAuthRequest(Verb.POST, STREAM_URI);
            request.addHeader("version", "HTTP/1.1");
            request.addHeader("host", "stream.twitter.com");
            request.setConnectionKeepAlive(true);
            request.addHeader("user-agent", "Twitter Stream Reader");
            request.addBodyParameter("track", ""); // Set keywords you'd like to track here
            service.signRequest(accessToken, request);
            Response response = request.send();

            // Create a reader to read Twitter's stream
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getStream()));
            
            // Create a new file for raw tweets
            File file = new File("C:\\Users\\Striker\\Desktop\\Tweet Data\\raw_tweet.txt");
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
            String line;
            while ((line = reader.readLine()) != null) {
            	 latestTweet = line;
            	 bw.write(line);
            	 bw.newLine();
                 tweetCount++;
                System.out.println(line);
            }
            
            bw.close();
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }

    }
}

package jwplatform.examples.videoslistexample;

import java.util.*;
import java.io.UnsupportedEncodingException;
import org.json.JSONObject;

import com.mashape.unirest.http.exceptions.UnirestException;

import com.andybowling.jwplatform.jwclient.JWClient;
import com.andybowling.jwplatform.jwexceptions.JWExceptions.*;

public class VideosListExample
{
    public static void main( String[] args ) throws UnirestException, UnsupportedEncodingException, JWPlatformException
      {
          /*
          * Sample usage that:
          * 1. Creates API client using apiKey, apiSecret, and (optional) host
          * 2. Makes API request for videos/list with (optional) custom search param
          * 3. Prints result
          */
          String apiKey = "AnAPIKey";
          String apiSecret = "YourTwentyFourCharSecret";
          HashMap<String, String> clientParams = new HashMap<String, String>();
          clientParams.put("host", "api.jwplatform.com");

          HashMap<String, String> params = new HashMap<String, String>();
          // Sample query param. Any valid query parameter(s) work here
          params.put("search", "historic");

          JWClient c = new JWClient(apiKey, apiSecret, clientParams);
          JSONObject jsonResponse = c.request("videos/list", params);
          System.out.println(jsonResponse.toString(2));
      }
}

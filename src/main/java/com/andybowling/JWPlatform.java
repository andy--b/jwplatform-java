package com.andybowling.jwplatform;

/*
To build package (with debug): mvn -e package
To run: java -cp target/JWPlatform-0.1.0-SNAPSHOT.jar com.andybowling.jwplatform.JWPlatform
*/

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.net.URLEncoder;
import org.json.JSONObject;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Hello media_api!
 *
 */
public class JWPlatform
{
    // Todo:
    // DONE 1. Turn generate_api_signature into class and rename to jwplatform
    // DONE 1.5: rename project and toplevel class
    // DONE 2. Overload constructors to make params - optional params should be optional
    // DONE 3. Create notion of "client" similar to python sdk
    // DONE 4. Make urlpath a function arg so that any type of call can be made
    // DONE 4.5 Add other options to be consistent w/ python lib (host, protocol etc)
    // 5. Custom exception handling
    // DONE 5.5 Setters and getters
    // 6. Tests
    // WONT DO 7. Break out client and request into two separate classes maybe? Maybe not.
    // 8. Docstrings
    // DONE 8.5 Get into github
    // 9. Add readme
    // 9.5 add License
    // 10. Conform to java coding standards (Indentation, var names, import order etc)
    // 11. Move Sample usage into examples directory and remove main() method

    public static class JWClient {
      protected String agent;
      protected String api_key, api_secret;
      protected Integer port;
      protected final String api_format = "json";
      protected String host = "api.jwplatform.com";
      protected String scheme = "https";
      protected String version = "v1";

      public JWClient(String api_key, String api_secret, HashMap<String, String> params)
                                                      throws IllegalArgumentException {
        this.api_key = api_key;
        this.api_secret = api_secret;

        // Clone params so that we can remove items without changing user input
        // Todo: this might not be a thing in Java, look it up
        HashMap<String, String> paramsClone = new HashMap<String, String>(params);

        if (paramsClone.get("host") != null) {
          this.setHost(paramsClone.get("host"));
          paramsClone.remove("host");
        }

        if (paramsClone.get("port") != null) {
          this.setPort(paramsClone.get("port"));
          paramsClone.remove("port");
        }

        if (paramsClone.get("scheme") != null) {
          this.setScheme(paramsClone.get("scheme"));
          paramsClone.remove("scheme");
        }

        if (paramsClone.get("version") != null) {
          this.setVersion(paramsClone.get("version"));
          paramsClone.remove("version");
        }

        // Check if any extra parameters were passed - if so, throw exception
        if (!paramsClone.isEmpty()) {
          throw new IllegalArgumentException("Illegal param(s) passed: " + paramsClone.keySet());
        }
      }

      public String getAgent() {
        return (this.agent == null ? "java-jwplatform" : "java-jwplatform/" + this.agent);
      }

      public void setAgent(String agent) {
        this.agent = agent;
      }

      public String getHost() {
        return this.host;
      }

      public void setHost(String host) {
        this.host = host;
      }

      public int getPort() {
        return this.port;
      }

      public String getPortFormatted() {
        // Returns ":port" if port is set, empty string otherwise
        return (this.port == null ? "" : ":" +  Integer.toString(this.port));
      }

      // Port can be set with int or int-castable String
      public void setPort(String port) {
        try {
          this.port = Integer.parseInt(port);
        } catch (NumberFormatException e) {
          throw new NumberFormatException("port must be castable to int");
        }
      }
      public void setPort(int port) {
        this.port = port;
      }

      public String getScheme() {
        return this.scheme;
      }

      public void setScheme(String scheme) {
        if (!(scheme == "http" || scheme == "https")) {
          throw new IllegalArgumentException("Scheme must be one of: http or https");
        }
        this.scheme = scheme;
      }

      public String getVersion() {
        return this.version;
      }

      public void setVersion(String version) {
        this.version = version;
      }

      public JSONObject request( String path, Map<String, String> params
                                      ) throws UnsupportedEncodingException, UnirestException {
        /*
        * Generates API signature, makes request to JWPlatform and returns result
        * See https://developer.jwplayer.com/jw-platform/reference/v1/authentication.html
        * for definition of authentication steps
        */

        // Add key, format, random nonce, and current timestamp to list of params
        TreeMap<String, String> ordered_params = new TreeMap<String, String>();
        ordered_params.putAll(params);

        ordered_params.put("api_key", this.api_key);
        ordered_params.put("api_format", this.api_format);
        String api_nonce = Integer.toString(ThreadLocalRandom.current().nextInt(10000000, 100000000));
        ordered_params.put("api_nonce", api_nonce);
        String api_timestamp = Long.toString((long) (new Date()).getTime() / 1000);
        ordered_params.put("api_timestamp", api_timestamp);

        // Encode each query param and bulid into query string
        StringBuilder encoded_params = new StringBuilder();
        for (String param : ordered_params.keySet()) {
          if (encoded_params.length() != 0) { encoded_params.append("&"); }
          String encoded_value = URLEncoder.encode(ordered_params.get(param), "utf-8");
          encoded_params.append(param + "=" + encoded_value);
        }

        // We need to keep appending to encoded_params to get the hex digest, but
        // we branch off here to keep the params we'll use directly in the request
        String params_wo_sig = encoded_params.toString();
        encoded_params.append(this.api_secret);

        String hex_digest = DigestUtils.sha1Hex(encoded_params.toString());
        String final_params = params_wo_sig + "&api_signature=" + hex_digest;

        String url = this.scheme + "://" + this.host + this.getPortFormatted() + '/' + this.version + '/' + path;
        return Unirest.get(url + "?" + final_params)
          .header("accept", "application/json")
          .header("User-Agent", this.getAgent())
          .asJson().getBody().getObject();
      }
    }

    public static void main( String[] args ) throws UnirestException, UnsupportedEncodingException {
      /*
      * Sample usage that:
      * 1. Creates API client using api_key, api_secret, and (optional) host
      * 2. Makes API request for videos/list with (optional) custom search param
      * 3. Prints result
      */
      String api_key = "AnAPIKey";
      String api_secret = "YourTwentyFourCharSecret";
      HashMap<String, String> client_params = new HashMap<String, String>();
      client_params.put("host", "api.jwplatform.com");

      HashMap<String, String> params = new HashMap<String, String>();
      // Sample query param. Any valid query parameter(s) work here
      params.put("search", "historic");

      JWClient c = new JWClient(api_key, api_secret, client_params);
      JSONObject jsonResponse = c.request("videos/list", params);
      System.out.println(jsonResponse.toString(2));
    }
}

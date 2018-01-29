package com.andybowling.jwplatform.jwclient;

/*
To build package (with debug): mvn -e package
To run: java -cp target/JWPlatform-0.1.0-SNAPSHOT.jar com.andybowling.jwplatform.jwclient.JWClient
*/

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.net.URLEncoder;
import org.json.JSONObject;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.andybowling.jwplatform.jwexceptions.JWExceptions;
import com.andybowling.jwplatform.jwexceptions.JWExceptions.*;

/**
 * Hello media api! Master class for making API calls to jwplatform
 *
 */
public class JWClient
{
    protected final String API_FORMAT = "json";
    protected String agent;
    protected String apiKey, apiSecret;
    protected Integer port;
    protected String host = "api.jwplatform.com";
    protected String scheme = "https";
    protected String version = "v1";

    public JWClient(String apiKey, String apiSecret, HashMap<String, String> params)
                                                    throws IllegalArgumentException
    {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;

        // Clone params so that we can remove items without changing user input
        // Todo: this might not be a thing in Java, look it up
        HashMap<String, String> paramsClone = new HashMap<String, String>(params);

        if (paramsClone.get("host") != null)
        {
            this.setHost(paramsClone.get("host"));
            paramsClone.remove("host");
        }

        if (paramsClone.get("port") != null)
        {
            this.setPort(paramsClone.get("port"));
            paramsClone.remove("port");
        }

        if (paramsClone.get("scheme") != null)
        {
            this.setScheme(paramsClone.get("scheme"));
            paramsClone.remove("scheme");
        }

        if (paramsClone.get("version") != null)
        {
            this.setVersion(paramsClone.get("version"));
            paramsClone.remove("version");
        }

        // Check if any extra parameters were passed - if so, throw exception
        if (!paramsClone.isEmpty())
        {
            throw new IllegalArgumentException("Illegal param(s) passed: " + paramsClone.keySet());
        }
    }

    public String getAgent()
    {
        return (this.agent == null ? "java-jwplatform" : "java-jwplatform/" + this.agent);
    }

    public void setAgent(String agent)
    {
        this.agent = agent;
    }

    public String getHost()
    {
        return this.host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public int getPort()
    {
        return this.port;
    }

    public String getPortFormatted()
    {
        // Returns ":port" if port is set, empty string otherwise
        return (this.port == null ? "" : ":" +  Integer.toString(this.port));
    }

    // Port can be set with int or int-castable String
    public void setPort(String port)
    {
        try
        {
            this.port = Integer.parseInt(port);
        } catch (NumberFormatException e)
        {
            throw new NumberFormatException("port must be castable to int");
        }
    }
    public void setPort(int port)
    {
        this.port = port;
    }

    public String getScheme()
    {
        return this.scheme;
    }

    public void setScheme(String scheme)
    {
        if (!(scheme == "http" || scheme == "https"))
        {
            throw new IllegalArgumentException("Scheme must be one of: http or https");
        }
        this.scheme = scheme;
    }

    public String getVersion()
    {
        return this.version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public static String getCurrentTimestamp()
    {
        return Long.toString((long) (new Date()).getTime() / 1000);
    }

    public static String getRandomNonce()
    {
        return Integer.toString(ThreadLocalRandom.current().nextInt(10000000, 100000000));
    }

    protected static HttpResponse<JsonNode> makeResponse( GetRequest request ) throws JWPlatformException
    {
        HttpResponse<JsonNode> response;
        try
        {
            response = request.asJson();
        } catch (UnirestException e)
        {
            throw new JWPlatformUnknownException("Non-JSON response from server: " + e.toString());
        }
        if (response.getStatus() != 200)
        {
            String errorType = response.getBody().getObject().get("code").toString();

            String message = response.getBody().getObject().toString(2);
            JWExceptions.mapJWException(StringUtils.stripEnd(errorType, "Error"), message);
        }

        return response;
    }

    public String makeRequestURL( String path, Map<String, String> params ) throws UnsupportedEncodingException
    {
        TreeMap<String, String> orderedParams = new TreeMap<String, String>();
        orderedParams.putAll(params);

        orderedParams.put("api_key", this.apiKey);
        orderedParams.put("api_format", this.API_FORMAT);
        orderedParams.put("api_nonce", this.getRandomNonce());
        orderedParams.put("api_timestamp", this.getCurrentTimestamp());

        // Encode each query param and bulid into query string
        StringBuilder encodedParams = new StringBuilder();
        for (String param : orderedParams.keySet())
        {
            if (encodedParams.length() != 0) { encodedParams.append("&"); }
            String encodedValue = URLEncoder.encode(orderedParams.get(param), "utf-8");
            encodedParams.append(param + "=" + encodedValue);
        }

        // We need to keep appending to encodedParams to get the hex digest, but
        // we branch off here to keep the params we'll use directly in the request
        String paramsNoSignature = encodedParams.toString();
        encodedParams.append(this.apiSecret);

        String hexDigest = DigestUtils.sha1Hex(encodedParams.toString());
        String finalParams = paramsNoSignature + "&api_signature=" + hexDigest;

        String url = this.scheme + "://" + this.host + this.getPortFormatted() + '/' + this.version + '/' + path;
        return url + "?" + finalParams;
    }

    public JSONObject request( String path, Map<String, String> params
                                    ) throws JWPlatformException, UnsupportedEncodingException
    {
        /**
         * Generates API signature, makes request to JWPlatform and returns result
         * See https://developer.jwplayer.com/jw-platform/reference/v1/authentication.html
         * for definition of authentication steps
        **/

        // Add key, format, random nonce, and current timestamp to list of params
        String requestURL = this.makeRequestURL(path, params);
        GetRequest request =  Unirest.get(requestURL)
            .header("accept", "application/json")
            .header("User-Agent", this.getAgent());
        return makeResponse(request).getBody().getObject();

    }
}

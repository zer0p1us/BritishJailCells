package com.zer0p1us.core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author zer0p1us
 */
public class ApiCall {
    
    public static HttpURLConnection GetRequest(String URL, Map<String, String> Parameters) {
        try {
            String formattedParams = Parameters.entrySet().stream().map(entry -> {
            try {
                return URLEncoder.encode(entry.getKey(), "UTF-8") + "=" +
                       URLEncoder.encode(entry.getValue(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            }).collect(Collectors.joining("&"));

            URL url = new URI(URL + formattedParams).toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            
            return con;
        } catch (URISyntaxException ex) {
            Logger.getLogger(ApiCall.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ProtocolException ex) {
            Logger.getLogger(ApiCall.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(ApiCall.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}

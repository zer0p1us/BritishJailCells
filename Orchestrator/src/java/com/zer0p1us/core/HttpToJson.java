package com.zer0p1us.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zer0p1us
 */
public class HttpToJson {
    
    public static String getJson(HttpURLConnection con) {
        String json = "";
        try {
            BufferedReader buf = new BufferedReader( new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String line = buf.readLine(); 
            while (line != null) {
                json += line;
                line = buf.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(HttpToJson.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }
    
}

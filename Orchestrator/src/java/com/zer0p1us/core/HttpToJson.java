package com.zer0p1us.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author zer0p1us
 */
public class HttpToJson {
    
    public static String getJson(HttpURLConnection con) throws IOException {
        BufferedReader buf = new BufferedReader( new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
        String json = "";
        String line = buf.readLine(); 
        while (line != null) {
            json += line;
            line = buf.readLine();
        }
        return json;
    }
    
}

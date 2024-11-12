package com.zer0p1us.core;

import com.google.gson.Gson;
import com.zer0p1us.core.misc.NoDataException;
import com.zer0p1us.endpoints.models.SevenTimer.SevenTimer;
import com.zer0p1us.endpoints.models.open_meteo.OpenMeteo;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author siman
 */
public class WeatherService {
    
    private float lat;
    private float lon;
    
    private float currentTemp;
    private float averageTemp;
    
    public WeatherService(float lat, float lon) {
        this.lat = lat;
        this.lon = lon;
    }
    
    private void load7TimerData() throws URISyntaxException, IOException, NoDataException {
        Map<String, String> params = new HashMap<>();
        params.put("product", "civil");
        params.put("unit", "metric");
        params.put("output", "json");
        params.put("lang", "en");
        params.put("lat", String.valueOf(lat));
        params.put("lon", String.valueOf(lon));
        
        String formatedParams = params.entrySet().stream().map(entry -> {
            try {
                return URLEncoder.encode(entry.getKey(), "UTF-8") + "=" +
                       URLEncoder.encode(entry.getValue(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.joining("&"));
        
        URL url = new URI("http://www.7timer.info/bin/api.pl?" + formatedParams).toURL();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();
        
        String json = HttpToJson.getJson(con);
        if (json.isEmpty()) { throw new NoDataException("7Timer api call "+url.toString()+" json response is empty"); }
        Gson gson = new Gson();
        
        SevenTimer sevenTimer = gson.fromJson(json, SevenTimer.class);
        if (sevenTimer.dataseries.size() == 0) { throw new NoDataException("7Timer json response dataseries is empty"); }
        currentTemp = sevenTimer.dataseries.get(0).temp2m;
        averageTemp = (float) sevenTimer.dataseries.stream()
                .mapToInt(ds -> ds.temp2m)
                .average()
                .orElse(0.0f);
    }
    
    private void loadOpenMeteoData() throws URISyntaxException, ProtocolException, IOException, NoDataException {
        Map<String, String> params = new HashMap<>();
        params.put("hourly", "temperature_2m");
        params.put("current", "temperature_2m");
        params.put("latitude", String.valueOf(lat));
        params.put("longitude", String.valueOf(lon));
        
        String formatedParams = params.entrySet().stream().map(entry -> {
            try {
                return URLEncoder.encode(entry.getKey(), "UTF-8") + "=" +
                       URLEncoder.encode(entry.getValue(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.joining("&"));
        
        URL url = new URI("https://api.open-meteo.com/v1/forecast?" + formatedParams).toURL();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();
        
        String json = HttpToJson.getJson(con);
        if (json.isEmpty()) { throw new NoDataException("OpenMeteo api call "+url.toString()+" json response is empty"); }
        Gson gson = new Gson();
        
        OpenMeteo openMeteo = gson.fromJson(json, OpenMeteo.class);
        currentTemp = (float) openMeteo.current.temperature_2m;
        averageTemp = (float) Arrays.stream(openMeteo.hourly.temperature_2m).average().orElse(0);
    }
    
    public void loadWeather() {
        try {
            this.load7TimerData();
            return;
        } catch (Exception e) {
            System.err.println("7Timer api call failed due to: "+e.toString());
        }
        
        
        try {
            this.loadOpenMeteoData();
            return;
        } catch (Exception e) {
            System.err.println("OpenMeteo api call failed due to: "+e.toString());
        }
    }
    
    
        
}

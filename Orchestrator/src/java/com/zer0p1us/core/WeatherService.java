package com.zer0p1us.core;

import com.google.gson.Gson;
import com.zer0p1us.endpoints.models._7timer.Root;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
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
    
    private String currentWeather;
    private String averageWeather;
    
    public WeatherService(float lat, float lon) {
        this.lat = lat;
        this.lon = lon;
    }
    
    public void get7TimerData() throws URISyntaxException, IOException {
        Map<String, String> params = new HashMap<>();
        params.put("product", "civillight");
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
        Gson gson = new Gson();
        
        Root root = gson.fromJson(json, Root.class);
        if (root.dataseries.size() == 0) { return; }
        currentWeather = root.dataseries.get(0).weather;
        averageWeather = root.dataseries.stream()
                .collect(Collectors.groupingBy(classifier -> classifier.weather, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByKey())
                .get().getKey();
        
    }
    
    public String getCurrentWeather() { return this.currentWeather; }
    public String getAverageWeather() { return this.averageWeather; }
    
}

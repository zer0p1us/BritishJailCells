package com.zer0p1us.service;

import com.google.gson.Gson;
import com.zer0p1us.core.ApiCall;
import com.zer0p1us.core.CacheManager;
import com.zer0p1us.core.HttpToJson;
import com.zer0p1us.core.misc.NoDataException;
import com.zer0p1us.endpoints.models.Coordinates;
import com.zer0p1us.endpoints.models.sevenTimer.SevenTimer;
import com.zer0p1us.endpoints.models.WeatherData;
import com.zer0p1us.endpoints.models.openMeteo.OpenMeteo;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author zer0p1us
 */
public class WeatherService {
    
    private String primary_api = "http://www.7timer.info/bin/civil.php?";
    private String secondary_api = "https://api.open-meteo.com/v1/forecast?";

    // Singleton instance
    private static WeatherService instance;
    
    private CacheManager<Coordinates, WeatherData> cacheManager;

    // Private constructor to prevent instantiation
    private WeatherService() {
        this.cacheManager = new CacheManager();
    }

    // Thread-safe singleton getter
    public static synchronized WeatherService getInstance() {
        if (instance == null) {
            instance = new WeatherService();
        }
        return instance;
    }

    public WeatherData getWeather(Coordinates coords) {
        return cacheManager.getOrCompute(coords, this::fetchWeatherData);
    }
    
    private WeatherData fetchWeatherData(Coordinates coords) {
        try {
            return get7TimerData(coords);
        } catch (Exception e) {
            System.err.println("7Timer api call failed due to: "+e.toString());
        }
        
        try {
            return getOpenMeteoData(coords);
        } catch (Exception e) {
            System.err.println("OpenMeteo api call failed due to: "+e.toString());
        }
        
        return new WeatherData();
    }

    private WeatherData get7TimerData(Coordinates coords) throws NoDataException {
        Map<String, String> params = new HashMap<>();
        params.put("unit", "metric");
        params.put("output", "json");
        params.put("lang", "en");
        params.put("lat", String.valueOf(coords.latitude));
        params.put("lon", String.valueOf(coords.longitude));
        
        HttpURLConnection con = ApiCall.GetRequest(primary_api, params);
        String json = HttpToJson.getJson(con);
        if (json.isEmpty()) { throw new NoDataException("7Timer api call "+con.getURL()+" json response is empty"); }
        
        Gson gson = new Gson();
        SevenTimer sevenTimer = gson.fromJson(json, SevenTimer.class);
        if (sevenTimer.dataseries.size() == 0) { throw new NoDataException("7Timer json response dataseries is empty"); }
        WeatherData data = new WeatherData();
        data.current_temp_celsius = sevenTimer.dataseries.get(0).temp2m;
        data.seven_day_average_temp_celsius = (float) sevenTimer.dataseries.stream()
                .mapToInt(ds -> ds.temp2m)
                .average()
                .orElse(0.0f);
        return data;
    }
    
    private WeatherData getOpenMeteoData(Coordinates coords) throws NoDataException {
        Map<String, String> params = new HashMap<>();
        params.put("hourly", "temperature_2m");
        params.put("current", "temperature_2m");
        params.put("latitude", String.valueOf(coords.latitude));
        params.put("longitude", String.valueOf(coords.longitude));
        
        HttpURLConnection con = ApiCall.GetRequest(secondary_api, params);
        String json = HttpToJson.getJson(con);
        if (json.isEmpty()) { throw new NoDataException("OpenMeteo api call "+con.getURL()+" json response is empty"); }
        
        Gson gson = new Gson();
        OpenMeteo openMeteo = gson.fromJson(json, OpenMeteo.class);
        WeatherData data = new WeatherData();
        data.current_temp_celsius = (float) openMeteo.current.temperature_2m;
        data.seven_day_average_temp_celsius = (float) Arrays.stream(openMeteo.hourly.temperature_2m).average().orElse(0);
        return data;
    }
       
}

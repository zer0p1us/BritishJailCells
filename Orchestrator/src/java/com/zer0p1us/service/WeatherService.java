package com.zer0p1us.service;

import com.google.gson.Gson;
import com.zer0p1us.core.ApiCall;
import com.zer0p1us.core.HttpToJson;
import com.zer0p1us.core.misc.NoDataException;
import com.zer0p1us.endpoints.models.Coordinates;
import com.zer0p1us.endpoints.models.SevenTimer.SevenTimer;
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
    
    private String primary_api = "http://www.7timer.info/bin/api.pl?";
    private String secondary_api = "https://api.open-meteo.com/v1/forecast?";
    
    private WeatherData load7TimerData(Coordinates coords) throws NoDataException {
        Map<String, String> params = new HashMap<>();
        params.put("product", "civil");
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
        data.currentTemp = sevenTimer.dataseries.get(0).temp2m;
        data.sevenDayAverageTemp = (float) sevenTimer.dataseries.stream()
                .mapToInt(ds -> ds.temp2m)
                .average()
                .orElse(0.0f);
        return data;
    }
    
    private WeatherData loadOpenMeteoData(Coordinates coords) throws NoDataException {
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
        data.currentTemp = (float) openMeteo.current.temperature_2m;
        data.sevenDayAverageTemp = (float) Arrays.stream(openMeteo.hourly.temperature_2m).average().orElse(0);
        return data;
    }
    
    public WeatherData getWeather(Coordinates coords) {
        try {
            return load7TimerData(coords);
        } catch (Exception e) {
            System.err.println("7Timer api call failed due to: "+e.toString());
        }
        
        try {
            return loadOpenMeteoData(coords);
        } catch (Exception e) {
            System.err.println("OpenMeteo api call failed due to: "+e.toString());
        }
        
        return new WeatherData();
    }
    
    
        
}

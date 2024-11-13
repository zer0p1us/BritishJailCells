package com.zer0p1us.core;

import com.google.gson.Gson;
import com.zer0p1us.core.misc.NoDataException;
import com.zer0p1us.core.misc.Coordinate;
import com.zer0p1us.endpoints.models.ProjectOSRM.ProjectOSRM;
import com.zer0p1us.endpoints.models.SevenTimer.SevenTimer;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
 * @author zer0p1us
 */
public class ProximityService {
    
    public transient Coordinate[] coordinates;
    
    public double durationSeconds;
    public double distanceMeters;
    
    public ProximityService(Coordinate[] coordinates) {
        this.coordinates = coordinates;
    }
    
    public void loadProjectOSRMData() throws URISyntaxException, MalformedURLException, NoDataException, IOException {
        Map<String, String> params = new HashMap<>();
        params.put("overview", "false");
        
        String formatedParams = params.entrySet().stream().map(entry -> {
            try {
                return URLEncoder.encode(entry.getKey(), "UTF-8") + "=" +
                       URLEncoder.encode(entry.getValue(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.joining("&"));
        
        // must be logitude and then latitude
        String formatedCoordinates = Arrays.stream(coordinates).map(coordinate -> {
            try {
                return URLEncoder.encode(String.valueOf(coordinate.longitude), "UTF-8") + "," +
                       URLEncoder.encode(String.valueOf(coordinate.latitude), "UTF-8");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.joining(";"));
        
        URL url = new URI("http://router.project-osrm.org/route/v1/driving/" + formatedCoordinates + "?" + formatedParams).toURL();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();
        
        String json = HttpToJson.getJson(con);
        if (json.isEmpty()) { throw new NoDataException("ProjetOSRM api call "+url.toString()+" json response is empty"); }
        
        Gson gson = new Gson();
        ProjectOSRM projectOSRM = gson.fromJson(json, ProjectOSRM.class);
        if (projectOSRM.routes.length == 0) { throw new NoDataException("ProjectOSRM json response dataseries is empty"); }
        
        this.distanceMeters = projectOSRM.routes[0].distance;
        this.durationSeconds = projectOSRM.routes[0].duration;
        
    }
}

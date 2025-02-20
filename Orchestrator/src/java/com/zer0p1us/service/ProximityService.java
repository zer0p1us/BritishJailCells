package com.zer0p1us.service;

import com.google.gson.Gson;
import com.zer0p1us.core.ApiCall;
import com.zer0p1us.core.CacheManager;
import com.zer0p1us.core.HttpToJson;
import com.zer0p1us.core.misc.NoDataException;
import com.zer0p1us.endpoints.models.projectOSRM.ProjectOSRM;
import com.zer0p1us.endpoints.models.Coordinates;
import com.zer0p1us.endpoints.models.ProximityData;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author zer0p1us
 */
public class ProximityService {
    
    private String primary_api = "http://router.project-osrm.org/route/v1/driving/";

    private static ProximityService instance;

    private CacheManager<Coordinates[], ProximityData> cacheManager;

    // Private constructor to prevent instantiation
    private ProximityService() {
        this.cacheManager = new CacheManager();
    }

    // Thread-safe singleton getter
    public static synchronized ProximityService getInstance() {
        if (instance == null) {
            instance = new ProximityService();
        }
        return instance;
    }

    public ProximityData getProximityData(Coordinates[] Coordinates) {
        return cacheManager.getOrCompute(Coordinates, this::fetchProximityData);
    }

    public ProximityData fetchProximityData(Coordinates[] coordinates) {
        try {
            return getProjectOSRMData(coordinates);
        } catch (URISyntaxException ex) {
            Logger.getLogger(ProximityService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoDataException ex) {
            Logger.getLogger(ProximityService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProximityService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ProximityData();
    }
    
    private ProximityData getProjectOSRMData(Coordinates[] coordinates) throws URISyntaxException, MalformedURLException, NoDataException, IOException {
        Map<String, String> params = new HashMap<>();
        params.put("overview", "false");
        
        // must be logitude and then latitude
        String formattedCoordinates = Arrays.stream(coordinates).map(coordinate -> {
            try {
                return URLEncoder.encode(String.valueOf(coordinate.longitude), "UTF-8") + "," +
                       URLEncoder.encode(String.valueOf(coordinate.latitude), "UTF-8");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.joining(";"));
        
        HttpURLConnection con = ApiCall.GetRequest(primary_api+formattedCoordinates+"?", params);
        
        String json = HttpToJson.getJson(con);
        if (json.isEmpty()) { throw new NoDataException("ProjetOSRM api call "+con.getURL()+" json response is empty"); }
        
        Gson gson = new Gson();
        ProjectOSRM projectOSRM = gson.fromJson(json, ProjectOSRM.class);
        if (projectOSRM.routes.length == 0) { throw new NoDataException("ProjectOSRM json response dataseries is empty"); }
        
        ProximityData proximity = new ProximityData();
        proximity.distance_meters = projectOSRM.routes[0].distance;
        proximity.duration_seconds = projectOSRM.routes[0].duration;
        return proximity;
    }
}

package com.zer0p1us.service;

import com.google.gson.Gson;
import com.zer0p1us.core.ApiCall;
import com.zer0p1us.core.CacheManager;
import com.zer0p1us.core.HttpToJson;
import com.zer0p1us.core.misc.NoDataException;
import com.zer0p1us.endpoints.models.Coordinates;
import com.zer0p1us.endpoints.models.getTheData.GetTheData;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zer0p1us
 */
public class GeoCodingService {
    
    private String api = "https://api.getthedata.com/postcode/";
    
    private static GeoCodingService instance;
    
    private CacheManager<String, Coordinates> cacheManager;
    
    // Private constructor to prevent instantiation
    private GeoCodingService() {
        this.cacheManager = new CacheManager();
    }
    
    // Thread-safe singleton getter
    public static synchronized GeoCodingService getInstance() {
        if (instance == null) {
            instance = new GeoCodingService();
        }
        return instance;
    }
        
    public Coordinates getCoordinatesFromPostcode(String postcode) {
        return cacheManager.getOrCompute(postcode, this::fetchCoordinatesFromPostcode);
    }
    
    private Coordinates fetchCoordinatesFromPostcode(String postcode) {
        try {
            return getGetTheData(postcode);
        } catch (NoDataException ex) {
            Logger.getLogger(GeoCodingService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new Coordinates();
    }
    
    private Coordinates getGetTheData(String postcode) throws NoDataException{
        HttpURLConnection con = ApiCall.GetRequest(api+postcode, new HashMap<String, String>());
        String json = HttpToJson.getJson(con);
        if (json.isEmpty()) { throw new NoDataException("getthedata api call "+con.getURL()+" json response is empty"); }
        Gson gson = new Gson();
        GetTheData coords = gson.fromJson(json, GetTheData.class);
        return coords.data;
    }
}

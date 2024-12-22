package com.zer0p1us.service;

import com.google.gson.Gson;
import com.zer0p1us.core.ApiCall;
import com.zer0p1us.core.HttpToJson;
import com.zer0p1us.core.misc.NoDataException;
import com.zer0p1us.endpoints.models.coordinates.Coordinates;
import com.zer0p1us.endpoints.models.getTheData.GetTheData;
import java.net.HttpURLConnection;
import java.util.HashMap;

/**
 *
 * @author zer0p1us
 */
public class GeoCodingService {
    
    private String api = "https://api.getthedata.com/postcode/";
        
    public Coordinates GetCoordinatesFromPostcode(String postcode) throws NoDataException {
        HttpURLConnection con = ApiCall.GetRequest(api+postcode, new HashMap<String, String>());
        String json = HttpToJson.getJson(con);
        if (json.isEmpty()) { throw new NoDataException("getthedata api call "+con.getURL()+" json response is empty"); }
        Gson gson = new Gson();
        GetTheData coords = gson.fromJson(json, GetTheData.class);
        return coords.data;
    }
}

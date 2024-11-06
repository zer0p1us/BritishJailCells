package com.zer0p1us.endpoints;

import com.google.gson.Gson;
import com.zer0p1us.core.HttpToJson;
import com.zer0p1us.endpoints.models.Rooms;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Search for a room endpoint
 *
 * @author zer0p1us
 */
@Path("search")
public class Search {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Search
     */
    public Search() {
    }

    /**
     * Retrieves representation of an instance of com.zer0p1us.endpoints.Search
     * @param lon longitude
     * @param lat latitude
     * @return an instance of java.lang.String
     * @throws java.net.MalformedURLException
     * @throws java.net.URISyntaxException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@QueryParam("lon") float lon, @QueryParam("lat") float lat) throws MalformedURLException, IOException, URISyntaxException {
        System.out.println("lon="+lon);
        System.out.println("lat="+lat);
        
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
        
        if (con.getResponseCode() != 200) {
            System.out.println(con.getResponseCode());
        }
        
        String json = HttpToJson.getJson(con);
        
        return json;
    }

    /**
     * PUT method for updating or creating an instance of Search
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}

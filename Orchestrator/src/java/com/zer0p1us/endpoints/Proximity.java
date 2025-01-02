package com.zer0p1us.endpoints;

import com.google.gson.Gson;
import com.zer0p1us.service.ProximityService;
import com.zer0p1us.core.misc.NoDataException;
import com.zer0p1us.endpoints.models.Coordinates;
import com.zer0p1us.endpoints.models.ProximityData;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author zer0p1us
 */
@Path("proximity")
public class Proximity {

    /**
     * Creates a new instance of Proximity
     */
    public Proximity() {
    }

    /**
     * Retrieves representation of an instance of com.zer0p1us.endpoints.Proximity
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@QueryParam("lon0") double lon0, @QueryParam("lat0") double lat0, @QueryParam("lon1") double lon1, @QueryParam("lat1") double lat1) throws URISyntaxException, NoDataException, IOException {
        ProximityService proximityService = ProximityService.getInstance();
        ProximityData proximity = proximityService.getProximityData(new Coordinates[] {new Coordinates(lon0, lat0), new Coordinates(lon1, lat1)});
        Gson gson = new Gson();
        return gson.toJson(proximity, ProximityData.class);
    }
}

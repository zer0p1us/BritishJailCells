package com.zer0p1us.endpoints;

import com.google.gson.Gson;
import com.zer0p1us.core.ProximityService;
import com.zer0p1us.core.misc.Coordinate;
import com.zer0p1us.core.misc.NoDataException;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author zer0p1us
 */
@Path("proximity")
public class Proximity {

    @Context
    private UriInfo context;

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
    public String getJson(@QueryParam("lon0") float lon0, @QueryParam("lat0") float lat0, @QueryParam("lon0") float lon1, @QueryParam("lat1") float lat1) throws URISyntaxException, NoDataException, IOException {
        ProximityService proximityService = new ProximityService(new Coordinate[] {new Coordinate(lon0, lat0), new Coordinate(lon1, lat1)});
        proximityService.loadProjectOSRMData();
        Gson gson = new Gson();
        return gson.toJson(proximityService, ProximityService.class);
    }

    /**
     * PUT method for updating or creating an instance of Proximity
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}

package com.zer0p1us.endpoints;

import com.google.gson.Gson;
import com.zer0p1us.endpoints.models.Coordinates;
import com.zer0p1us.service.GeoCodingService;
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
 * @author zer0p1us <42619260+zer0p1us@users.noreply.github.com>
 */
@Path("geocoding")
public class GeoCoding {

    /**
     * Creates a new instance of GeoCoding
     */
    public GeoCoding() {
    }

    /**
     * Retrieves representation of an instance of com.zer0p1us.endpoints.GeoCoding
     * @param postcode postcode to GeoCode
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@QueryParam("postcode") String postcode) {
        GeoCodingService geoCoding = GeoCodingService.getInstance();
        Coordinates coords = geoCoding.getCoordinatesFromPostcode(postcode);
        Gson gson = new Gson();
        return gson.toJson(coords, Coordinates.class);
    }
}

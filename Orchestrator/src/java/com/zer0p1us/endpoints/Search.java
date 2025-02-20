package com.zer0p1us.endpoints;

import com.google.gson.Gson;
import com.zer0p1us.core.Database;
import com.zer0p1us.endpoints.models.rooms.Room;
import com.zer0p1us.endpoints.models.rooms.Rooms;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Search for a room endpoint
 *
 * @author zer0p1us
 */
@Path("search")
public class Search {

    /**
     * Creates a new instance of Search
     */
    public Search() {
    }

    /**
     * Retrieves representation of an instance of com.zer0p1us.endpoints.Search
     * @param searchTerms Search terms
     * @param maxMonthlyRent
     * @param furnished
     * @param liveInLandlord
     * @param maxSharedWith
     * @param billsIncluded
     * @param bathroomShared
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@QueryParam("searchTerms") String searchTerms,
                          @QueryParam("maxMonthlyRent") Integer maxMonthlyRent,
                          @QueryParam("furnished") Boolean furnished,
                          @QueryParam("liveInLandlord") Boolean liveInLandlord,
                          @QueryParam("maxSharedWith") Integer maxSharedWith,
                          @QueryParam("billsIncluded") Boolean billsIncluded,
                          @QueryParam("bathroomShared") Boolean bathroomShared) {
        Database db = Database.getInstance();
        Rooms rooms = db.getRooms(searchTerms,
                                  maxMonthlyRent,
                                  furnished,
                                  liveInLandlord,
                                  maxSharedWith,
                                  billsIncluded,
                                  bathroomShared);
        
        //TODO: this is really expensive, consider moving this to a separate endpoint
        for (Room r : rooms.rooms) {
            r.fetchCoordinates().fetchWeatherData();
        }
        
        Gson gson = new Gson();
        return gson.toJson(rooms);
    }
}

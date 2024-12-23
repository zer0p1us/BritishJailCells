package com.zer0p1us.endpoints;

import com.google.gson.Gson;
import com.zer0p1us.core.Database;
import com.zer0p1us.endpoints.models.roomApplication.RoomApplications;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Get history of a room endpoint
 *
 * @author zer0p1us
 */
@Path("history")
public class History {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of History
     */
    public History() {
    }

    /**
     * Retrieves representation of an instance of com.zer0p1us.endpoints.History
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@QueryParam("roomId") String roomId) {
        Database db = new Database();
        RoomApplications roomApplications = db.getRoomHistory(roomId);
        Gson gs = new Gson();
        return gs.toJson(roomApplications, RoomApplications.class);
    }
}

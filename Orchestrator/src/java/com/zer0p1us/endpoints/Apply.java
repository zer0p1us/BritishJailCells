package com.zer0p1us.endpoints;

import com.zer0p1us.core.Database;
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
 * Apply for a room endpoint
 *
 * @author zer0p1us
 */
@Path("apply")
public class Apply {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Apply
     */
    public Apply() {
    }

    /**
     * Apply for room
     * @param roomId room id 
     * @param userId user id, this is made up by the user
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(@QueryParam("roomId") String roomId, @QueryParam("userId") String userId) {
        Database db = new Database();
        db.ApplyForRoom(roomId, userId);
    }
}

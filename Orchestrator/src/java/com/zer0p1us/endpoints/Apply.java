package com.zer0p1us.endpoints;

import com.zer0p1us.core.Database;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Apply for a room endpoint
 *
 * @author zer0p1us
 */
@Path("apply")
public class Apply {

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
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void postJson(@QueryParam("roomId") String roomId, @QueryParam("userId") String userId) {
        Database db = Database.getInstance();
        db.applyForRoom(roomId, userId);
    }
}

package com.zer0p1us.endpoints;

import com.zer0p1us.core.Database;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Cancel room application endpoint
 *
 * @author zer0p1us
 */
@Path("cancel")
public class Cancel {

    /**
     * Creates a new instance of Cancel
     */
    public Cancel() {
    }

    /**
     * PUT method for updating or creating an instance of Cancel
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(@QueryParam("applicationRef") String applicationRef, @QueryParam("userId") String userId) {
        Database db = Database.getInstance();
        db.cancelApplication(applicationRef, userId);
    }
}

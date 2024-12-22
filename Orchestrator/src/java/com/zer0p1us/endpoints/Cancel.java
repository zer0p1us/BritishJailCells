package com.zer0p1us.endpoints;

import com.zer0p1us.core.Database;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
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

    @Context
    private UriInfo context;

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
    public void putJson(@QueryParam("application_ref") String application_ref) {
        Database db = new Database();
        db.CancelApplication(application_ref);
    }
}

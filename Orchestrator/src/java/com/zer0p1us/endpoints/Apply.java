package com.zer0p1us.endpoints;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
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
     * Retrieves representation of an instance of com.zer0p1us.endpoints.Apply
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        return "Apply endpoint not implemented";
    }

    /**
     * PUT method for updating or creating an instance of Apply
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}

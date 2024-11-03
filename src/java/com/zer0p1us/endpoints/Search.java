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
 * Search for a room endpoint
 *
 * @author zer0p1us
 */
@Path("search")
public class Search {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Search
     */
    public Search() {
    }

    /**
     * Retrieves representation of an instance of com.zer0p1us.endpoints.Search
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        return "Search endpoint not implemented";
    }

    /**
     * PUT method for updating or creating an instance of Search
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}

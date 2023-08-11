package com.controller;

import com.entity.HeaderData;
import com.service.HeaderService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@Path("/headerdata")
public class HeaderController {
    HeaderService service = new HeaderService();
    @GET
    @Path("/view")
    @Produces(MediaType.APPLICATION_JSON)
    public HeaderData getData() {
        return service.getData();
    }

    @POST
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Map<String, Object> updateData(HeaderData data) {
        return service.updateData(data);
    }
}

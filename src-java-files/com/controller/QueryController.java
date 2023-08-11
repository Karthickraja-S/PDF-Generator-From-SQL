package com.controller;

import com.service.QueryService;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@Path("/query")
public class QueryController {
    @POST
    @Path("/get")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getDataFromDB(Map<String,Object> param) {
        System.out.println(param);
        return QueryService.getDataFromQuery(param.get("query").toString());
    }
}

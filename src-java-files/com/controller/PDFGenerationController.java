package com.controller;

import com.service.PDFGenerationService;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@Path("/reports")
public class PDFGenerationController {
    PDFGenerationService service = new PDFGenerationService();

    @POST
    @Path("/export")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public void exportData(Map payload , @Context HttpServletRequest request , @Context HttpServletResponse response)throws Exception {
        System.out.println("payload => "+payload);
        try {
            String fileName = payload.get("file-name").toString();
            String result = payload.get("result").toString();
            service.writeQueryResultToPDF(fileName, result, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }
}

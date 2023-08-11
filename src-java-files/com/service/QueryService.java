package com.service;

import com.database.DBQueryHandler;
import org.json.JSONObject;

import java.util.Map;

public class QueryService {
    public static Map<String, Object> getDataFromQuery(String query) {
        return  DBQueryHandler.getInstance().processSelectQuery(query).toMap();
    }
}

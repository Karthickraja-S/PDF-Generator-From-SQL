package com.service;

import com.database.DBUtil;
import com.entity.HeaderData;

import java.util.HashMap;
import java.util.Map;

public class HeaderService {
    public HeaderData getData() {
       return DBUtil.getHeaderData();
    }
    public Map<String,Object> updateData(HeaderData data){
        String res =  DBUtil.setHeaderData(data);
        Map<String,Object> response= new HashMap<>();
        response.put("result",res);
        return  response;
    }
}

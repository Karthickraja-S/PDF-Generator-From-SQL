package com.database;

import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class DBQueryHandler {

    public static DBQueryHandler handler = null;
    private DBQueryHandler(){}
    public static DBQueryHandler getInstance() {
        if(handler == null){
            handler = new DBQueryHandler();
        } return handler;
    }
    private List<String> getColumnNames(ResultSet resultSet) throws Exception{
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        List<String> columnNames = new ArrayList<>();
        for(int ctr = 1; ctr <= columnCount; ctr++) {
            String columnName = metaData.getColumnName(ctr);
            columnNames.add(columnName);
        }
        return columnNames;
    }
    public JSONObject convertResultSetToJSON(ResultSet resultSet) {
        JSONObject jsonObject = new JSONObject();
        try {
            List<String> columnNames = getColumnNames(resultSet);
            jsonObject.put("columnnames", columnNames.toArray());

            List<List<Object>> data = new ArrayList<>();
            while (resultSet.next()) {
                List<Object> rowValue= new ArrayList<>();
                for(int ctr=1; ctr <= columnNames.size(); ctr++ ) {
                    rowValue.add(resultSet.getObject(ctr));
                }
                data.add(rowValue);
            }
            jsonObject.put("values",data.toArray());
            //System.out.println(jsonObject);

        } catch (Exception e) {
            System.out.println(e);
        }
        return jsonObject;
    }
    public JSONObject processSelectQuery(String query) {
        JSONObject result = new JSONObject();
        if(query.toLowerCase().contains("insert into ")
                || query.toLowerCase().contains("update ")
                || query.toLowerCase().contains("create table "))
        {
            result.put("error","Only Select Query is Allowed.");
        }
        Object queryResult = DBUtil.processQuery(query);
        if(queryResult instanceof String) {
            result.put("error","please check the query once and try again..!");
            result.put("actual_error",(String)queryResult);
        }
        if(queryResult instanceof ResultSet) {
            JSONObject data = convertResultSetToJSON((ResultSet) queryResult);
            // at-least we will get column name as result. if exception means only json will be empty.
            if (data.length() == 0) {
                result.put("error", "error in server. please check with admin.");
            } else {
                System.out.println("returning => "+data);
                return data;
            }
        }
        System.out.println("returning => "+result);
        return result;
    }
}

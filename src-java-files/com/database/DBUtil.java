package com.database;

import com.entity.HeaderData;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBUtil {
    private static String url = "jdbc:postgresql://localhost:<PORT_NUMBER>/<DB_NAME>";
    private static String user = "<USER_NAME>";
    private static String password = "<PASSWORD>";

    static {
        try {
            Class.forName("org.postgresql.Driver");
            createTableIfNotExist();
        } catch (ClassNotFoundException e) {
            System.out.println("postgres initialization failed.");
        }
    }

    private static void createTableIfNotExist() {
        try {
            Connection con = DriverManager.getConnection(url,user,password);
            Statement st = con.createStatement();
            String query = "create table if not exists headerdata" +
                    "(" +
                    "id int primary key," +
                    "title varchar(70)," +
                    "address varchar(250)," +
                    "contact_no varchar(10)," +
                    "email_id varchar(50) unique," +
                    "image_data bytea" +
                    ")";
            boolean res = st.execute(query);
            System.out.println(res);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static HeaderData getHeaderData() {
        HeaderData data = new HeaderData();
        try {
            Connection connection = DriverManager.getConnection(url,user,password);
           String query = "select * from headerdata";
           Statement st = connection.createStatement();
            ResultSet result = st.executeQuery(query);
            while(result.next()){
                data.setTitle(result.getString(2));
                data.setAddress(result.getString(3));
                data.setContactNo(result.getString(4));
                data.setMailID(result.getString(5));
                data.setLogo(result.getBytes(6));
            }
        //    System.out.println("data sent -> "+data);  : WORKED FINE
            connection.close();
            return data;
        } catch (Exception e){
            System.out.println(e);
        }
        return data;
    }
    public static boolean isValueExistInHeaderData() {
        HeaderData data = new HeaderData();
        try {
            Connection connection = DriverManager.getConnection(url,user,password);
            String query = "select * from headerdata";
            Statement st = connection.createStatement();
            ResultSet result = st.executeQuery(query);
            while(result.next()){
                return true;
            }
            connection.close();
        } catch (Exception e){
            System.out.println(e);
        }
        return false;
    }
    public static String setHeaderData(HeaderData data) {
        String result = "success";
        try {
            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = null;
            String query = "";
            if(isValueExistInHeaderData()) {
                query = "update headerdata set title=?,address=?,contact_no=?,email_id=?,image_data=? where id=1";
                statement = connection.prepareStatement(query);
                statement.setString(1,data.getTitle());
                statement.setString(2, data.getAddress());
                statement.setString(3,data.getContactNo());
                statement.setString(4, data.getMailID());
                statement.setBytes(5, data.getLogo());
            } else {
                query = "insert into headerdata values(?,?,?,?,?,?)";
                statement = connection.prepareStatement(query);
                statement.setInt(1,1);
                statement.setString(2,data.getTitle());
                statement.setString(3, data.getAddress());
                statement.setString(4,data.getContactNo());
                statement.setString(5, data.getMailID());
                statement.setBytes(6, data.getLogo());
            }

            statement.execute();
        //    System.out.println("data updated successfully -> "+data); ==> WORKED FINE
            connection.close();
            return result;
        } catch (Exception e){
            System.out.println(e);
            result = e.getMessage();
        }
        return result;
    }
    public static Object processQuery(String selectQuery) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet rs =  statement.executeQuery(selectQuery);
            connection.close();
            return rs;
        } catch (Exception e) {
            System.out.println(e);
            return e.getMessage();
        }
    }
}

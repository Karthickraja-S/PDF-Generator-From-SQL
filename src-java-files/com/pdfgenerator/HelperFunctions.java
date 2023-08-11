package com.pdfgenerator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HelperFunctions {
    public static String getDateFormat() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy h:mm a");
        return sdf.format(date);
    }

}

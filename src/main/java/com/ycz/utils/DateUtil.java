package com.ycz.utils;/*
 @author ycz
 @date 2021-10-19-8:59
*/

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static Date getDate(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(date));
        return Timestamp.valueOf(sdf.format(date));
    }


}

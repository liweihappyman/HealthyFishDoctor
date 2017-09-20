package com.healthyfish.healthyfishdoctor.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/2/10.
 */

public class DateTimeUtil {
    public static String getDate(){
        Date date=new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = df.format(date);
        return dateStr;
    }
    public static String getMon(){
        Date date=new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM");
        String dateStr = df.format(date);
        return dateStr;
    }
    public static String getTime(){
        Date date=new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = df.format(date);
        return dateStr;
    }
    public static String getTime(long ts){
        Date date=new Date();
        date.setTime(ts);
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateStr = df.format(date);
        return dateStr;
    }
    public static String getMS(){
        Date date=new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String dateStr = df.format(date);
        return dateStr;
    }
    public static long getLongMs(){
        Date date=new Date();
        return date.getTime();
    }
}

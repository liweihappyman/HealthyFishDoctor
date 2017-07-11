package com.healthyfish.healthyfishdoctor.utils;

/**
 * 描述：
 * 作者：WKJ on 2017/7/5.
 * 邮箱：
 * 编辑：WKJ
 */

public class Utils1 {

    //获取本机时间
    public static String getTime() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        int year = calendar.get(java.util.Calendar.YEAR);
        int month = calendar.get(java.util.Calendar.MONTH);
        int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
        StringBuffer stringBuffer = new StringBuffer().append(year).append("年").append(month).append("月").append(day).append("日");
        String date = stringBuffer.toString();
        return date;
    }
}

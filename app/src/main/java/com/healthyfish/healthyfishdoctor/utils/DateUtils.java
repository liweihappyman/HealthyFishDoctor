package com.healthyfish.healthyfishdoctor.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 描述：日期加减工具类
 * 作者：LYQ on 2017/7/5.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class DateUtils {

    public static String addAndSubtractDate(String ymd, int count){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(System.currentTimeMillis());
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        if(ymd.equals("Y")){
            rightNow.add(Calendar.YEAR, count);//日期加1年
        } else if(ymd.equals("M")) {
            rightNow.add(Calendar.MONTH, count);//日期加1个月
        } else if(ymd.equals("D")) {
            rightNow.add(Calendar.DAY_OF_YEAR, count);//日期加10天
        }
        Date dt1=rightNow.getTime();
        String reStr = sdf.format(dt1);
        return reStr;
    }
}

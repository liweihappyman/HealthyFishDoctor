package com.healthyfish.healthyfishdoctor.utils;


import com.healthyfish.healthyfishdoctor.POJO.BeanMedRec;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;


/**
 * 描述： 日期大小比较类,比较日期的大小（先后），用于全部病历页面对列表进行排序
 * 作者： TMXK on 2017/7/1.
 */

public class ComparatorDate implements Comparator {

    //日期格式
    //初始化的数据SimpleDateFormat sdf = new SimpleDateFormat("现在是yyyy年MM月dd日 HH(hh)时   mm分 ss秒 S毫秒
    // 星期E 今年的第D天  这个月的第F星期   今年的第w个星期   这个月的第W个星期  今天的a k1~24制时间 K0-11小时制时间 z时区");
    //SimpleDateFormat sdf = new SimpleDateFormat("yyyyyyyy-MM-dd HH(hh):mm:ss S E D F w W a k K z");
    //日期大小比较类

    DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
    @Override
    public int compare(Object o, Object t1) {
        BeanMedRec bean1 = (BeanMedRec) o;
        BeanMedRec bean2 = (BeanMedRec) t1;

        Date dt1 = null, dt2 = null;
        try {
            dt1 = df.parse(bean1.getClinicalTime());
            dt2 = df.parse(bean2.getClinicalTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        //if (dt1.before(dt2))
        if (dt1.getTime() < dt2.getTime()) {//大于号小于号决定排序的升降（目前是降序）
            return 1;

        } else {
           return -1;

        }
    }
}
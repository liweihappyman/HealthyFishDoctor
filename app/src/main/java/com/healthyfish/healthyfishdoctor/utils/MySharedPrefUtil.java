package com.healthyfish.healthyfishdoctor.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.healthyfish.healthyfishdoctor.MyApplication;


public class MySharedPrefUtil {
    public static boolean saveKeyValue(String key, String value) {
        try{
            //1.通过Context对象创建一个SharedPreference对象
            //name:sharedpreference文件的名称    mode:文件的操作模式
//			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences sharedPreferences = MyApplication.getContetxt().getSharedPreferences("userinfo.txt", Context.MODE_PRIVATE);
            //2.通过sharedPreferences对象获取一个Editor对象
            SharedPreferences.Editor editor = sharedPreferences.edit();
            //3.往Editor中添加数据
            editor.putString(key, value);
            //4.提交Editor对象
            editor.commit();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getValue(String key){
        try{
            //1.通过Context对象创建一个SharedPreference对象
            SharedPreferences sharedPreferences = MyApplication.getContetxt().getSharedPreferences("userinfo.txt", Context.MODE_PRIVATE);
//			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            //2.通过sharedPreference获取存放的数据
            //key:存放数据时的key   defValue: 默认值,根据业务需求来写
            String value = sharedPreferences.getString(key, "");
            return value;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void remKey(String key){
        try{
            //1.通过Context对象创建一个SharedPreference对象
            //name:sharedpreference文件的名称    mode:文件的操作模式
//			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences sharedPreferences = MyApplication.getContetxt().getSharedPreferences("userinfo.txt", Context.MODE_PRIVATE);
            //2.通过sharedPreferences对象获取一个Editor对象
            SharedPreferences.Editor editor = sharedPreferences.edit();
            //3.往Editor中添加数据
            editor.remove(key);
            //4.提交Editor对象
            editor.commit();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

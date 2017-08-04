package com.healthyfish.healthyfishdoctor;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.zhy.autolayout.config.AutoLayoutConifg;

import org.litepal.LitePal;

/**
 * 描述：MyApplication初始化参数
 * 作者：Wayne on 2017/6/26 16:53
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */

public class MyApplication extends Application{
    public static Context applicationContext;


    @Override
    public void onCreate() {
        super.onCreate();
        //自适应平屏幕
        AutoLayoutConifg.getInstance().useDeviceSize();
        applicationContext = getApplicationContext();
        LitePal.initialize(getApplicationContext());//初始化数据库
    }

    /**
     * 获取全局context
     *
     * @return contetxt
     */
    public static Context getContetxt() {
        return applicationContext;
    }




    public static Handler getApplicationHandler(){
        return applicationHandler;
    }


    /**
     * 服务上传图片成功或者失败Toast提醒用户
     */
    public static  Handler applicationHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x11:
                    Toast.makeText(getContetxt(),"图片上传成功",Toast.LENGTH_SHORT).show();
                    break;
                case 0x12:
                    Toast.makeText(getContetxt(),"图片上传失败",Toast.LENGTH_SHORT).show();
                    break;
                case 0x13:
                    Toast.makeText(getContetxt(),"图片保存成功",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}

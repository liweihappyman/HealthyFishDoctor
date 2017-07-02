package com.healthyfish.healthyfishdoctor;

import android.app.Application;
import android.content.Context;

import com.healthyfish.healthyfishdoctor.utils.HttpsUtils;
import com.healthyfish.healthyfishdoctor.utils.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.healthyfish.healthyfishdoctor.constant.constants.CONNECT_TIMEOUT;
import static com.healthyfish.healthyfishdoctor.constant.constants.READ_TIMEOUT;
import static com.healthyfish.healthyfishdoctor.constant.constants.WRITE_TIMEOUT;

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
        applicationContext = getApplicationContext();
    }

    /**
     * 获取全局context
     *
     * @return contetxt
     */
    public static Context getContetxt() {
        return applicationContext;
    }
}

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

        /**
         * @description OkHttp初始化
         * @author
         *
         */
        // 设置拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // 设置ssl以访问Https
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(MyApplication.getContetxt(), new int[0], R.raw.kangfish, "");
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(CONNECT_TIMEOUT,TimeUnit.SECONDS)//设置连接超时时间
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .hostnameVerifier(HttpsUtils.getHostnameVerifier())
                //.cookieJar(new CookieMangerUtils(applicationContext))//设置cookie保存
                .build();
        OkHttpUtils.initClient(okHttpClient);

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

package com.healthyfish.healthyfishdoctor.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.healthyfish.healthyfishdoctor.MyApplication;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.api.IApiService;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.healthyfish.healthyfishdoctor.constant.Constants.CONNECT_TIMEOUT;
import static com.healthyfish.healthyfishdoctor.constant.Constants.HttpHealthyFishyUrl;
import static com.healthyfish.healthyfishdoctor.constant.Constants.READ_TIMEOUT;
import static com.healthyfish.healthyfishdoctor.constant.Constants.WRITE_TIMEOUT;

/**
 * 描述：Retrofit封装
 * 作者：Wayne on 2017/6/27 20:57
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */

public class RetrofitManagerUtils {

    private IApiService apiService;
    private static Context mContext;
    private static RetrofitManagerUtils mInstance;

    public static String baseUrl = HttpHealthyFishyUrl;

    public static RetrofitManagerUtils getInstance(Context context, String url) {
        if (context != null) {
            mContext = context;
        }
        mInstance = new RetrofitManagerUtils(url);
        return mInstance;
    }

    private RetrofitManagerUtils(String url) {

        if (TextUtils.isEmpty(url)) {
            url = baseUrl;
        }

        /**
         * @description OkHttp初始化
         * @author Wayne
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
                .cookieJar(new CookieMangerUtils(MyApplication.getContetxt()))//设置cookie保存
                .build();

        //这个WKJ正在使用的，如果有冲突改回原来那个，这个先不要删，注释掉，后面再修改
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new ReceivedCookiesInterceptor(MyApplication.getContetxt()))
//                .addInterceptor(new AddCookiesInterceptor(MyApplication.getContetxt(), null))
//
//                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
//                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
//                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
//                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
//                .hostnameVerifier(HttpsUtils.getHostnameVerifier())
//                .cookieJar(new CookieMangerUtils(MyApplication.getContetxt()))//设置cookie保存
//                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url)
                .build();
        apiService = retrofit.create(IApiService.class);
    }

    /**
     * @description 使用new Subscribe<ResponseBody>实现回调方法
     * @author Wayne
     *
     */
    public void getHealthyInfoByRetrofit(RequestBody requestBody, Subscriber<ResponseBody> subscriber) {
        apiService.getHealthyInfoByRetrofit(requestBody)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取药典相关
     * @param requestBody
     * @param subscriber
     */
    public void getPharmacopoeiaByRetrofit(RequestBody requestBody, Subscriber<ResponseBody> subscriber) {
        apiService.getHealthyInfoByRetrofit(requestBody)
                .subscribe(subscriber);
    }



    /**
     * 逐个获取病历，必须执行在相同的线程历里面，不然获取到的数据有可能会乱
     *
     * @param requestBody
     * @param subscriber
     * @author Wkj
     */
    public void getMedRecByRetrofit(RequestBody requestBody, Subscriber<ResponseBody> subscriber) {
        apiService.getHealthyInfoByRetrofit(requestBody)
                .subscribe(subscriber);
    }


    /**
     * 上传图片文件(多张图片)
     *
     * @param compressFlies 要上传的压缩后的图片文件
     * @author Wkj
     */
    public void uploadFilesRetrofit(List<File> compressFlies, int position, Subscriber<ResponseBody> subscriber) {
        Map<String, RequestBody> photos = new HashMap<>();
        if (compressFlies.size() > 0) {
            for (int i = 0; i < compressFlies.size(); i++) {
                photos.put("photos" + position + "\"; filename=\"icon.png", RequestBody.create(MediaType.parse("multipart/form-data"), compressFlies.get(i)));
                Log.i("图片上传","photos" + position + "\"; filename=\"icon.png");
            }
        }
        //运行在服务线程
        apiService.uploadFiles(photos)
                .subscribe(subscriber);
    }














}

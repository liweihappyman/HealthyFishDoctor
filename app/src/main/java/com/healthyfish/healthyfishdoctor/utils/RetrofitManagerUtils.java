package com.healthyfish.healthyfishdoctor.utils;

import android.content.Context;
import android.text.TextUtils;

import com.healthyfish.healthyfishdoctor.POJO.BeanBaseReq;
import com.healthyfish.healthyfishdoctor.api.IApiService;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.healthyfish.healthyfishdoctor.constant.constants.HttpHealthyFishyUrl;

/**
 * 描述：Retrofit封装
 * 作者：Wayne on 2017/6/27 20:57
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */

public class RetrofitManagerUtils {

    private IApiService apiService;
    private static Context mContext;
    private static RetrofitManagerUtils mNewInstance;

    BeanBaseReq beanBaseReq = new BeanBaseReq();
    public static String baseUrl = HttpHealthyFishyUrl;
    private OkHttpClient okHttpClient = OkHttpUtils.getOkHttpClient();

/*    private static class SingletonHolder {
        private static RetrofitManagerUtils INSTANCE = new RetrofitManagerUtils(mContext);
    }*/

    public static RetrofitManagerUtils getInstance(Context context, String url) {
        if (context != null) {
            mContext = context;
        }
        mNewInstance = new RetrofitManagerUtils(context, url);
        return mNewInstance;
    }

    private RetrofitManagerUtils(Context context, String url) {

        if (TextUtils.isEmpty(url)) {
            url = baseUrl;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url)
                .build();
        apiService = retrofit.create(IApiService.class);
    }

    public void getHealthyInfoByRetrofit(BeanBaseReq beanBaseReq, Subscriber<ResponseBody> subscriber) {
        apiService.getHealthyInfoByRetrofit(beanBaseReq)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    public void getHealthyInfoByDemoGetKey(BeanBaseReq beanBaseReq, String path, Subscriber<ResponseBody> subscriber){
        apiService.getHealthyInfoByDemoGetKey(beanBaseReq, path)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }





}

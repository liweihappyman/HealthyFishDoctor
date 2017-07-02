package com.healthyfish.healthyfishdoctor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.healthyfish.healthyfishdoctor.POJO.BeanBaseReq;
import com.healthyfish.healthyfishdoctor.POJO.BeanDoctorListReq;
import com.healthyfish.healthyfishdoctor.POJO.BeanHomeImgSlideReq;
import com.healthyfish.healthyfishdoctor.POJO.BeanHomeImgSlideResp;
import com.healthyfish.healthyfishdoctor.POJO.BeanHomeImgSlideRespItem;
import com.healthyfish.healthyfishdoctor.POJO.BeanPageReq;
import com.healthyfish.healthyfishdoctor.POJO.BeanUserSmsAuthReq;
import com.healthyfish.healthyfishdoctor.POJO.BeanWeChatNews_All;
import com.healthyfish.healthyfishdoctor.api.IApiService;
import com.healthyfish.healthyfishdoctor.utils.RetrofitManagerUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
import rx.subscriptions.CompositeSubscription;

import static com.healthyfish.healthyfishdoctor.constant.constants.HttpHealthyFishyUrl;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnGetInfoByPackageRetrofit)
    Button btnGetInfoByPackageRetrofit;
    @BindView(R.id.btnGetInfoByNormalRetrofit)
    Button btnGetInfoByNormalRetrofit;
    @BindView(R.id.imvGetImageByUrl)
    ImageView imvGetImageByUrl;


    private CompositeSubscription mSubscriptions = new CompositeSubscription();

    final BeanBaseReq beanBaseReq = new BeanBaseReq();
    final BeanPageReq beanPageReq = new BeanPageReq();
    final BeanDoctorListReq beanDoctorListReq = new BeanDoctorListReq();
    final BeanUserSmsAuthReq beanUserSmsAuthReq = new BeanUserSmsAuthReq();
    final BeanHomeImgSlideReq beanHomeImgSlideReq = new BeanHomeImgSlideReq();
    final BeanWeChatNews_All beanWeChatNews_all = new BeanWeChatNews_All();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnGetInfoByPackageRetrofit,
            R.id.btnGetInfoByNormalRetrofit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnGetInfoByPackageRetrofit:
                GetInfoByPackageRetrofit();
                break;

            case R.id.btnGetInfoByNormalRetrofit:
                /*GetInfoByNormalRetrofit();*/
                break;

        }
    }


    private void GetInfoByPackageRetrofit() {

        // 将请求类转换为Json字符
        String req = new Gson().toJson(beanHomeImgSlideReq);
        // 将Json字符转换为retrofit api所需的请求体
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), req);
        // 获取封装工具类的实例getInstance，实例中包含上下文和服务器地址，并调用api方法（请求体， 观察者回调方法）
        RetrofitManagerUtils.getInstance(MainActivity.this, HttpHealthyFishyUrl)
                .getHealthyInfoByRetrofit(body, new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        List<BeanHomeImgSlideRespItem> imgs = new ArrayList<BeanHomeImgSlideRespItem>();
                        try {
                            // 将响应体（Json对象）转换为实体类（响应）对象，如果没有实体类对象可以直接调用
                            BeanHomeImgSlideResp obj = new Gson().fromJson(responseBody.string(), BeanHomeImgSlideResp.class);
                            // 如果返回数据中包含需要上传的图片url，通过实体类方法获取
                            imgs = obj.getImgList();
                            for (BeanHomeImgSlideRespItem img : imgs) {
                                Log.e("Wayne", img.getImg());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Glide.with(getApplicationContext()).load(HttpHealthyFishyUrl + imgs.get(1).getImg()).into(imvGetImageByUrl);
                    }
                });

    }


    /*private void GetInfoByNormalRetrofit() {

        String url = null;

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(HttpHealthyFishyUrl)
                .build();

        IApiService apiService = retrofit.create(IApiService.class);

        String req = new Gson().toJson(beanHomeImgSlideReq);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), req);

        mSubscriptions.add(apiService.getHealthyInfoByRetrofit(body)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {

                        try {
                            BeanHomeImgSlideResp obj = new Gson().fromJson(responseBody.string(), BeanHomeImgSlideResp.class);
                            List<BeanHomeImgSlideRespItem> imgs = obj.getImgList();
                            for (BeanHomeImgSlideRespItem img : imgs) {
                                Log.e("Wayne", img.getImg());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }));

        url = HttpHealthyFishyUrl + "/demo/downloadFile/upload/20170318/19411489838119199.jpg";

        Glide.with(this).load(url).into(imvGetImageByUrl);

    }*/


}

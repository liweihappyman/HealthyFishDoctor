package com.healthyfish.healthyfishdoctor.api;

import com.healthyfish.healthyfishdoctor.POJO.BeanBaseReq;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 描述：创建API接口
 * 　　在retrofit中通过一个Java接口作为http请求的api接口。
 * 作者：Wayne on 2017/6/26 19:59
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */

public interface IApiService {

    @POST("demo/TestServlet")
    Observable<ResponseBody> getHealthyInfoByRetrofit(@Body RequestBody req);

/*    @POST("{path}")
    Observable<ResponseBody> getHealthyInfoByDemoGetKey(@Path("path") String path);

    @POST("demo/TestServlet")
    Call<BeanDoctorListResp> getHealthyInfoByEasyRetrofit(@Body BeanBaseReq beanBaseReq);*/
    /**
     * 多张图片上传
     * @return
     */
    @Multipart
    @POST("demo/uploadFile")
    Observable<ResponseBody> uploadFiles(@PartMap Map<String, RequestBody> imgs);



}

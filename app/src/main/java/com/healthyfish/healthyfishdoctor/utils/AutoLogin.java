package com.healthyfish.healthyfishdoctor.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.healthyfish.healthyfishdoctor.MyApplication;
import com.healthyfish.healthyfishdoctor.POJO.BeanBaseResp;
import com.healthyfish.healthyfishdoctor.POJO.BeanUserLoginReq;
import com.healthyfish.healthyfishdoctor.utils.mqtt_utils.MqttUtil;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * 描述：为了保持与服务器的会话状态，与服务器交互的会话（有关sessionid的），最好先默默地登录一下，
 * 可以理解为用户跟该sessionid绑定，不然有可能获取不到数据，或者相应login first；
 * 作者：WKJ on 2017/8/1.
 * 邮箱：
 * 编辑：WKJ
 */

public class AutoLogin {
    /**
     * 自动登录
     */
    public static void autoLogin() {
        String user = MySharedPrefUtil.getValue("user");
        String sid = MySharedPrefUtil.getValue("sid");
        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(sid)) {
            final BeanUserLoginReq beanUserLoginReq = JSON.parseObject(user, BeanUserLoginReq.class);
            beanUserLoginReq.setMobileNo(beanUserLoginReq.getMobileNo());//号码
            beanUserLoginReq.setAct(BeanUserLoginReq.class.getSimpleName());//设置操作类型，不然服务器不知道
            beanUserLoginReq.setPwdSHA256(beanUserLoginReq.getPwdSHA256());//密码
            RetrofitManagerUtils.getInstance(MyApplication.getContetxt(), null)
                    .getHealthyInfoByRetrofit(OkHttpUtils.getRequestBody(beanUserLoginReq), new Subscriber<ResponseBody>() {
                        @Override
                        public void onCompleted() {


                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(MyApplication.getContetxt(), "自动登录失败", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            String str = null;
                            try {
                                str = responseBody.string();
                                BeanBaseResp beanBaseResp = JSON.parseObject(str, BeanBaseResp.class);
                                int code = beanBaseResp.getCode();
                                if (code >= 0) {


                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }
}

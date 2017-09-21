package com.healthyfish.healthyfishdoctor.ui.activity.login_register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.healthyfish.healthyfishdoctor.MyApplication;
import com.healthyfish.healthyfishdoctor.POJO.BeanBaseKeySetReq;
import com.healthyfish.healthyfishdoctor.POJO.BeanBaseResp;
import com.healthyfish.healthyfishdoctor.POJO.BeanDoctor;
import com.healthyfish.healthyfishdoctor.POJO.BeanPersonalInformation;
import com.healthyfish.healthyfishdoctor.POJO.BeanSessionIdReq;
import com.healthyfish.healthyfishdoctor.POJO.BeanSessionIdResp;
import com.healthyfish.healthyfishdoctor.POJO.BeanUserLoginReq;
import com.healthyfish.healthyfishdoctor.POJO.BeanUserRegisterReq;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.ui.activity.BaseActivity;
import com.healthyfish.healthyfishdoctor.utils.AutoLogin;
import com.healthyfish.healthyfishdoctor.utils.MySharedPrefUtil;
import com.healthyfish.healthyfishdoctor.utils.MyToast;
import com.healthyfish.healthyfishdoctor.utils.OkHttpUtils;
import com.healthyfish.healthyfishdoctor.utils.RetrofitManagerUtils;
import com.healthyfish.healthyfishdoctor.utils.Sha256;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscriber;

import static com.healthyfish.healthyfishdoctor.constant.Constants.HttpHealthyFishyUrl;

/**
 * 描述：注册填写昵称的密码页面
 * 作者：LYQ on 2017/7/7.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class RegisterPassword extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_input_password)
    EditText etInputPassword;
    @BindView(R.id.et_verify_password)
    EditText etVerifyPassword;
    @BindView(R.id.btn_next)
    Button btnNext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_name_and_password);
        ButterKnife.bind(this);
        initToolBar(toolbar,toolbarTitle,"注册");

    }

    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        register();
    }

    /**
     *     注册操作
     */
    private void register() {
        if (!etInputPassword.getText().toString().equals(etVerifyPassword.getText().toString())) {
            Toast.makeText(this, "输入密码不相同", Toast.LENGTH_LONG).show();
        }
//        else if (TextUtils.isEmpty(etInputName.getText().toString().trim())) {
//            Toast.makeText(this, "请填写您的姓名", Toast.LENGTH_LONG).show();
//        }
        else {
//            name = etInputName.getText().toString().trim();
            //注册请求的bean
            final BeanUserRegisterReq beanUserRegisterReq = (BeanUserRegisterReq) getIntent().getSerializableExtra("user");
            beanUserRegisterReq.setAct(BeanUserRegisterReq.class.getSimpleName());
            Log.i("电话号码：", beanUserRegisterReq.getMobileNo());
            beanUserRegisterReq.setPwdSHA256(Sha256.getSha256(etInputPassword.getText().toString()));
            beanUserRegisterReq.setType(0);//新注册用户
            //待保存的bean，如果登录成功，将会由sharepreference保存如下bean
            BeanUserLoginReq beanUserLoginReq = new BeanUserLoginReq();
            beanUserLoginReq.setMobileNo(beanUserRegisterReq.getMobileNo());
            beanUserLoginReq.setPwdSHA256(Sha256.getSha256(beanUserRegisterReq.getPwdSHA256()));
            final String user = JSON.toJSONString(beanUserLoginReq);

            RetrofitManagerUtils.getInstance(this, null)
                    .getHealthyInfoByRetrofit(OkHttpUtils.getRequestBody(beanUserRegisterReq), new Subscriber<ResponseBody>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(RegisterPassword.this, "注册失败，请检查网络环境", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            String str = null;
                            try {
                                str = responseBody.string();
                                Log.i("LYQ", "注册响应：" + str);
                                BeanBaseResp beanBaseResp = JSON.parseObject(str, BeanBaseResp.class);
                                int code = beanBaseResp.getCode();
                                if (code == 0) {
                                    Toast.makeText(RegisterPassword.this, "注册成功", Toast.LENGTH_LONG).show();
                                    MySharedPrefUtil.saveKeyValue("user", user);
                                    //————————————————————————————————————————

                                    //saveDataToNetwork(beanUserRegisterReq.getMobileNo());//保存个人信息到服务器及本地数据库
                                    getSidAndAutoLogin();//获取Sid后自动登录加载用户信息

                                    Intent intent = new Intent(RegisterPassword.this, RegisterSuccess.class);
                                    startActivity(intent);
                                    finish();
                                    //—————————————————————————————————————————————
                                } else {
                                    Toast.makeText(RegisterPassword.this, "注册失败", Toast.LENGTH_LONG).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }

    /**
     * 将个人信息同步保存到服务器
     */
//    private void saveDataToNetwork(String uid) {
//        final String key = "certReq_" + uid;
//        final BeanDoctor beanDoctor = new BeanDoctor();
//        beanDoctor.setType(2);
//        beanDoctor.setName(name);
//
//        String jsonReq = JSON.toJSONString(beanDoctor);
//
//        Log.i("LYQ", "jsonReq:" + jsonReq);
//        BeanBaseKeySetReq beanBaseKeySetReq = new BeanBaseKeySetReq();
//        beanBaseKeySetReq.setKey(key);
//        beanBaseKeySetReq.setValue(jsonReq);
//
//        RetrofitManagerUtils.getInstance(MyApplication.getContetxt(), null).getHealthyInfoByRetrofit(OkHttpUtils.getRequestBody(beanBaseKeySetReq), new Subscriber<ResponseBody>() {
//            String resp = null;
//
//            @Override
//            public void onCompleted() {
//                if (!TextUtils.isEmpty(resp)) {
//                    BeanBaseResp beanBaseResp = JSON.parseObject(resp, BeanBaseResp.class);
//                    if (beanBaseResp.getCode() == 0) {
//                        boolean isSave = personalInformation.saveOrUpdate("key = ?", key);//将个人信息保存到数据库
//                        if (!isSave) {
//                            MyToast.showToast(RegisterPassword.this, "保存个人信息失败");
//                        }
//                        //EventBus.getDefault().post(personalInformation);//发送消息提醒刷新个人中心的个人信息
//                    } else {
//                        MyToast.showToast(RegisterPassword.this, "上传个人信息失败");
//                        //EventBus.getDefault().post(new BeanPersonalInformation());//发送消息提醒刷新个人中心的个人信息
//                    }
//                } else {
//                    MyToast.showToast(RegisterPassword.this, "上传个人信息失败");
//                    //EventBus.getDefault().post(new BeanPersonalInformation());//发送消息提醒刷新个人中心的个人信息
//                }
//                Log.i("LYQ", "上传请求onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                MyToast.showToast(RegisterPassword.this, "上传个人信息失败");
//                //EventBus.getDefault().post(new BeanPersonalInformation());//发送消息提醒刷新个人中心的个人信息
//            }
//
//            @Override
//            public void onNext(ResponseBody responseBody) {
//                try {
//                    resp = responseBody.string();
//                    Log.i("LYQ", "上传请求返回：" + resp);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    /**
     * 获取Sid后自动登录加载用户信息
     */
    private void getSidAndAutoLogin() {
        RetrofitManagerUtils.getInstance(MyApplication.getContetxt(), HttpHealthyFishyUrl)
                .getHealthyInfoByRetrofit(OkHttpUtils.getRequestBody(new BeanSessionIdReq()), new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
//                        String user = MySharedPrefUtil.getValue("user");
//                        if (!TextUtils.isEmpty(user)) {
//                            MqttUtil.startAsync();
//                        }
                        //EventBus.getDefault().post(new BeanPersonalInformation(true));
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            BeanSessionIdResp obj = new Gson().fromJson(responseBody.string(), BeanSessionIdResp.class);
                            Log.e("从服务器获取sid", obj.getSid());
                            MySharedPrefUtil.saveKeyValue("sid", obj.getSid());
                            AutoLogin.autoLogin();//获取到sid后自动登录
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

}

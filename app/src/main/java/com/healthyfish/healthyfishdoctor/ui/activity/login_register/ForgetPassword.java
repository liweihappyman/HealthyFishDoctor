package com.healthyfish.healthyfishdoctor.ui.activity.login_register;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.healthyfish.healthyfishdoctor.POJO.BeanUserRegisterReq;
import com.healthyfish.healthyfishdoctor.POJO.BeanUserRegisterResp;
import com.healthyfish.healthyfishdoctor.POJO.BeanUserSmsAuthReq;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.ui.activity.BaseActivity;
import com.healthyfish.healthyfishdoctor.utils.OkHttpUtils;
import com.healthyfish.healthyfishdoctor.utils.RetrofitManagerUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * 描述：忘记密码页面
 * 作者：LYQ on 2017/7/7.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class ForgetPassword extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_input_phone)
    EditText etInputPhone;
    @BindView(R.id.et_input_validation_code)
    EditText etInputValidationCode;
    @BindView(R.id.bt_get_code)
    Button btGetCode;
    @BindView(R.id.btn_next)
    Button btnNext;

    private MyCount count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        initToolBar(toolbar,toolbarTitle,"忘记密码");
    }

    @OnClick({R.id.bt_get_code, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_get_code:
                //点击获取验证码，给手机号发送验证码，显示重发倒计时，发送之前可能要判断该手机号是否已经注册，未注册则提示
                if (etInputPhone.getText().toString().length()!=11){
                    Toast.makeText(ForgetPassword.this,"请检查手机号码",Toast.LENGTH_LONG).show();
                }else {
                    //点击获取验证码，给手机号发送验证码，显示重发倒计时，发送之前可能要判断该手机号是否已经注册，未注册则提示
                    countTime();
                    getVerifyRequest();
                }
                break;
            case R.id.btn_next:
                //需要传用户标识，至少传手机号，验证码过去,可能要判断验证码是否正确才能跳转到下一页面
                judgeAndJumpToActivity();
                break;
        }
    }

    //倒计时
    class MyCount extends CountDownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            btGetCode.setText("点击重新发送");
            btGetCode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btGetCode.setText("倒计时(" + millisUntilFinished / 1000 + ")");
            btGetCode.setClickable(false);
        }
    }

    //粗劣判断号码和验证码是否正确，并做相应的跳转
    private void judgeAndJumpToActivity() {
        if (etInputPhone.getText().toString().length()==11&&etInputValidationCode.getText().toString().length()==6) {
            BeanUserRegisterReq beanUserRegisterReq = new BeanUserRegisterReq();
            beanUserRegisterReq.setType(1);//1.表示修改密码
            beanUserRegisterReq.setMobileNo(etInputPhone.getText().toString());//手机号
            beanUserRegisterReq.setVerifyCode(etInputValidationCode.getText().toString());//验证码
            //把手机号码和验证码一起传到下一个页面
            Intent intent = new Intent(this, ChangePassword.class);
            intent.putExtra("find_password", beanUserRegisterReq);
            startActivity(intent);
        }else if(etInputPhone.getText().toString().length()!=11){
            Toast.makeText(ForgetPassword.this,"请检查号码是否有误",Toast.LENGTH_SHORT).show();
        }else if (etInputValidationCode.getText().toString().length()!=6){
            Toast.makeText(ForgetPassword.this,"验证码不正确",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 发送短信获取验证码
     */
    private void getVerifyRequest() {
        BeanUserSmsAuthReq beanUserSmsAuthReq = new BeanUserSmsAuthReq();
        beanUserSmsAuthReq.setMobileNo(etInputPhone.getText().toString());
        beanUserSmsAuthReq.setType(1);//找回密码
        RetrofitManagerUtils.getInstance(this, null)
                .getHealthyInfoByRetrofit(OkHttpUtils.getRequestBody(beanUserSmsAuthReq), new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(ForgetPassword.this, "获取验证码失败，请检查网络环境", Toast.LENGTH_LONG).show();
                        count.onFinish();
                    }
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        String str = null;
                        try {
                            str = responseBody.string();
                            BeanUserRegisterResp beanUserRegisterResp = JSON.parseObject(str, BeanUserRegisterResp.class);
                            int code = beanUserRegisterResp.getCode();
                            judgeAndShowToast(code);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }


    /**
     * 根据返货状态码做出提示
     * @param code 相应状态码
     */
    private void judgeAndShowToast(int code) {
        if (code == 0) {
            Toast.makeText(ForgetPassword.this, "验证码已发送，请耐心等待", Toast.LENGTH_LONG).show();
        }else if (code == -2){
            Toast.makeText(ForgetPassword.this, "用户不存在，请先注册", Toast.LENGTH_LONG).show();
        } else if (code == -3){
            Toast.makeText(ForgetPassword.this, "操作太频繁了...", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(ForgetPassword.this, "验证码获取失败", Toast.LENGTH_LONG).show();
            count.onFinish();
        }
    }


    //开启线程计时
    private void countTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        count = new MyCount(30000, 1000);
                        count.start();
                    }
                });
            }
        }).start();
    }
}

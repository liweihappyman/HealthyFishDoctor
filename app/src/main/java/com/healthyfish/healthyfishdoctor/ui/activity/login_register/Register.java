package com.healthyfish.healthyfishdoctor.ui.activity.login_register;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
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
import com.healthyfish.healthyfishdoctor.ui.view.register.IRegisterView;
import com.healthyfish.healthyfishdoctor.utils.OkHttpUtils;
import com.healthyfish.healthyfishdoctor.utils.RetrofitManagerUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * 描述：注册Activity
 * 作者：Wayne on 2017/6/28 16:11
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */
public class Register extends AppCompatActivity implements IRegisterView{

    @BindView(R.id.et_input_phone_number)
    EditText etInputPhoneNumber;
    @BindView(R.id.et_input_validation_code)
    EditText etInputValidationCode;
    @BindView(R.id.bt_get_code)
    Button btGetCode;
    @BindView(R.id.bt_register)
    Button btRegister;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;

    private MyCount count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_get_code, R.id.bt_register, R.id.tv_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_get_code:
                //发送短信验证码
                if (getRegisterUserName().length() != 11) {
                    Toast.makeText(Register.this, "请检查手机号码", Toast.LENGTH_LONG).show();
                } else {
                    countTime();//30秒计时
                    getVerifyRequest(); //发送短信验证码
                }
                break;
            case R.id.bt_register:
                //注册操作
                register();
                break;
            case R.id.tv_agreement:
                //阅读服务条款及协议
                break;
        }
    }

    /**
     *     注册操作
     */
    private void register() {
        if (getRegisterUserName().length() == 11 && getRegisterVerificationCode().length() == 6) {
            BeanUserRegisterReq beanUserRegisterReq = new BeanUserRegisterReq();
            beanUserRegisterReq.setMobileNo(getRegisterUserName());
            beanUserRegisterReq.setVerifyCode(getRegisterVerificationCode());
            Intent intent = new Intent(this, RegisterPassword.class);
            intent.putExtra("user", beanUserRegisterReq);
            startActivity(intent);
        } else if (getRegisterUserName().length() != 11) {
            Toast.makeText(Register.this, "请检查手机号码", Toast.LENGTH_LONG).show();
        } else if (getRegisterVerificationCode().length() != 6) {
            Toast.makeText(Register.this, "请检验证码", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 发送短信获取验证码
     */
    private void getVerifyRequest() {
        BeanUserSmsAuthReq beanUserSmsAuthReq = new BeanUserSmsAuthReq();
        beanUserSmsAuthReq.setMobileNo(getRegisterUserName());
        beanUserSmsAuthReq.setType(0);
        RetrofitManagerUtils.getInstance(this, null)
                .getHealthyInfoByRetrofit(OkHttpUtils.getRequestBody(beanUserSmsAuthReq), new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(Register.this, "获取验证码失败，请检查网络环境", Toast.LENGTH_LONG).show();
                        count.onFinish();
                    }
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        String str = null;
                        try {
                            str = responseBody.string();
                            BeanUserRegisterResp beanUserRegisterResp = JSON.parseObject(str, BeanUserRegisterResp.class);
                            if (beanUserRegisterResp.getCode() == 0) {
                                Toast.makeText(Register.this, "验证码发送成功，请耐心等待", Toast.LENGTH_LONG).show();
                            } else if (beanUserRegisterResp.getCode() == -2) {
                                Toast.makeText(Register.this, "该号码已经注册了", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(Register.this, "验证码获取失败", Toast.LENGTH_LONG).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     *     开启线程计时
     */
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


    /**
     *     倒计时
     */
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

    @Override
    public void showRegisterProgressBar() {

    }

    @Override
    public void hideRegisterProgressBar() {

    }

    @Override
    public String getRegisterUserName() {
        return etInputPhoneNumber.getText().toString().trim();
    }

    @Override
    public String getRegisterVerificationCode() {
        return etInputValidationCode.getText().toString().trim();
    }


    @Override
    public void toRegisterActivity() {

    }

    @Override
    public void showRegisterFailedError() {

    }
}

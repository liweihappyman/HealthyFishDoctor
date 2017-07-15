package com.healthyfish.healthyfishdoctor.ui.activity.login_register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.ui.view.register.IRegisterView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
                break;
            case R.id.bt_register:
                //注册操作
                Intent intent = new Intent(this, RegisterPassword.class);
                startActivity(intent);
                break;
            case R.id.tv_agreement:
                //阅读服务条款及协议
                break;
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

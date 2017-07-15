package com.healthyfish.healthyfishdoctor.ui.activity.login_register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.ui.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.et_input_password)
    EditText etInputPassword;
    @BindView(R.id.et_input_validation_code)
    EditText etInputValidationCode;
    @BindView(R.id.bt_get_code)
    Button btGetCode;
    @BindView(R.id.btn_next)
    Button btnNext;

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
                break;
            case R.id.btn_next:
                //需要传用户标识，至少传手机号，验证码过去,可能要判断验证码是否正确才能跳转到下一页面
                Intent intent = new Intent(this, ChangePassword.class);
                startActivity(intent);
                break;
        }
    }
}

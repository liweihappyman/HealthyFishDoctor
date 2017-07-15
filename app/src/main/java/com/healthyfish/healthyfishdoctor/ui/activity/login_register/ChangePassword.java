package com.healthyfish.healthyfishdoctor.ui.activity.login_register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.ui.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：修改密码页面
 * 作者：LYQ on 2017/7/7.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class ChangePassword extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_input_password)
    EditText etInputPassword;
    @BindView(R.id.et_verify_password)
    EditText etVerifyPassword;
    @BindView(R.id.bt_done)
    Button btDone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        initToolBar(toolbar,toolbarTitle,"修改密码");

    }

    @OnClick(R.id.bt_done)
    public void onViewClicked() {
        //需要判断两次输入的密码是否一致，并上传服务器
        Intent intent = new Intent(this, ChangePasswordSuccess.class);
        startActivity(intent);
    }
}

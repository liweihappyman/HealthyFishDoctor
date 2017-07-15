package com.healthyfish.healthyfishdoctor.ui.activity.login_register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.ui.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：注册填写身份证号和选择性别页面
 * 作者：LYQ on 2017/7/7.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class RegisterInformation extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_input_idCart)
    EditText etInputIdCart;
    @BindView(R.id.rbtn_male)
    RadioButton rbtnMale;
    @BindView(R.id.rbtn_female)
    RadioButton rbtnFemale;
    @BindView(R.id.rgp_gender)
    RadioGroup rgpGender;
    @BindView(R.id.btn_next)
    Button btnNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_identity_information);
        ButterKnife.bind(this);
        initToolBar(toolbar,toolbarTitle,"注册");
    }

    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        Intent intent = new Intent(this, RegisterSuccess.class);
        startActivity(intent);
    }
}

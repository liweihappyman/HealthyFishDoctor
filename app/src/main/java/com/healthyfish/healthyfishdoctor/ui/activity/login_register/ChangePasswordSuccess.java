package com.healthyfish.healthyfishdoctor.ui.activity.login_register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;


import com.healthyfish.healthyfishdoctor.MainActivity;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.ui.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：
 * 作者：LYQ on 2017/7/7.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class ChangePasswordSuccess extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_back_home)
    TextView tvBackHome;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_success);
        ButterKnife.bind(this);
        initToolBar(toolbar,toolbarTitle,"修改成功");
    }

    @OnClick(R.id.tv_back_home)
    public void onViewClicked() {
        //回到首页
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

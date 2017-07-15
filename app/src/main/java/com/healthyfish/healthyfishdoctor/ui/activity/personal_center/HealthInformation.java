package com.healthyfish.healthyfishdoctor.ui.activity.personal_center;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.ui.activity.BaseActivity;


import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 描述：个人中心我的健康信息页面
 * 作者：LYQ on 2017/7/7.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class HealthInformation extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.civ_head_portrait)
    CircleImageView civHeadPortrait;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_information);
        ButterKnife.bind(this);
        initToolBar(toolbar,toolbarTitle,"我的健康信息");
        Glide.with(this).load("http://wmtp.net/wp-content/uploads/2017/02/0227_weimei01_1.jpeg").into(civHeadPortrait);
    }
}

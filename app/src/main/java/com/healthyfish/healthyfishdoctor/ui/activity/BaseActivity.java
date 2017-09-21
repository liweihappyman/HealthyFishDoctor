package com.healthyfish.healthyfishdoctor.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.utils.AutoLogin;
import com.healthyfish.healthyfishdoctor.utils.MySharedPrefUtil;
import com.healthyfish.healthyfishdoctor.utils.mqtt_utils.MqttUtil;


/**
 * 描述：Activity的基类，用来封装一些相同的方法
 * 作者：LYQ on 2017/7/5.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("当前活动", getClass().getSimpleName());
    }

    /**
     * 初始化ToolBar
     */
    public void initToolBar(Toolbar toolbar, TextView tvTitle,String title) {
        toolbar.setTitle("");//设置不显示应用名
        tvTitle.setText(title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back_icon);
        }
    }

    /**
     * 返回按钮的监听
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        String user = MySharedPrefUtil.getValue("user");
        String sid = MySharedPrefUtil.getValue("sid");
        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(sid)) {
            AutoLogin.autoLogin();
            MqttUtil.startAsync();
        }
    }
}

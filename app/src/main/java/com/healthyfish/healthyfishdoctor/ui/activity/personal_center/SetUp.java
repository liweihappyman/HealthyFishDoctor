package com.healthyfish.healthyfishdoctor.ui.activity.personal_center;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.alibaba.fastjson.JSON;
import com.healthyfish.healthyfishdoctor.MainActivity;
import com.healthyfish.healthyfishdoctor.MyApplication;
import com.healthyfish.healthyfishdoctor.POJO.BeanBaseResp;
import com.healthyfish.healthyfishdoctor.POJO.BeanInspectionReport;
import com.healthyfish.healthyfishdoctor.POJO.BeanMedRec;
import com.healthyfish.healthyfishdoctor.POJO.BeanMedRecUser;
import com.healthyfish.healthyfishdoctor.POJO.BeanPersonalInformation;
import com.healthyfish.healthyfishdoctor.POJO.BeanPrescriptiom;
import com.healthyfish.healthyfishdoctor.POJO.BeanUserLoginReq;
import com.healthyfish.healthyfishdoctor.POJO.BeanUserLogoutReq;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.eventbus.LoginEventBus;
import com.healthyfish.healthyfishdoctor.ui.activity.BaseActivity;
import com.healthyfish.healthyfishdoctor.ui.activity.login_register.ForgetPassword;
import com.healthyfish.healthyfishdoctor.utils.MySharedPrefUtil;
import com.healthyfish.healthyfishdoctor.utils.OkHttpUtils;
import com.healthyfish.healthyfishdoctor.utils.RetrofitManagerUtils;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * 描述：个人中心设置页面
 * 作者：LYQ on 2017/7/7.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class SetUp extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.switch_message_notification)
    ToggleButton switchMessageNotification;
    @BindView(R.id.lly_message_notification)
    AutoLinearLayout llyMessageNotification;
    @BindView(R.id.iv_change_password)
    ImageView ivChangePassword;
    @BindView(R.id.lly_change_password)
    AutoLinearLayout llyChangePassword;
    @BindView(R.id.iv_update_version)
    ImageView ivUpdateVersion;
    @BindView(R.id.lly_update_version)
    AutoLinearLayout llyUpdateVersion;
    @BindView(R.id.bt_login_out)
    Button btLoginOut;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);
        ButterKnife.bind(this);
        initToolBar(toolbar, toolbarTitle, "设置");
    }

    @OnClick({R.id.lly_change_password, R.id.bt_login_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lly_change_password:
                Intent intent = new Intent(this, ForgetPassword.class);
                startActivity(intent);
                break;
            case R.id.bt_login_out:
                loginOut();
                break;
        }
    }

    /**
     * 退出登录
     */
    private void loginOut() {

        String user = MySharedPrefUtil.getValue("user");

        if (TextUtils.isEmpty(user)) {
            Toast.makeText(this, "您还没有登录哟", Toast.LENGTH_LONG).show();

        } else {
            BeanUserLoginReq beanUserLoginReq = JSON.parseObject(user, BeanUserLoginReq.class);

            BeanUserLogoutReq beanUserLogoutReq = new BeanUserLogoutReq();
            if (beanUserLoginReq != null) {//防止空引用炸锅
                beanUserLogoutReq.setMobileNo(beanUserLoginReq.getMobileNo());
                beanUserLogoutReq.setPwdSHA256(beanUserLoginReq.getPwdSHA256());
            }

            RetrofitManagerUtils.getInstance(this, null).getHealthyInfoByRetrofit(OkHttpUtils.getRequestBody(beanUserLogoutReq), new Subscriber<ResponseBody>() {

                String logoutResp = "";

                @Override
                public void onCompleted() {
                    if (!TextUtils.isEmpty(logoutResp)) {
                        BeanBaseResp beanBaseResp = JSON.parseObject(logoutResp, BeanBaseResp.class);
                        if (beanBaseResp.getCode() == 0) {
                            Toast.makeText(SetUp.this, "退出登录成功", Toast.LENGTH_SHORT).show();
                            MySharedPrefUtil.remKey("user");     //清除用户登录信息
                            MyApplication.uid = "";
                            MyApplication.isFirstOpen = true;
                            DataSupport.deleteAll(BeanMedRecUser.class);//清除所有病历夹用户数据
                            DataSupport.deleteAll(BeanMedRec.class);//清除所有病历
                            DataSupport.deleteAll(BeanInspectionReport.class);//清除所有化验单
                            DataSupport.deleteAll(BeanPrescriptiom.class);//清除所有化验单

                            EventBus.getDefault().post(new LoginEventBus(false));//通知个人中心退出登录，刷新状态
                            Intent intent = new Intent(SetUp.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SetUp.this, "退出登录失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(SetUp.this, "网络异常" + e.toString(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNext(ResponseBody responseBody) {
                    try {
                        logoutResp = responseBody.string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }
}

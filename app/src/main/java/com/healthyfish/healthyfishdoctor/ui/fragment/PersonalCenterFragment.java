package com.healthyfish.healthyfishdoctor.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.healthyfish.healthyfishdoctor.MyApplication;
import com.healthyfish.healthyfishdoctor.POJO.BeanDoctor;
import com.healthyfish.healthyfishdoctor.POJO.BeanDoctorDB;
import com.healthyfish.healthyfishdoctor.POJO.BeanPersonalInformation;
import com.healthyfish.healthyfishdoctor.POJO.BeanUserLoginReq;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.eventbus.DoctorInfo;
import com.healthyfish.healthyfishdoctor.eventbus.LoginEventBus;
import com.healthyfish.healthyfishdoctor.ui.activity.login_register.Login;
import com.healthyfish.healthyfishdoctor.ui.activity.personal_center.ChangePersonalInformation;
import com.healthyfish.healthyfishdoctor.ui.activity.personal_center.Feedback;
import com.healthyfish.healthyfishdoctor.ui.activity.personal_center.MyConcern;
import com.healthyfish.healthyfishdoctor.ui.activity.personal_center.MyNews;
import com.healthyfish.healthyfishdoctor.ui.activity.personal_center.SetUp;
import com.healthyfish.healthyfishdoctor.utils.MySharedPrefUtil;
import com.healthyfish.healthyfishdoctor.utils.MyToast;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

import static com.healthyfish.healthyfishdoctor.constant.Constants.HttpHealthyFishyUrl;

/**
 * 描述：个人中心fragment
 * 作者：Wayne on 2017/7/11 20:55
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */
public class PersonalCenterFragment extends Fragment {

    @BindView(R.id.civ_head_portrait)
    CircleImageView civHeadPortrait;
    @BindView(R.id.tv_login_or_register)
    TextView tvLoginOrRegister;
    @BindView(R.id.lly_personal_information)
    AutoLinearLayout llyPersonalInformation;
    @BindView(R.id.lly_my_news)
    AutoLinearLayout llyMyNews;
    @BindView(R.id.lly_my_concern)
    AutoLinearLayout llyMyConcern;
    @BindView(R.id.lly_feedback)
    AutoLinearLayout llyFeedback;
    @BindView(R.id.lly_set)
    AutoLinearLayout llySet;
    @BindView(R.id.civ_head_portrait_login)
    CircleImageView civHeadPortraitLogin;
    @BindView(R.id.tv_name_login)
    TextView tvNameLogin;
    @BindView(R.id.tv_constitution_login)
    TextView tvConstitutionLogin;
    @BindView(R.id.iv_go)
    ImageView ivGo;
    @BindView(R.id.rly_mail_login)
    AutoRelativeLayout rlyMailLogin;
    @BindView(R.id.rly_not_login)
    AutoRelativeLayout rlyNotLogin;
    @BindView(R.id.rly_login)
    AutoRelativeLayout rlyLogin;
    Unbinder unbinder;

    private Context mContext;
    private View rootView;
    private LoginEventBus mLoginEventBus = new LoginEventBus();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        rootView = inflater.inflate(R.layout.fragment_personal_center, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        ivGo.setVisibility(View.GONE);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        String user = MySharedPrefUtil.getValue("user");
        String sid = MySharedPrefUtil.getValue("sid");
        if (!TextUtils.isEmpty(user)) {
            judgeLoginState(true);
        }
        return rootView;
    }


    @OnClick({R.id.civ_head_portrait, R.id.tv_login_or_register, R.id.lly_personal_information, R.id.lly_my_news, R.id.lly_my_concern, R.id.lly_feedback, R.id.lly_set, R.id.civ_head_portrait_login, R.id.tv_name_login, R.id.tv_constitution_login, R.id.iv_go, R.id.rly_mail_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.civ_head_portrait:
                //点击头像
                break;
            case R.id.tv_login_or_register:
                //点击登录/注册
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                break;
            case R.id.lly_personal_information:
                //点击个人信息
                if (!TextUtils.isEmpty(MyApplication.uid)) {
                    Intent intent02 = new Intent(getActivity(), ChangePersonalInformation.class);
                    //Intent intent02 = new Intent(getActivity(), PersonalInformation.class);
                    startActivity(intent02);
                } else {
                    MyToast.showToast(getActivity(), "您还没有登录呦！请先登录");
                }

                break;
            case R.id.lly_my_news:
                //点击我的消息
                Intent intent03 = new Intent(getActivity(), MyNews.class);
                startActivity(intent03);
                break;
            case R.id.lly_my_concern:
                //点击我的关注
                if (!TextUtils.isEmpty(MyApplication.uid)) {
                    Intent intent04 = new Intent(getActivity(), MyConcern.class);
                    startActivity(intent04);
                } else {
                    MyToast.showToast(getActivity(), "您还没有登录呦！请先登录");
                }
                break;
            case R.id.lly_feedback:
                //点击意见反馈
                Intent intent05 = new Intent(getActivity(), Feedback.class);
                startActivity(intent05);
                break;
            case R.id.lly_set:
                //点击设置
                Intent intent06 = new Intent(getActivity(), SetUp.class);
                startActivity(intent06);
                break;
            case R.id.civ_head_portrait_login:
                //登录后点击头像
                break;
            case R.id.tv_name_login:
                //登录后点击名字
                break;
            case R.id.tv_constitution_login:
                //登录后点击体质
                break;
            case R.id.iv_go:
                //登录后点击进入个人的健康信息
                break;
            case R.id.rly_mail_login:
                //登录后点击右上角消息邮箱
                Intent intent07 = new Intent(getActivity(), MyNews.class);
                startActivity(intent07);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshLoginState(LoginEventBus loginEventBus) {
        mLoginEventBus = loginEventBus;
        Log.i("LYQ", "refreshLoginState");
        judgeLoginState(loginEventBus.isLogin());
    }

    //登录状态判断初始化相应的控件
    private void judgeLoginState(boolean isLogin) {
        if (isLogin) {
            String user = MySharedPrefUtil.getValue("user");
            BeanUserLoginReq beanUserLoginReq = JSON.parseObject(user, BeanUserLoginReq.class);
            String number = beanUserLoginReq.getMobileNo();
            isLogin(true, number);
        } else {
            isLogin(false, null);
        }
    }




    /**
     * 判断是否登录
     * number：目前用手机号码表示用户
     */
    private void isLogin(boolean isLogin, String number) {
        if (isLogin) {
            rlyNotLogin.setVisibility(View.GONE);
            rlyLogin.setVisibility(View.VISIBLE);

            //initInfoPrompt("16");//检查有多少未读消息，并显示

            //初始化个人信息
            //initPersonalInformation(number);
            getInfoFromDB(number);

            //初始化用户体质
            //initUserPhy(number);

            initWidget();

            rlyNotLogin.setBackgroundResource(R.color.color_primary_dark);
        } else {
            rlyLogin.setVisibility(View.GONE);
            rlyNotLogin.setVisibility(View.VISIBLE);
            rlyNotLogin.setBackgroundResource(R.color.color_divider);
        }
    }

    /**
     * 从数据库查找个人信息
     */
    private void getInfoFromDB(String uid) {
        List<BeanDoctorDB> list = DataSupport.where("doctId = ?", uid).find(BeanDoctorDB.class);
        if (list.size() > 0) {
            mLoginEventBus.setName(list.get(0).getName());
            mLoginEventBus.setImgUrl(list.get(0).getIcon());
        }
    }

    /**
     * 初始化个人信息
     *
     * @param uid
     */
    private void initPersonalInformation(String uid) {
        String key = "info_" + uid;
        List<BeanPersonalInformation> personalInformationList = DataSupport.where("key = ?", key).find(BeanPersonalInformation.class);
        if (!personalInformationList.isEmpty()) {
            //beanDoctor = personalInformationList.get(0);
            initWidget();
        } else {
            //upDatePersonalInformation(uid);
        }
    }

    /**
     * 从网络获取个人信息
     */



    /**
     * 展示数据
     */
    private void initWidget() {
        if (mLoginEventBus != null) {
            if (!TextUtils.isEmpty(mLoginEventBus.getImgUrl())) {
                Glide.with(getActivity()).load(HttpHealthyFishyUrl + mLoginEventBus.getImgUrl()).error(R.mipmap.error).into(civHeadPortraitLogin);
            } else {
                Glide.with(getActivity()).load(R.mipmap.ic_logo).into(civHeadPortraitLogin);
            }
            if (!TextUtils.isEmpty(mLoginEventBus.getName())) {
                setTextBold(mLoginEventBus.getName());
            } else {
                tvNameLogin.setText("医生 你好");
            }
        } else {
            Glide.with(getActivity()).load(R.mipmap.ic_logo).into(civHeadPortraitLogin);
            tvNameLogin.setText("医生 你好");
        }
    }

    /**
     * 设置字体为粗体
     */
    private void setTextBold(String name) {
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append(name+" 医生");
        //setSpan可多次使用
        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);//粗体
        spannableString.setSpan(styleSpan, 0, name.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tvNameLogin.setText(spannableString);
    }

    /**
     * 初始化显示消息数控件
     *
     * @param string:总消息数
     */
    public void initInfoPrompt(String string) {
        Badge badge = new QBadgeView(mContext).bindTarget(rlyMailLogin);
        badge.setBadgeBackgroundColor(0xFFF70909);
        badge.setBadgeTextColor(0xffffffFF);
        badge.setBadgeGravity(Gravity.CENTER | Gravity.TOP);
        badge.setBadgeTextSize(12, true);
        badge.setBadgePadding(3, true);
        badge.setBadgeText(string);
        badge.setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
            @Override
            public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                if (dragState == STATE_SUCCEED) {

                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
}

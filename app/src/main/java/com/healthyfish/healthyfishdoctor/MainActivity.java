package com.healthyfish.healthyfishdoctor;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.healthyfish.healthyfishdoctor.POJO.BeanBaseKeyGetReq;
import com.healthyfish.healthyfishdoctor.POJO.BeanBaseKeyGetResp;
import com.healthyfish.healthyfishdoctor.POJO.BeanDoctor;
import com.healthyfish.healthyfishdoctor.POJO.BeanDoctorDB;
import com.healthyfish.healthyfishdoctor.POJO.BeanSessionIdReq;
import com.healthyfish.healthyfishdoctor.POJO.BeanSessionIdResp;
import com.healthyfish.healthyfishdoctor.POJO.BeanUserLoginReq;
import com.healthyfish.healthyfishdoctor.adapter.MainVpAdapter;
import com.healthyfish.healthyfishdoctor.eventbus.DoctorInfo;
import com.healthyfish.healthyfishdoctor.eventbus.InitAllMessage;
import com.healthyfish.healthyfishdoctor.eventbus.LoginEventBus;
import com.healthyfish.healthyfishdoctor.ui.activity.BaseActivity;

import com.healthyfish.healthyfishdoctor.ui.activity.login_register.Login;

import com.healthyfish.healthyfishdoctor.ui.fragment.HomeFragment;
import com.healthyfish.healthyfishdoctor.ui.fragment.InterrogationFragment;
import com.healthyfish.healthyfishdoctor.ui.fragment.PersonalCenterFragment;
import com.healthyfish.healthyfishdoctor.ui.fragment.PharmacopeiaFragment;
import com.healthyfish.healthyfishdoctor.ui.fragment.TrainingFragment;
import com.healthyfish.healthyfishdoctor.utils.AutoLogin;
import com.healthyfish.healthyfishdoctor.utils.MySharedPrefUtil;
import com.healthyfish.healthyfishdoctor.utils.MyToast;
import com.healthyfish.healthyfishdoctor.utils.OkHttpUtils;
import com.healthyfish.healthyfishdoctor.utils.RetrofitManagerUtils;
import com.healthyfish.healthyfishdoctor.utils.mqtt_utils.MqttUtil;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.healthyfish.healthyfishdoctor.constant.Constants.HttpHealthyFishyUrl;

public class MainActivity extends BaseActivity {

    @BindView(R.id.fg_viewpage)
    ViewPager fgViewpage;
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.ly_home)
    AutoLinearLayout lyHome;
    @BindView(R.id.iv_interrogation)
    ImageView ivInterrogation;
    @BindView(R.id.tv_interrogation)
    TextView tvInterrogation;
    @BindView(R.id.ly_interrogation)
    AutoLinearLayout lyInterrogation;
    @BindView(R.id.iv_pharmacopeia)
    ImageView ivPharmacopeia;
    @BindView(R.id.tv_pharmacopeia)
    TextView tvPharmacopeia;
    @BindView(R.id.ly_pharmacopeia)
    AutoLinearLayout lyPharmacopeia;
    @BindView(R.id.iv_training)
    ImageView ivTraining;
    @BindView(R.id.tv_training)
    TextView tvTraining;
    @BindView(R.id.ly_training)
    AutoLinearLayout lyTraining;
    @BindView(R.id.iv_personal_center)
    ImageView ivPersonalCenter;
    @BindView(R.id.tv_personal_center)
    TextView tvPersonalCenter;
    @BindView(R.id.ly_personal_center)
    AutoLinearLayout lyPersonalCenter;

    private MainVpAdapter ViewpageAdapter;
    private List<Fragment> fragments;
    private HomeFragment homeFragment;
    private InterrogationFragment interrogationFragment;
    private PharmacopeiaFragment pharmacopeiaFragment;
    private TrainingFragment trainingFragment;
    private PersonalCenterFragment personalCenterFragment;

    private BeanSessionIdReq beanSessionIdReq = new BeanSessionIdReq();

    private boolean isExit = false;
    private String uid = "";
    private boolean isFirstReq = true;
    private boolean isSecondReq = false;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        init();
        String user = MySharedPrefUtil.getValue("user");
        if (!TextUtils.isEmpty(user)) {
            BeanUserLoginReq beanUserLoginReq = JSON.parseObject(user, BeanUserLoginReq.class);
            MyApplication.uid = beanUserLoginReq.getMobileNo();
            uid = beanUserLoginReq.getMobileNo();
            if (MyApplication.isFirstOpen) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        getInformationFromNetwork("cert_" + uid);
                    }
                }).start();
            }
            initPermision();//获取权限
            // 初始化MQTT获取sid
            initMQTT();
        } else {
            MyToast.showToast(this, "您还没有登录呦");
            startActivity(new Intent(this, Login.class));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshLoginState(InitAllMessage initAllMessage) {

        //打开App时若未登录则跳转到登录页面，登录成功后返回通知初始化以下内容
        uid = MyApplication.uid;

        initPermision();//获取权限

        initMQTT();// 初始化MQTT连接，首先获取sid，然后开启MQTT连接

        //登录积分
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getInformationFromNetwork("cert_" + uid);
            }
        }).start();

    }

    //初始化接界面
    private void init() {
        initpgAdapter();//初始化viewpage
        // 初始化MQTT获取sid
        initMQTT();
        setTab(0);//初始化界面设置，即指定刚进入是可见的界面
        //菜单监听
        fgViewpage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                int pos = fgViewpage.getCurrentItem();
                setTab(pos);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    //菜单监听


    //初始化ViewPage
    private void initpgAdapter() {
        fragments = new ArrayList<>();
        homeFragment = new HomeFragment();
        interrogationFragment = new InterrogationFragment();
        pharmacopeiaFragment = new PharmacopeiaFragment();
        trainingFragment = new TrainingFragment();
        personalCenterFragment = new PersonalCenterFragment();
        fragments.add(homeFragment);
        fragments.add(interrogationFragment);
        fragments.add(pharmacopeiaFragment);
        fragments.add(trainingFragment);
        fragments.add(personalCenterFragment);
        ViewpageAdapter = new MainVpAdapter(getSupportFragmentManager()
                , fragments);
        //设置ViewPage的缓存页，数字表示预先加载的页面的偏移量，
        // 现在0的意思是不预先加载，另一个作用是也不销毁生的页面
        fgViewpage.setOffscreenPageLimit(0);
        fgViewpage.setAdapter(ViewpageAdapter);
    }

    //重置菜单状态
    private void reSet() {
        ivHome.setImageResource(R.mipmap.home_unselected);
        ivInterrogation.setImageResource(R.mipmap.interrogation_unselect);
        ivPharmacopeia.setImageResource(R.mipmap.pharmacopeia_unselected);
        ivTraining.setImageResource(R.mipmap.training_unselected);
        ivPersonalCenter.setImageResource(R.mipmap.personal_center_unselect);
        tvHome.setTextColor(getResources().getColor(R.color.color_general_and_title));
        tvInterrogation.setTextColor(getResources().getColor(R.color.color_general_and_title));
        tvPharmacopeia.setTextColor(getResources().getColor(R.color.color_general_and_title));
        tvTraining.setTextColor(getResources().getColor(R.color.color_general_and_title));
        tvPersonalCenter.setTextColor(getResources().getColor(R.color.color_general_and_title));
    }

    //设置菜单的选中状态
    private void setTab(int i) {
        switch (i) {
            case 0:
                reSet();
                ivHome.setImageResource(R.mipmap.home);
                tvHome.setTextColor(getResources().getColor(R.color.color_primary_dark));
                fgViewpage.setCurrentItem(0);
                break;
            case 1:
                reSet();
                ivInterrogation.setImageResource(R.mipmap.interrogation);
                tvInterrogation.setTextColor(getResources().getColor(R.color.color_primary_dark));
                fgViewpage.setCurrentItem(1);
                break;
            case 2:
                reSet();
                ivPharmacopeia.setImageResource(R.mipmap.pharmacopeia);
                tvPharmacopeia.setTextColor(getResources().getColor(R.color.color_primary_dark));
                fgViewpage.setCurrentItem(2);
                break;
            case 3:
                reSet();
                ivTraining.setImageResource(R.mipmap.training);
                tvTraining.setTextColor(getResources().getColor(R.color.color_primary_dark));
                fgViewpage.setCurrentItem(3);
                break;
            case 4:
                reSet();
                ivPersonalCenter.setImageResource(R.mipmap.personal_center);
                tvPersonalCenter.setTextColor(getResources().getColor(R.color.color_primary_dark));
                fgViewpage.setCurrentItem(4);
                break;
            default:
        }
    }

    @OnClick({R.id.ly_home, R.id.ly_interrogation, R.id.ly_pharmacopeia, R.id.ly_training, R.id.ly_personal_center})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ly_home:
                setTab(0);
                break;
            case R.id.ly_interrogation:
                setTab(1);
                break;
            case R.id.ly_pharmacopeia:
                setTab(2);
                break;
            case R.id.ly_training:
                setTab(3);
                break;
            case R.id.ly_personal_center:
                setTab(4);
                break;
        }
    }

    public void initPermision() {
        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .compose(RxPermissions.getInstance(this).ensureEach(Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Permission>() {
                    @Override
                    public void call(Permission permission) {

                    }
                });
    }

    /**
     * 从服务器获取医生信息
     */
    private void getInformationFromNetwork(String reqKey) {
        BeanBaseKeyGetReq beanBaseKeyGetReq = new BeanBaseKeyGetReq();
        beanBaseKeyGetReq.setKey(reqKey);

        RetrofitManagerUtils.getInstance(this, null).getHealthyInfoByRetrofit(OkHttpUtils.getRequestBody(beanBaseKeyGetReq), new Subscriber<ResponseBody>() {

            String infoResp = "";

            @Override
            public void onCompleted() {
                if (!TextUtils.isEmpty(infoResp)) {
                    if (infoResp.substring(0, 1).equals("{")) {
                        BeanBaseKeyGetResp beanBaseKeyGetResp = JSON.parseObject(infoResp, BeanBaseKeyGetResp.class);
                        if (beanBaseKeyGetResp.getCode() == 0) {
                            MyApplication.isFirstOpen = false;
                            if (!TextUtils.isEmpty(beanBaseKeyGetResp.getValue())) {
                                String str = beanBaseKeyGetResp.getValue().toString().substring(0, 1);
                                if (!str.equals("{")) {//防止之前网页版的个人信息格式不匹配导致出错
                                    MyToast.showToast(MyApplication.getContetxt(), "您的个人信息有误，请到个人信息重新修改");
                                    EventBus.getDefault().post(new LoginEventBus(null,true));
                                    return;
                                }
                                Log.i("LYQ", "beanBaseKeyGetResp.getValue():" + beanBaseKeyGetResp.getValue());
                                if (isFirstReq) {//第一次请求时已审核，第二次请求时未审核
                                    isFirstReq = false;
                                    BeanDoctor beanDoctor = JSON.parseObject(beanBaseKeyGetResp.getValue(), BeanDoctor.class);
                                    saveToDB("cert_" + uid, beanDoctor, true);
                                } else {
                                    BeanDoctor beanDoctor = JSON.parseObject(beanBaseKeyGetResp.getValue(), BeanDoctor.class);
                                    saveToDB("certReq_" + uid, beanDoctor, false);
                                }
                            } else {//第一次请求时未审核
                                if (!isSecondReq) {
                                    isFirstReq = false;
                                    getInformationFromNetwork("certReq_" + uid);
                                    isSecondReq = true;
                                } else {
                                    MyToast.showToast(MainActivity.this, "您还未上传个人信息");
                                }
                            }

                        } else {
                            MyToast.showToast(MainActivity.this, "加载个人信息失败");
                        }
                    } else {
                        MyToast.showToast(MainActivity.this, "加载个人信息出错啦");
                    }
                } else {
                    MyToast.showToast(MainActivity.this, "加载个人信息出错了");
                }
            }

            @Override
            public void onError(Throwable e) {
                MyToast.showToast(MainActivity.this, "加载个人信息出错");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    infoResp = responseBody.string();
                    Log.i("LYQ", "infoResp:" + infoResp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 将个人信息保存到数据库
     *
     * @param key
     * @param beanDoctor
     * @param isPast
     */
    private void saveToDB(String key, BeanDoctor beanDoctor, boolean isPast) {
        BeanDoctorDB beanDoctorDB = new BeanDoctorDB();
        beanDoctorDB.setDoctId(beanDoctor.getDoctId());
        beanDoctorDB.setKey(key);
        beanDoctorDB.setType(beanDoctor.getType());
        beanDoctorDB.setHosp(beanDoctor.getHosp());
        beanDoctorDB.setHospTxt(beanDoctor.getHospTxt());
        beanDoctorDB.setDept(beanDoctor.getDept());
        beanDoctorDB.setDeptTxt(beanDoctor.getDeptTxt());
        beanDoctorDB.setName(beanDoctor.getName());
        beanDoctorDB.setGender(beanDoctor.getGender());
        beanDoctorDB.setDoct(beanDoctor.getDoct());
        beanDoctorDB.setDoctReg(beanDoctor.getDoctReg());
        beanDoctorDB.setDesc(beanDoctor.getDesc());
        beanDoctorDB.setIcon(beanDoctor.getIcon());
        String jsonImages = JSONArray.toJSONString(beanDoctor.getImgList());
        beanDoctorDB.setImages(jsonImages);
        beanDoctorDB.setReister(beanDoctor.getReister());
        beanDoctorDB.setPhone(beanDoctor.getPhone());
        beanDoctorDB.setPast(isPast);
        Log.i("LYQ", "保存到数据库：" + JSON.toJSONString(beanDoctorDB));
        boolean isSave = beanDoctorDB.saveOrUpdate("doctId = ?", uid);
        if (!isSave) {
            beanDoctorDB.saveOrUpdate("doctId = ?", uid);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /**
     * 再次点击返回键退出App
     */
    private void exit() {
        if (isExit) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
        } else {
            isExit = true;
            MyToast.showToast(MyApplication.getContetxt(),"再按一次退出程序");
            mHandler.sendEmptyMessageDelayed(0, 2000);
        }
    }

    /**
     * 初始化MQTT
     */
    private void initMQTT() {

        RetrofitManagerUtils.getInstance(MainActivity.this, HttpHealthyFishyUrl)
                .getSidByRetrofit(OkHttpUtils.getRequestBody(beanSessionIdReq), new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        String user = MySharedPrefUtil.getValue("user");
                        String sid = MySharedPrefUtil.getValue("sid");
                        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(sid)) {
                            AutoLogin.autoLogin();
                            MqttUtil.startAsync();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            BeanSessionIdResp obj = new Gson().fromJson(responseBody.string(), BeanSessionIdResp.class);
                            Log.e("MainActivity从服务器获取sid", obj.getSid());
                            MySharedPrefUtil.saveKeyValue("sid", obj.getSid());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}

package com.healthyfish.healthyfishdoctor.ui.activity.login_register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.healthyfish.healthyfishdoctor.MainActivity;
import com.healthyfish.healthyfishdoctor.MyApplication;
import com.healthyfish.healthyfishdoctor.POJO.BeanBaseKeyGetReq;
import com.healthyfish.healthyfishdoctor.POJO.BeanBaseKeyGetResp;
import com.healthyfish.healthyfishdoctor.POJO.BeanBaseResp;
import com.healthyfish.healthyfishdoctor.POJO.BeanDoctor;
import com.healthyfish.healthyfishdoctor.POJO.BeanDoctorDB;
import com.healthyfish.healthyfishdoctor.POJO.BeanPersonalInformation;
import com.healthyfish.healthyfishdoctor.POJO.BeanSessionIdReq;
import com.healthyfish.healthyfishdoctor.POJO.BeanSessionIdResp;
import com.healthyfish.healthyfishdoctor.POJO.BeanUserLoginReq;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.eventbus.DoctorInfo;
import com.healthyfish.healthyfishdoctor.eventbus.LoginEventBus;
import com.healthyfish.healthyfishdoctor.ui.presenter.login.LoginPresenter;
import com.healthyfish.healthyfishdoctor.ui.view.login.ILoginView;
import com.healthyfish.healthyfishdoctor.utils.AutoLogin;
import com.healthyfish.healthyfishdoctor.utils.MySharedPrefUtil;
import com.healthyfish.healthyfishdoctor.utils.MyToast;
import com.healthyfish.healthyfishdoctor.utils.OkHttpUtils;
import com.healthyfish.healthyfishdoctor.utils.RetrofitManagerUtils;
import com.healthyfish.healthyfishdoctor.utils.Sha256;
import com.healthyfish.healthyfishdoctor.utils.mqtt_utils.MqttUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscriber;

import static com.healthyfish.healthyfishdoctor.constant.Constants.HttpHealthyFishyUrl;

/**
 * 描述：登录Activity
 * 作者：Wayne on 2017/6/28 16:11
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */
public class Login extends AppCompatActivity implements ILoginView{

    @BindView(R.id.et_login_user_name)
    EditText etLoginUserName;
    @BindView(R.id.et_login_password)
    EditText etLoginPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_forgot_password)
    TextView tvForgotPassword;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.login_progressBar)
    ProgressBar loginProgressBar;

    private LoginPresenter mLoginPresenter = new LoginPresenter(this);

    private String uid = "";
    private boolean isFirstReq = true;
    private boolean isSecondReq = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_login, R.id.tv_forgot_password, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                //mLoginPresenter.login();
                login();
                break;
            case R.id.tv_forgot_password:
                toResetPasswordActivity();
                break;
            case R.id.btn_register:
                toRegisterActivity();
                break;
        }
    }

    /**
     * 登录
     */
    private void login() {
        if (getLoginUserName().length() != 11) {
            MyToast.showToast(this,"请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(getLoginPassword())) {
            MyToast.showToast(this,"请输入您的密码");
            return;
        }
        BeanUserLoginReq beanUserLoginReq = new BeanUserLoginReq();
        beanUserLoginReq.setMobileNo(getLoginUserName());//号码
        beanUserLoginReq.setAct(BeanUserLoginReq.class.getSimpleName());//设置操作类型，不然服务器不知道
        beanUserLoginReq.setPwdSHA256(Sha256.getSha256(getLoginPassword()));//密码

        final String user = JSON.toJSONString(beanUserLoginReq);//如果登录成功，将会由sharepreference保存

        RetrofitManagerUtils.getInstance(this, null)
                .getHealthyInfoByRetrofit(OkHttpUtils.getRequestBody(beanUserLoginReq), new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        String user = MySharedPrefUtil.getValue("user");

                        if (!TextUtils.isEmpty(user)) {
                            AutoLogin.autoLogin();
                            MqttUtil.startAsync();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(Login.this, "登录失败，请检查网络环境", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        String str = null;
                        try {
                            str = responseBody.string();
                            Log.i("LYQ", "登录响应:" + str);
                            BeanBaseResp beanBaseResp = JSON.parseObject(str, BeanBaseResp.class);
                            int code = beanBaseResp.getCode();
                            judgeAndShowToast(code, user);//根据返回码做出相应的提示
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 根据返回码做出相应的提示
     */
    private void judgeAndShowToast(int code, String user) {
        if (code == 0) {
            Toast.makeText(Login.this, "登录成功", Toast.LENGTH_LONG).show();
            MySharedPrefUtil.saveKeyValue("user", user);  //登录成功由shareprefrence保存
            MyApplication.uid = getLoginUserName();
            uid = getLoginUserName();
            upDatePersonalInformation("cert_" + uid);//更新医生个人信息
            //EventBus.getDefault().post(new InitAllMessage());//通知首页初始化
            EventBus.getDefault().post(new BeanPersonalInformation(true));//通知个人中心刷新登录状态
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if (code == -1) {
            Toast.makeText(Login.this, "用户不存在", Toast.LENGTH_LONG).show();
        } else if (code == -2 || code == -5 || code == -3) {
            Toast.makeText(Login.this, "密码错误", Toast.LENGTH_LONG).show();
        } else if (code == -10) {
            Toast.makeText(Login.this, "操作次数过多", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(Login.this, "登录失败", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 从服务器获取医生信息
     */
    private void upDatePersonalInformation(String reqKey) {
        BeanBaseKeyGetReq beanBaseKeyGetReq = new BeanBaseKeyGetReq();
        beanBaseKeyGetReq.setKey(reqKey);

        RetrofitManagerUtils.getInstance(MyApplication.getContetxt(), null).getHealthyInfoByRetrofit(OkHttpUtils.getRequestBody(beanBaseKeyGetReq), new Subscriber<ResponseBody>() {

            String infoResp = "";

            @Override
            public void onCompleted() {
                if (!TextUtils.isEmpty(infoResp)) {
                    if (infoResp.substring(0, 1).equals("{")) {
                        BeanBaseKeyGetResp beanBaseKeyGetResp = JSON.parseObject(infoResp, BeanBaseKeyGetResp.class);
                        if (beanBaseKeyGetResp.getCode() == 0) {
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
                                    EventBus.getDefault().post(new LoginEventBus(beanDoctor.getName(),beanDoctor.getIcon(),true));
                                } else {
                                    BeanDoctor beanDoctor = JSON.parseObject(beanBaseKeyGetResp.getValue(), BeanDoctor.class);
                                    saveToDB("certReq_" + uid, beanDoctor, false);
                                    EventBus.getDefault().post(new LoginEventBus(beanDoctor.getName(),beanDoctor.getIcon(),true));
                                }
                            } else {//第一次请求时未审核
                                if (!isSecondReq) {
                                    isFirstReq = false;
                                    upDatePersonalInformation("certReq_" + uid);
                                    isSecondReq = true;
                                } else {
                                    MyToast.showToast(MyApplication.getContetxt(), "您还未上传个人信息");
                                    EventBus.getDefault().post(new LoginEventBus(null,true));
                                }
                            }

                        } else {
                            MyToast.showToast(MyApplication.getContetxt(), "加载个人信息失败");
                            EventBus.getDefault().post(new LoginEventBus(null,true));
                        }
                    } else {
                        MyToast.showToast(MyApplication.getContetxt(), "加载个人信息出错啦");
                        EventBus.getDefault().post(new LoginEventBus(null,true));
                    }
                } else {
                    MyToast.showToast(MyApplication.getContetxt(), "加载个人信息出错了");
                    EventBus.getDefault().post(new LoginEventBus(null,true));

                }
            }

            @Override
            public void onError(Throwable e) {
                MyToast.showToast(MyApplication.getContetxt(), "加载个人信息出错");
                EventBus.getDefault().post(new LoginEventBus(null,true));
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    infoResp = responseBody.string();
                    Log.i("LYQ", "LogiinfoResp:" + infoResp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 将个人信息保存到数据库
     * @param key
     * @param beanDoctor
     * @param isPast
     */
    private void saveToDB(String key, BeanDoctor beanDoctor, boolean isPast) {
        BeanDoctorDB beanDoctorDB = new BeanDoctorDB();
        beanDoctorDB.setDoctId(uid);
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
        Log.i("LYQ", "Login保存到数据库："+JSON.toJSONString(beanDoctorDB));
        boolean isSave = beanDoctorDB.saveOrUpdate("doctId = ?", uid);
        if (!isSave) {
            beanDoctorDB.saveOrUpdate("doctId = ?", uid);
        }
    }


    /**
     * 跳转到注册界面
     * */
    private void toRegisterActivity() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    /**
     * 跳转到重置密码界面
     * */
    private void toResetPasswordActivity() {
        Intent intent = new Intent(this, ForgetPassword.class);
        startActivity(intent);
    }

    @Override
    public void showLoginProgressBar() {
        if (loginProgressBar.getVisibility() == View.GONE){
            loginProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoginProgressBar() {
        if (loginProgressBar.getVisibility() == View.VISIBLE){
            loginProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public String getLoginUserName() {
        return etLoginUserName.getText().toString().trim();
    }

    @Override
    public String getLoginPassword() {
        return etLoginPassword.getText().toString().trim();
    }

    @Override
    public void toLoginActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void showLoginFailedError() {
        MyToast.showToast(this,"登录失败");
    }

}

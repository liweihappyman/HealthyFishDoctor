package com.healthyfish.healthyfishdoctor.ui.presenter.login;

import android.os.Handler;

import com.healthyfish.healthyfishdoctor.model.login.ILoginModel;
import com.healthyfish.healthyfishdoctor.model.login.LoginModel;
import com.healthyfish.healthyfishdoctor.model.login.OnLoginListener;
import com.healthyfish.healthyfishdoctor.ui.view.login.ILoginView;

/**
 * 描述：登录Presenter
 * 作者：Wayne on 2017/6/28 11:16
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */

// TODO: 2017/6/28 实现登录Presenter
public class LoginPresenter {
    private ILoginView loginView;
    private ILoginModel loginModel;
    private Handler mHandler = new Handler();

    public LoginPresenter(ILoginView loginView){
        this.loginView = loginView;
        this.loginModel = new LoginModel();
    }

    public void login(){
        loginView.showLoginProgressBar();
        loginModel.Login(loginView.getLoginUserName(), loginView.getLoginPassword(), new OnLoginListener() {
            @Override
            public void LoginSucess() {
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        loginView.hideLoginProgressBar();
                        loginView.toLoginActivity();
                    }
                });
            }

            @Override
            public void LoginFailed() {
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        loginView.hideLoginProgressBar();
                        loginView.showLoginFailedError();
                    }
                });
            }
        });
    }
}

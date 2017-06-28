package com.healthyfish.healthyfishdoctor.model.login;

/**
 * 描述：登录业务接口
 * 作者：Wayne on 2017/6/28 11:40
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */


// TODO: 2017/6/28 实现登录业务接口
public interface ILoginModel {
    void Login(String username, String password, OnLoginListener loginListener);
}

package com.healthyfish.healthyfishdoctor.model.register;

/**
 * 描述：注册业务接口
 * 作者：Wayne on 2017/6/28 16:27
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */

// TODO: 2017/6/28 实现注册业务接口
public interface IRegisterModel {
    void Register(String username, String password, OnRegisterListener registerListener);

}

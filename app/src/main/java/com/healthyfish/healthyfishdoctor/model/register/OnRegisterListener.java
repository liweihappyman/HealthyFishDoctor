package com.healthyfish.healthyfishdoctor.model.register;

/**
 * 描述：注册状态监听事件
 * 作者：Wayne on 2017/6/28 16:30
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */

// TODO: 2017/6/28 实现注册状态监听事件
public interface OnRegisterListener {
    void RegisterSucess();

    void RegisterFailed();
}

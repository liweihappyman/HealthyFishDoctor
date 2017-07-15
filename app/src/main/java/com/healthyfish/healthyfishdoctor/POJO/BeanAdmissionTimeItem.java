package com.healthyfish.healthyfishdoctor.POJO;

/**
 * 描述：问诊设置接诊时间基本封装类
 * 作者：LYQ on 2017/7/13.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class BeanAdmissionTimeItem {

    private String time;//具体时间点
    private boolean isCheck;//是否可以接诊

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}

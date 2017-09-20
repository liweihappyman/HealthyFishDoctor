package com.healthyfish.healthyfishdoctor.POJO;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * 描述：个人信息类
 * 作者：LYQ on 2017/7/25.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class BeanPersonalInformation extends DataSupport implements Serializable {

    private int id;//数据库自动生成的id
    private String key;//用户信息的key
    private String phone;//手机号
    private String name;//姓名
    private String nickname;//昵称
    private String imgUrl;//头像
    private String gender;//性别
    private String birthDate;//出生日期
    private String idCard;//身份证号
    protected boolean isLogin;

    public BeanPersonalInformation() {
    }

    public BeanPersonalInformation(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

}

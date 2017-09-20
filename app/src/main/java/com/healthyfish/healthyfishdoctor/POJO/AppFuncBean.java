package com.healthyfish.healthyfishdoctor.POJO;

public class AppFuncBean {

    private int id;
    private int icon;
    private String funcName;

    public int getIcon() {
        return icon;
    }

    public String getFuncName() {
        return funcName;
    }

    public int getId() {
        return id;
    }

    public AppFuncBean(int icon, String funcName) {
        this.icon = icon;
        this.funcName = funcName;
    }

    public AppFuncBean(int icon, String funcName, int id){
        this.icon = icon;
        this.funcName = funcName;
        this.id = id;
    }
}

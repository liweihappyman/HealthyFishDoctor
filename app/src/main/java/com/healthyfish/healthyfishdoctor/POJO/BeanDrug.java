package com.healthyfish.healthyfishdoctor.POJO;

/**
 * 描述：某类药典列表用的
 * 作者：WKJ on 2017/7/15.
 * 邮箱：
 * 编辑：WKJ
 */

public class BeanDrug {
    private String nameOfdrug;//药品的名字
    private String company;//公司的名字

    public BeanDrug() {
    }

    public BeanDrug(String nameOfdrug, String company) {

        this.nameOfdrug = nameOfdrug;
        this.company = company;
    }

    public String getNameOfdrug() {
        return nameOfdrug;
    }

    public void setNameOfdrug(String nameOfdrug) {
        this.nameOfdrug = nameOfdrug;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}

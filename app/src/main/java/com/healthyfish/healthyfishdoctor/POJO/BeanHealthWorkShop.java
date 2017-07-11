package com.healthyfish.healthyfishdoctor.POJO;

/**
 * 描述：健康工坊Bean
 * 作者：Wayne on 2017/7/11 11:25
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */

public class BeanHealthWorkShop {

    // 商品缩略图
    private int smallImgCommodity;
    // 商品名称
    private String nameCommodity;
    // 热销图片
    private String imgHotSale;
    // 是否热销
    private boolean isHotSale = false;

    public int getSmallImgCommodity() {
        return smallImgCommodity;
    }

    public void setSmallImgCommodity(int smallImgCommodity) {
        this.smallImgCommodity = smallImgCommodity;
    }

    public String getNameCommodity() {
        return nameCommodity;
    }

    public void setNameCommodity(String nameCommodity) {
        this.nameCommodity = nameCommodity;
    }

    public String getImgHotSale() {
        return imgHotSale;
    }

    public void setImgHotSale(String imgHotSale) {
        this.imgHotSale = imgHotSale;
    }

    public boolean isHotSale() {
        return isHotSale;
    }

    public void setHotSale(boolean hotSale) {
        isHotSale = hotSale;
    }
}

package com.healthyfish.healthyfishdoctor.POJO;

/**
 * 描述：健康圈社区信息封装类
 * 作者：LYQ on 2017/7/9.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class BeanHealthyCircleItem {

    private String url;//社区链接
    private String imgUrl;//社区图标
    private String title;//社区名字
    private String content;//社区主要内容

    public BeanHealthyCircleItem(String url, String imgUrl, String title, String content) {
        this.url = url;
        this.imgUrl = imgUrl;
        this.title = title;
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

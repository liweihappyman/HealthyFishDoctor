package com.healthyfish.healthyfishdoctor.POJO;

/**
 * 描述：个人中心我的消息封装类
 * 作者：LYQ on 2017/7/8.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class BeanMyNewsItem {

    private String newsType;//消息类型
    private String newsContent;//消息内容
    private String newsTime;//消息时间


    public BeanMyNewsItem(String newsType, String newsContent, String newsTime) {
        this.newsType = newsType;
        this.newsContent = newsContent;
        this.newsTime = newsTime;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(String newsTime) {
        this.newsTime = newsTime;
    }
}

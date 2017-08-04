package com.healthyfish.healthyfishdoctor.POJO;

/**
 * 描述：上传图片返回的数据bean
 * 作者：WKJ on 2017/7/27.
 * 邮箱：
 * 编辑：WKJ
 */

public class BeanUploadImagesResp {

    /**
     * beanKey : upload/20170727/58651501120528555.png
     * code : 0
     * url : /demo/downloadFile/upload/20170727/58651501120528555.png
     * ver : 0.1
     */

    private String beanKey;
    private int code;
    private String url;
    private String ver;

    public String getBeanKey() {
        return beanKey;
    }

    public void setBeanKey(String beanKey) {
        this.beanKey = beanKey;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }
}

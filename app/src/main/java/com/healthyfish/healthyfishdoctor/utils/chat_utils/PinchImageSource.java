package com.healthyfish.healthyfishdoctor.utils.chat_utils;

/**
 * 描述：
 * 作者：Wayne on 2017/7/8 17:06
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */

public class PinchImageSource implements ImageSource {

    private String mOriginUrl;
    private int mOriginWidth;
    private int mOriginHeight;
    private String mThumbUrl;
    private int mThumbWidth;
    private int mThumbHeight;

    public PinchImageSource(String mOriginUrl, int mOriginWidth, int mOriginHeight, String mThumbUrl, int mThumbWidth, int mThumbHeight) {
        this.mOriginUrl = mOriginUrl;
        this.mOriginWidth = mOriginWidth;
        this.mOriginHeight = mOriginHeight;
        this.mThumbUrl = mThumbUrl;
        this.mThumbWidth = mThumbWidth;
        this.mThumbHeight = mThumbHeight;
    }

    @Override
    public ImageObject getThumb(int width, int height) {
        ImageObject imageObject = new ImageObject();
        imageObject.url = mThumbUrl;
        imageObject.width = mThumbWidth;
        imageObject.height = mThumbHeight;
        return imageObject;
    }

    @Override
    public ImageObject getOrigin() {
        ImageObject imageObject = new ImageObject();
        imageObject.url = mOriginUrl;
        imageObject.width = mOriginWidth;
        imageObject.height = mOriginHeight;
        return imageObject;
    }
}

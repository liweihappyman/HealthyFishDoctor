package com.healthyfish.healthyfishdoctor.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.healthyfish.healthyfishdoctor.MyApplication;
import com.healthyfish.healthyfishdoctor.POJO.BeanUploadImagesResp;
import com.healthyfish.healthyfishdoctor.POJO.ImMsgBean;
import com.healthyfish.healthyfishdoctor.eventbus.WeChatImageMessage;
import com.healthyfish.healthyfishdoctor.utils.RetrofitManagerUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscriber;
import top.zibin.luban.Luban;


/**
 * 描述：1.压缩图片；2.上传图片；3.保存到本地数据库
 * 作者：WKJ on 2017/7/27.
 * 邮箱：
 * 编辑：WKJ
 */
public class WeChatUploadImage extends IntentService {
    private String imgUrl;
    private ImMsgBean bean;
    public WeChatUploadImage() {
        super("WeChatUploadImage");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        List<String> imagePathList = initData(intent);//初始化数据，获取activity传过来的数据,即要上传的图片
        List<File> compressFiles = getCompressFiles(imagePathList);//压缩图片
        uploadFilesAndSave(compressFiles);//上传图片并保存返回的图片路径
    }

    /**
     * 上传图片，并将返回的路径保存到数据库
     *
     * @param compressFiles 压缩后的文件
     */
    private void uploadFilesAndSave(List<File> compressFiles) {

        RetrofitManagerUtils.getInstance(this, null).uploadFilesRetrofit(compressFiles, 1, new Subscriber<ResponseBody>() {//没有特别的意义
            @Override
            public void onCompleted() {
                Log.i("图片上传", "完成");
                bean.setImgUrl(imgUrl);
                bean.save();
                //bean.updateAll("time = ?", bean.getTime() + "");
                EventBus.getDefault().post(new WeChatImageMessage(bean.getTime(), imgUrl));
            }

            @Override
            public void onError(Throwable e) {
                Log.i("图片上传", "图片上传失败" + e.toString());
                bean.setImgUrl("failure");
                bean.save();
                // bean.updateAll("time = ?", bean.getTime() + "");
                EventBus.getDefault().post(new WeChatImageMessage(bean.getTime(), "failure"));
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String str = responseBody.string();
                    Log.i("图片上传", "返回数据" + str);
                    if (str != null) {
                        BeanUploadImagesResp beanUploadImagesResp = new BeanUploadImagesResp();
                        beanUploadImagesResp = JSON.parseObject(str, BeanUploadImagesResp.class);
                        imgUrl = "http://www.kangfish.cn" + beanUploadImagesResp.getUrl();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //网络图片路径样式:http://www.kangfish.cn/demo/downloadFile/upload/20170727/58651501120528555.png
            }
        });
    }



    /**
     * 压缩上传的图片文件
     *
     * @return
     */
    @NonNull
    private List<File> getCompressFiles(List<String> imagePathList) {
        List<File> compressFiles = new ArrayList<>();//暂时存放压缩后的图片，用来上传
        for (int i = 0; i < imagePathList.size(); i++) {
            File file = new File(imagePathList.get(i));
            final DecimalFormat df = new DecimalFormat("00.0000");
            try {
                String size = df.format(((double) (new FileInputStream(file).available())) / 1024 / 1024);
                //Log.i("图片原来的大小" + i, "第  " + i + "  张:  " + size + "   M");
                if (Float.valueOf(size) < 0.6) {//如果文件小于600k，就不用压缩了
                    compressFiles.add(file);
                } else {
                    compressFiles.add(Luban.with(MyApplication.getContetxt()).load(file).get());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return compressFiles;
    }



    /**
     * 初始化数据，获取activity传过来的数据
     *
     * @param intent
     */
    private List initData(Intent intent) {
        List<String> imagePathList = new ArrayList<>();
        bean = (ImMsgBean) intent.getSerializableExtra("WeChatImage");
        imagePathList.add(bean.getImage().replace("file://", ""));
        return imagePathList;
    }

    /*private List initData(Intent intent) {
        List<String> imagePathList = new ArrayList<>();
        String imagePath = intent.getStringExtra("WeChatImage");
        imagePathList.add(imagePath);
        return imagePathList;
    }*/


}

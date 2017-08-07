package com.healthyfish.healthyfishdoctor.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.healthyfish.healthyfishdoctor.MyApplication;
import com.healthyfish.healthyfishdoctor.POJO.BeanCourseOfDisease;
import com.healthyfish.healthyfishdoctor.POJO.BeanDoctor;
import com.healthyfish.healthyfishdoctor.POJO.BeanMedRec;
import com.healthyfish.healthyfishdoctor.POJO.BeanUploadImagesResp;
import com.healthyfish.healthyfishdoctor.POJO.MessageToServise;
import com.healthyfish.healthyfishdoctor.utils.RetrofitManagerUtils;


import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

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
public class UploadImage extends IntentService {
    int sizeOfImagePathList;//总共图片的size大小
    List<String> imagePathList;//原始图片路径
    List<String> imageUrls;//存放图片网络路径
    private String type = "";
    private final String TYPE_IMAGE = "IMAGE";
    private final String TYPE_HEAD_PORTRAIT = "HEAD_PORTRAIT";

    public UploadImage() {
        super("UploadImage");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //初始化数据，获取activity传过来的数据
        initData(intent);
        //获取要上传的图片的路径
        List<String> uploadList = getUploadList();
        if (uploadList.size() > 0) {
            List<File> list = new ArrayList<>();//每次上传往里面放一张图片
            List<File> compressFiles = getCompressFiles();//压缩图片
            for (int i = 0; i < compressFiles.size(); i++) {
                list.clear();
                list.add(compressFiles.get(i));
                uploadFilesAndSave(list, i);//上传图片并保存返回的图片路径
            }
        } else if (imageUrls.size() != 0 && sizeOfImagePathList == imageUrls.size()) {
            MyApplication.getApplicationHandler().sendEmptyMessage(0x11);
            if (type.equals(TYPE_HEAD_PORTRAIT)) {
                BeanDoctor beanDoctor = new BeanDoctor();
                beanDoctor.setIcon(imageUrls.get(0));
                EventBus.getDefault().post(beanDoctor);
            } else if (type.equals(TYPE_IMAGE)) {
                BeanDoctor beanDoctor = new BeanDoctor();
                beanDoctor.setImgList(imageUrls);
                EventBus.getDefault().post(beanDoctor);
            }
        }
    }


    /**
     * 上传图片，并将返回的路径保存到数据库
     */
    private void uploadFilesAndSave(final List<File> list, final int position) {
        Log.i("LYQ", "uploadFilesAndSave:");
        //list当前上传的图片文件，position：当前上传的图片文件在compressFiles中的位置
        RetrofitManagerUtils.getInstance(this, null).uploadFilesRetrofit(list, position, new Subscriber<ResponseBody>() {

            String str = "";

            @Override
            public void onCompleted() {
                if (str != null) {
                    BeanUploadImagesResp beanUploadImagesResp = new BeanUploadImagesResp();
                    beanUploadImagesResp = JSON.parseObject(str, BeanUploadImagesResp.class);
                    imageUrls.add(beanUploadImagesResp.getUrl());
                    //通知上传完毕
                    if (sizeOfImagePathList == imageUrls.size()) {
                        if (type.equals(TYPE_HEAD_PORTRAIT)) {
                            MyApplication.getApplicationHandler().sendEmptyMessage(0x14);
                            BeanDoctor beanDoctor = new BeanDoctor();
                            beanDoctor.setIcon(imageUrls.get(0));
                            EventBus.getDefault().post(beanDoctor);
                        } else if (type.equals(TYPE_IMAGE)) {
                            MyApplication.getApplicationHandler().sendEmptyMessage(0x15);
                            BeanDoctor beanDoctor = new BeanDoctor();
                            beanDoctor.setImgList(imageUrls);
                            EventBus.getDefault().post(beanDoctor);
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                //如果是502错误，则请求重新传送当前文件
                if (e.toString().equals("retrofit2.adapter.rxjava.HttpException: HTTP 502 Bad Gateway")) {
                    uploadFilesAndSave(list, position);
                } else {
                    MyApplication.getApplicationHandler().sendEmptyMessage(0x12);
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    str = responseBody.string();
                    Log.i("图片上传", "返回数据" + str);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 压缩上传的图片文件
     *
     * @return
     */
    @NonNull
    private List<File> getCompressFiles() {
        List<File> compressFiles = new ArrayList<>();//暂时存放压缩后的图片，用来上传
        for (int i = 0; i < imagePathList.size(); i++) {
            File file = new File(imagePathList.get(i));
            final DecimalFormat df = new DecimalFormat("00.0000");
            try {
                String size = df.format(((double) (new FileInputStream(file).available())) / 1024 / 1024);
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
     * 将从网络加载的图片筛选出来，添加到等待保存到数据库的imageUrls中，并移出imagePathList，剩下的上传
     *
     * @return
     */
    @NonNull
    private List<String> getUploadList() {
        List<String> uploadList = new ArrayList<>();
        for (int i = 0; i < imagePathList.size(); i++) {
            if (!new File(imagePathList.get(i)).exists()) {
                imageUrls.add(imagePathList.get(i));
            } else {
                uploadList.add(imagePathList.get(i));
            }
        }
        Log.i("LYQ", "getUploadList:" + uploadList.get(0));
        return uploadList;
    }

    /**
     * 初始化数据，获取activity传过来的数据
     *
     * @param intent
     */
    private void initData(Intent intent) {
        BeanDoctor beanDoctor = (BeanDoctor) intent.getSerializableExtra("BeanDoctor");
        imagePathList = new ArrayList<>();
        if (beanDoctor != null) {
            if (!TextUtils.isEmpty(beanDoctor.getIcon())) {
                type = TYPE_HEAD_PORTRAIT;
                imagePathList.add(beanDoctor.getIcon());
            } else if (!beanDoctor.getImgList().isEmpty()) {
                type = TYPE_IMAGE;
                imagePathList = beanDoctor.getImgList();
            }
            sizeOfImagePathList = imagePathList.size();
            imageUrls = new ArrayList<>();//存放图片网络路径
            Log.i("LYQ", "initData:" + imagePathList.get(0));
        }
    }

}

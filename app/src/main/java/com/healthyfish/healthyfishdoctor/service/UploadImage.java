package com.healthyfish.healthyfishdoctor.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.healthyfish.healthyfishdoctor.MyApplication;
import com.healthyfish.healthyfishdoctor.POJO.BeanUploadImagesResp;
import com.healthyfish.healthyfishdoctor.POJO.MessageToServise;
import com.healthyfish.healthyfishdoctor.utils.RetrofitManagerUtils;


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
    int medRecId;//病历夹在数据库中的id
    int courseOfDiseaseId;//病程在数据库中的id
    int sizeOfImagePathList;//总共图片的size大小
    List<String> imagePathList;//原始图片路径
    List<String> imageUrls;//存放图片网络路径
    boolean updateImages = false;

    public UploadImage() {
        super("UploadImage");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //初始化数据，获取activity传过来的数据
        initData(intent);
        //获取要上传的图片的路径
        List<String> uplaodList = getUplaodList();
        //imagePathList.size() > 0，说明有图片要上传，否则直接保存
        if (uplaodList.size() > 0) {
            List<File> compressFiles = getCompressFiles();//压缩图片
            uploadFilesAndSave(compressFiles);//上传图片并保存返回的图片路径
        } else {

        }
    }


    /**
     * 上传图片，并将返回的路径保存到数据库
     *
     * @param compressFiles 压缩后的文件
     */
    private void uploadFilesAndSave(List<File> compressFiles) {
        for (int i = 0; i < compressFiles.size(); i++) {
            final List<File> list = new ArrayList<>();
            list.clear();
            list.add(compressFiles.get(i));
            RetrofitManagerUtils.getInstance(this, null).uploadFilesRetrofit(list, i, new Subscriber<ResponseBody>() {
                @Override
                public void onCompleted() {
                    //Toast.makeText(MyApplication.getContetxt(), "图片上传完成！", Toast.LENGTH_SHORT).show();
                    //Log.i("图片上传", "完成");
                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(MyApplication.getContetxt(), "图片上传失败", Toast.LENGTH_SHORT).show();
                    //Log.i("图片上传", "图片上传失败" + e.toString());
                }

                @Override
                public void onNext(ResponseBody responseBody) {
                    try {
                        String str = responseBody.string();
                        //Log.i("图片上传", "返回数据" + str);
                        if (str != null) {
                            BeanUploadImagesResp beanUploadImagesResp = new BeanUploadImagesResp();
                            beanUploadImagesResp = JSON.parseObject(str, BeanUploadImagesResp.class);
                            String url = "http://www.kangfish.cn" + beanUploadImagesResp.getUrl();
                            imageUrls.add(url);
                            //保存路径到数据库中
                            if (sizeOfImagePathList == imageUrls.size()) {

                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //网络图片路径样式:http://www.kangfish.cn/demo/downloadFile/upload/20170727/58651501120528555.png
                }
            });
        }
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
     * 将从网络加载的图片筛选出来，添加到等待保存到数据库的imageUrls中，并移出imagePathList，剩下的上传
     *
     * @return
     */
    @NonNull
    private List<String> getUplaodList() {
        List<String> uplaodlist = new ArrayList<>();
        for (int i = 0; i < imagePathList.size(); i++) {
            if (!new File(imagePathList.get(i)).exists()) {
                imageUrls.add(imagePathList.get(i));
            } else {
                uplaodlist.add(imagePathList.get(i));
            }
        }
        return uplaodlist;
    }

    /**
     * 初始化数据，获取activity传过来的数据
     *
     * @param intent
     */
    private void initData(Intent intent) {
        MessageToServise messageToServise = (MessageToServise) intent.getSerializableExtra("messageToService");
        medRecId = messageToServise.getMedRecId();
        courseOfDiseaseId = messageToServise.getCourseOfDiseaseId();
        imagePathList = new ArrayList<>();
        imagePathList = messageToServise.getImageList();
        sizeOfImagePathList = imagePathList.size();
        imageUrls = new ArrayList<>();//存放图片网络路径
    }

    }

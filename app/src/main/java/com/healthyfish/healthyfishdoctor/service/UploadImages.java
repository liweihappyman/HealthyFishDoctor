package com.healthyfish.healthyfishdoctor.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.healthyfish.healthyfishdoctor.MyApplication;
import com.healthyfish.healthyfishdoctor.POJO.BeanCourseOfDisease;
import com.healthyfish.healthyfishdoctor.POJO.BeanMedRec;
import com.healthyfish.healthyfishdoctor.POJO.BeanUploadImagesResp;
import com.healthyfish.healthyfishdoctor.POJO.MessageToServise;
import com.healthyfish.healthyfishdoctor.utils.RetrofitManagerUtils;


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
public class UploadImages extends IntentService {
    int medRecId;//病历夹在数据库中的id
    int courseOfDiseaseId;//病程在数据库中的id
    int sizeOfImagePathList;//总共图片的size大小
    List<String> imagePathList;//原始图片路径
    List<String> imageUrls;//存放图片网络路径
    //List<File> list = new ArrayList<>();//每次上传往里面放一张图片
    List<File> compressFiles = new ArrayList<>();
    boolean updateImages = false;
    //int position;

    public UploadImages() {
        super("UploadImages");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //初始化数据，获取activity传过来的数据
        initData(intent);
        //获取要上传的图片的路径
        List<String> uplaodlist = getUplaodList();
        //imagePathList.size() > 0，说明有图片要上传，否则直接保存
        if (uplaodlist.size() > 0) {
            List<File> list = new ArrayList<>();//每次上传往里面放一张图片
            List<File> compressFiles = getCompressFiles();//压缩图片
            for (int i = 0; i < compressFiles.size(); i++) {
                list.clear();
                list.add(compressFiles.get(i));
                //position = i;
                uploadFilesAndSave(list,i);//上传图片并保存返回的图片路径
            }
        } else {
            saveToDB(medRecId, courseOfDiseaseId, imageUrls,false);
        }
    }


    /**
     * 上传图片，并将返回的路径保存到数据库
     *
     *
     */
    private void uploadFilesAndSave(final List<File> list, final int position) {
       //list当前上传的图片文件，position：当前上传的图片文件在compressFiles中的位置
            RetrofitManagerUtils.getInstance(this, null).uploadFilesRetrofit(list, position, new Subscriber<ResponseBody>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                    //如果是502错误，则请求重新传送当前文件
                    if (e.toString().equals("retrofit2.adapter.rxjava.HttpException: HTTP 502 Bad Gateway")){
                        uploadFilesAndSave(list,position);
                    }else {
                        MyApplication.getApplicationHandler().sendEmptyMessage(0x12);
                    }
                }

                @Override
                public void onNext(ResponseBody responseBody) {
                    try {
                        String str = responseBody.string();
                        Log.i("图片上传", "返回数据" + str);
                        if (str != null) {
                            BeanUploadImagesResp beanUploadImagesResp = new BeanUploadImagesResp();
                            beanUploadImagesResp = JSON.parseObject(str, BeanUploadImagesResp.class);
                            String url = "http://www.kangfish.cn" + beanUploadImagesResp.getUrl();
                            imageUrls.add(url);
                            //保存路径到数据库中
                            if (sizeOfImagePathList == imageUrls.size()) {
                                saveToDB(medRecId, courseOfDiseaseId, imageUrls,true);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //网络图片路径样式:http://www.kangfish.cn/demo/downloadFile/upload/20170730/2861501383251816.png
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
                //Log.i("图片原来的大小" + i, "第  " + i + "  张:  " + size + "   M");
                if (Float.valueOf(size) < 0.6) {//如果文件小于600k，就不用压缩了
                    compressFiles.add(file);
                } else {
                    compressFiles.add(Luban.with(MyApplication.getContetxt()).load(file).get());
                    //查看压缩后的图片大小
//                        String compressSize = null;
//                        try {
//                            compressSize = df.format(((double) (new FileInputStream(Luban.with(MyApplication.getContetxt()).load(file).get()).available())) / 1024 / 1024);
//                            Log.i("压缩后的图片", " " + compressSize + "   M");
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
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

    /**
     * 保存到数据库中，因为在数据库中以关联表的形式保存，所以每次修改最好都要重新关联
     *
     * @param medRecId          病历夹表的id
     * @param courseOfDiseaseId 病程表的id
     * @param imageUrls         网络图片路径
     */
    private void saveToDB(int medRecId, int courseOfDiseaseId, List<String> imageUrls, boolean updateImages) {
        BeanMedRec beanMedRec = DataSupport.find(BeanMedRec.class, medRecId, true);
        BeanCourseOfDisease beanCourseOfDisease = DataSupport.find(BeanCourseOfDisease.class, courseOfDiseaseId);
        beanCourseOfDisease.setBeanMedRec(beanMedRec);//关联，这个很重要，beanMedRec对象必须是数据库已经存在的，可以通过find找出来的说明是存在的
        beanCourseOfDisease.setImgUrls(imageUrls);
        if (beanCourseOfDisease.save()) {
            if (updateImages) {
                MyApplication.getApplicationHandler().sendEmptyMessage(0x11);
            } else {
                MyApplication.getApplicationHandler().sendEmptyMessage(0x13);
            }
        }
    }
        //查看压缩后图片的大小
//            for (int k = 0; k < compressFiles.size(); k++) {
//                DecimalFormat df = new DecimalFormat("#.0000");
//                String size = null;
//                try {
//                    size = df.format(((double) (new FileInputStream(compressFiles.get(k)).available())) / 1024 / 1024);
//                    Log.i("等待上传的图片的大小" + k, "第  " + k + "  张:  " + size + "   M");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }

//异步压缩
        //                        Observable.just(file)
//                                .subscribeOn(AndroidSchedulers.mainThread())
//                                .map(new Func1<File, File>() {
//                                    @Override
//                                    public File call(File file) {
//                                        File f = null;
//                                        try {
//                                            f = Luban.with(CreateCourse.this).load(file).get();
//                                        } catch (IOException e) {
//                                            e.printStackTrace();
//                                        }
//                                        return f;
//                                    }
//                                })
//                                .observeOn(AndroidSchedulers.mainThread())
//                                .subscribe(new Subscriber<File>() {
//                                    @Override
//                                    public void onCompleted() {
//
//                                    }
//
//                                    @Override
//                                    public void onError(Throwable e) {
//                                        Log.i("压缩图片异常","" +e.toString());
//                                    }
//
//                                    @Override
//                                    public void onNext(File file) {
//                                        try {
//                                            Log.i("压缩后图片的大小",""+df.format((double)(new FileInputStream(file).available()) / 1024 / 1024 )+"M");
//                                        } catch (IOException e) {
//                                            e.printStackTrace();
//                                        }
//                                        compressFiles.add(file);
//                                    }
//                                });
//================================================================
//                        Luban.with(this)
//                                .load(file)                     //传人要压缩的图片
//                                .setCompressListener(new OnCompressListener() { //设置回调
//                                    @Override
//                                    public void onStart() {
//                                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
//                                    }
//                                    @Override
//                                    public void onSuccess(File file) {
//                                        // TODO 压缩成功后调用，返回压缩后的图片文件
//                                        try {
//                                            Log.i("压缩后图片的大小",""+df.format((double)(new FileInputStream(file).available()) / 1024 / 1024 )+"M");
//                                            compressFiles.add(file);
//                                        } catch (IOException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onError(Throwable e) {
//                                        // TODO 当压缩过程出现问题时调用
//                                    }
//                                }).launch();    //启动压缩
    }

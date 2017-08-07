package com.healthyfish.healthyfishdoctor.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bumptech.glide.Glide;
import com.foamtrace.photopicker.ImageCaptureManager;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.PhotoPreviewActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.foamtrace.photopicker.intent.PhotoPreviewIntent;
import com.healthyfish.healthyfishdoctor.MyApplication;
import com.healthyfish.healthyfishdoctor.POJO.BeanBaseKeyGetReq;
import com.healthyfish.healthyfishdoctor.POJO.BeanBaseKeyGetResp;
import com.healthyfish.healthyfishdoctor.POJO.BeanBaseKeySetReq;
import com.healthyfish.healthyfishdoctor.POJO.BeanBaseResp;
import com.healthyfish.healthyfishdoctor.POJO.BeanDoctor;
import com.healthyfish.healthyfishdoctor.POJO.BeanDoctorDB;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.adapter.CreateCourseGridAdapter;
import com.healthyfish.healthyfishdoctor.eventbus.DoctorInfo;
import com.healthyfish.healthyfishdoctor.eventbus.LoginEventBus;
import com.healthyfish.healthyfishdoctor.eventbus.SaveInformation;
import com.healthyfish.healthyfishdoctor.service.UploadImage;
import com.healthyfish.healthyfishdoctor.utils.MyToast;
import com.healthyfish.healthyfishdoctor.utils.OkHttpUtils;
import com.healthyfish.healthyfishdoctor.utils.RetrofitManagerUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import rx.Subscriber;

import static com.healthyfish.healthyfishdoctor.constant.constants.HttpHealthyFishyUrl;

/**
 * 描述：线上信息页面
 * 作者：LYQ on 2017/8/5.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class OnlineInformationFragment extends Fragment {

    @BindView(R.id.tv_prompt)
    TextView tvPrompt;
    @BindView(R.id.tv_authentication)
    TextView tvAuthentication;
    @BindView(R.id.civ_head_portrait)
    CircleImageView civHeadPortrait;
    @BindView(R.id.et_hospital)
    EditText etHospital;
    @BindView(R.id.et_department)
    EditText etDepartment;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_duties)
    EditText etDuties;
    @BindView(R.id.et_desc)
    EditText etDesc;
    Unbinder unbinder;
    @BindView(R.id.gv_select_img)
    GridView gvSelectImg;

    private String uid;

    private String type = "";
    private final String TYPE_HEAD_PORTRAIT = "HEAD_PORTRAIT";
    private final String TYPE_IMAGE = "IMAGE";

    private boolean isUpload = false;
    private boolean isSelectImg = false;
    private boolean isSelectHeadPortrait = false;
    private boolean isUploadImg = false;
    private boolean isUploadHeadPortrait = false;
    private boolean isChangeImg = false;
    private boolean isChangeHeadPortrait = false;

    private boolean isLoadFromNetwork = false;

    private boolean isFirstReq = true;
    private boolean isSecondReq = false;

    private String headPortraitUrl = "";
    private String headPortraitPath = "";

    private List<String> imgUrlList = new ArrayList<>();
    private List<String> imagePaths = new ArrayList<>();

    private CreateCourseGridAdapter gridAdapter;//适配器

    private ImageCaptureManager captureManager; // 相机拍照处理类
    public static final int REQUEST_CAMERA_CODE = 11;//摄像头拍照的标志
    public static final int REQUEST_PREVIEW_CODE = 12;//预览的标志

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_online_information, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        uid = MyApplication.uid;
        getInformationFromNetwork("cert_" + uid);
        refreshAdapter(imagePaths);
        selectImgListener();
        return view;

    }


    @OnClick({R.id.civ_head_portrait, R.id.tv_gender})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.civ_head_portrait:
                type = TYPE_HEAD_PORTRAIT;
                selectImg(1);
                break;
            case R.id.tv_gender:
                chooseGenderDialog();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void saveInformation(SaveInformation saveInformation) {
        Log.i("LYQ", "saveInformation");

        if (isLoadFromNetwork) {
            if (isChangeHeadPortrait || isChangeImg) {
                isUploadHeadPortraitOrImg();
            } else {
                uploadOnlineInformation();//上传完图片之后修改了个人信息的时候直接将个人信息上传即可不用再次上传图片
            }
        } else {
            if (!isUpload || isChangeHeadPortrait || isChangeImg) {
                isUploadHeadPortraitOrImg();
            } else {
                uploadOnlineInformation();//上传完图片之后修改了个人信息的时候直接将个人信息上传即可不用再次上传图片
            }
        }
    }

    /**
     * 判断上传头像还是证件照
     */
    private void isUploadHeadPortraitOrImg() {
        if ((isSelectHeadPortrait && isSelectImg) || isChangeHeadPortrait) {
            BeanDoctor beanDoctor = new BeanDoctor();
            beanDoctor.setIcon(headPortraitPath);
            uploadImg(beanDoctor);//先上传头像
        } else if (isSelectImg || isChangeImg) {
            BeanDoctor beanDoctor = new BeanDoctor();
            beanDoctor.setImgList(imagePaths);
            uploadImg(beanDoctor);//直接上传证件照
        } else {
            Toast.makeText(getActivity(),"请添加您的证件照",Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void uploadInformation(BeanDoctor beanDoctor) {
        Log.i("LYQ", "uploadInformation");
        isUpload = true;
        if (isSelectHeadPortrait && !isUploadHeadPortrait) {
            isUploadHeadPortrait = true;
            isChangeHeadPortrait = false;//修改头像上传后将修改头像标志位置false
            if (!TextUtils.isEmpty(beanDoctor.getIcon())) {
                headPortraitUrl = beanDoctor.getIcon();
                Log.i("LYQ", "headPortraitUrl:" + headPortraitUrl);

            }

            BeanDoctor beanDoctorForImg = new BeanDoctor();
            beanDoctorForImg.setImgList(imagePaths);
            uploadImg(beanDoctorForImg);//接着上传证件照、

        } else if (isSelectImg && !isUploadImg) {
            //Log.i("LYQ", "uploadInformation003");
            isUploadImg = true;
            isChangeImg = false;//修改证件照上传后将修改证件标志位置false
            imgUrlList.clear();
            imgUrlList.addAll(beanDoctor.getImgList());
            Log.i("LYQ", "imgUrlList.get(0):" + imgUrlList.get(0));
            uploadOnlineInformation();//上传完图片将个人信息上传
        }
    }

    /**
     * 性别选择对话框
     */
    private void chooseGenderDialog() {
        final String[] strings = new String[]{"男", "女"};
        new AlertDialog.Builder(getActivity())
                .setTitle("请选择性别")
                .setSingleChoiceItems(strings, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                tvGender.setText(strings[which]);
                                dialog.dismiss();
                            }
                        }
                )
                .setNegativeButton("取消", null)
                .show();
    }

    /**
     * 选择图片点击监听
     */
    private void selectImgListener() {
        gvSelectImg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 9) {//设置显示图片的数量
                    if (imagePaths.size() == position) {
                        type = TYPE_IMAGE;
                        selectImg(8);//选择图片
                    } else {
                        PhotoPreviewIntent intent = new PhotoPreviewIntent(getActivity());//图片预览
                        intent.setPhotoPaths((ArrayList) imagePaths);
                        intent.setCurrentItem(position);
                        startActivityForResult(intent, REQUEST_PREVIEW_CODE);
                    }
                }
            }
        });
    }

    /**
     * 适配器更新列表
     */
    private void refreshAdapter(List<String> paths) {
        // 处理返回照片地址
        if (imagePaths == null) {
            imagePaths = new ArrayList<>();
        }
        if (!isLoadFromNetwork) {
            imagePaths.clear();
        }
        imagePaths.addAll(paths);
        int i = imagePaths.size();
        if (gridAdapter == null) {
            gridAdapter = new CreateCourseGridAdapter(imagePaths, getActivity());
            gvSelectImg.setAdapter(gridAdapter);
            gridAdapter.notifyDataSetChanged();
        } else {
            gridAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置选择图片的张数
     */
    protected void selectImg(int maxNum) {
        PhotoPickerIntent intent = new PhotoPickerIntent(getActivity());
        intent.setSelectModel(SelectModel.MULTI);
        intent.setShowCarema(true); // 是否显示拍照， 默认false
        intent.setMaxTotal(maxNum); // 最多选择照片数量，默认为8
        //intent.setSelectedPaths((ArrayList) imagePaths); // 已选中的照片地址， 用于回显选中状态
        startActivityForResult(intent, REQUEST_CAMERA_CODE);
    }

    /**
     * 选择图片的返回处理
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    if (type.equals(TYPE_HEAD_PORTRAIT)) {
                        isSelectHeadPortrait = true;

                        if (isLoadFromNetwork) {
                            isChangeHeadPortrait = true;
                            isUploadHeadPortrait = false;

                            //isSelectImg = false;
                        }
                        if (isUpload) {
                            isChangeHeadPortrait = true;
                            isUploadHeadPortrait = false;


                            //isSelectImg = false;

                        }
                        headPortraitPath = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT).get(0);
                        Glide.with(getActivity()).load(data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT).get(0)).into(civHeadPortrait);
                    } else if (type.equals(TYPE_IMAGE)) {
                        isSelectImg = true;

                        if (isLoadFromNetwork) {
                            isChangeImg = true;
                            isUploadImg = false;

                            //isSelectHeadPortrait = false;
                        }
                        if (isUpload) {
                            isChangeImg = true;
                            isUploadImg = false;

                            //isSelectHeadPortrait = false;

                        }
                        refreshAdapter(data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT));
                    }
                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    imagePaths.clear();
                    refreshAdapter(data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT));
                    break;
                // 调用相机拍照
                case ImageCaptureManager.REQUEST_TAKE_PHOTO:
                    if (captureManager.getCurrentPhotoPath() != null) {
                        captureManager.galleryAddPic();
                        ArrayList<String> paths = new ArrayList<>();
                        paths.add(captureManager.getCurrentPhotoPath());
                        if (type.equals(TYPE_HEAD_PORTRAIT)) {
                            isSelectHeadPortrait = true;
                            if (isLoadFromNetwork) {
                                isChangeHeadPortrait = true;
                                isUploadHeadPortrait = false;

                                //isSelectImg = false;
                            }

                            if (isUpload) {
                                isChangeHeadPortrait = true;
                                isUploadHeadPortrait = false;

                                //isSelectImg = false;
                            }
                            headPortraitPath = paths.get(0);
                            Glide.with(getActivity()).load(data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT).get(0)).into(civHeadPortrait);
                        } else if (type.equals(TYPE_IMAGE)) {
                            isSelectImg = true;

                            if (isLoadFromNetwork) {
                                isChangeImg = true;
                                isUploadImg = false;

                                //isSelectHeadPortrait = false;
                            }
                            if (isUpload) {
                                isChangeImg = true;
                                isUploadImg = false;

                                //isSelectHeadPortrait = false;

                            }
                            refreshAdapter(paths);
                        }
                    }
                    break;
            }
        }
    }

    /**
     * 获取输入的个人信息
     */
    private String getInputInformation() {

        String hospTxt = etHospital.getText().toString().trim();
        String deptTxt = etDepartment.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String gender = tvGender.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String reister = etDuties.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();

        Log.i("LYQ", "获取需要上传的个人信息");

        if (!TextUtils.isEmpty(hospTxt) && !TextUtils.isEmpty(deptTxt) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(gender) &&
                !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(reister) && !TextUtils.isEmpty(desc)) {
            if (phone.length() == 11) {
                if (!imgUrlList.isEmpty() && imgUrlList.size() == imagePaths.size()) {
                    BeanDoctor beanDoctor = new BeanDoctor();
                    beanDoctor.setType(2);
                    beanDoctor.setDoctId(uid);
                    beanDoctor.setHosp("");
                    beanDoctor.setHospTxt(hospTxt);
                    beanDoctor.setDept("");
                    beanDoctor.setDeptTxt(deptTxt);
                    beanDoctor.setDoct(0);//默认值0
                    beanDoctor.setName(name);
                    beanDoctor.setGender(gender);
                    beanDoctor.setPhone(phone);
                    beanDoctor.setReister(reister);
                    beanDoctor.setDesc(desc);
                    beanDoctor.setDoctReg("");
                    beanDoctor.setTitle(hospTxt + "-" + deptTxt + "-" + name);
                    beanDoctor.setIcon(headPortraitUrl);
                    beanDoctor.setImgList(imgUrlList);
                    String jsonStr = JSON.toJSONString(beanDoctor);
                    Log.i("LYQ", "需要上传的个人信息："+jsonStr);
                    return jsonStr;
                } else {
                    Toast.makeText(getActivity(),"图片上传出错",Toast.LENGTH_SHORT).show();
                    return null;
                }
            } else {
                Toast.makeText(getActivity(),"请确认您的手机号准确无误",Toast.LENGTH_SHORT).show();
                return null;
            }
        } else {
            Toast.makeText(getActivity(),"请填写完所有信息",Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    /**
     * 上传线上个人信息
     */
    private void uploadOnlineInformation() {
        String value = getInputInformation();

        if (TextUtils.isEmpty(value) && TextUtils.isEmpty(uid)) {
            return;
        }
        BeanBaseKeySetReq beanBaseKeySetReq = new BeanBaseKeySetReq();
        beanBaseKeySetReq.setKey("certReq_" + uid);
        beanBaseKeySetReq.setValue(value);

        RetrofitManagerUtils.getInstance(MyApplication.getContetxt(), null).getHealthyInfoByRetrofit(OkHttpUtils.getRequestBody(beanBaseKeySetReq), new Subscriber<ResponseBody>() {

            String upInfoResp = "";

            @Override
            public void onCompleted() {
                if (!TextUtils.isEmpty(upInfoResp)) {
                    if (upInfoResp.substring(0, 1).equals("{")) {
                        BeanBaseResp beanBaseResp = JSON.parseObject(upInfoResp, BeanBaseResp.class);
                        if (beanBaseResp.getCode() == 0) {
                            Toast.makeText(getActivity(),"成功上传个人信息，请等待审核",Toast.LENGTH_SHORT).show();
                            DoctorInfo doctorInfo = new DoctorInfo();
                            doctorInfo.setUpload(true);
                            doctorInfo.setGender(tvGender.getText().toString().trim());
                            doctorInfo.setImgList(imgUrlList);
                            EventBus.getDefault().post(doctorInfo);//通知关联挂号页面上传关联挂号信息
                            EventBus.getDefault().post(new LoginEventBus(etName.getText().toString().trim(),headPortraitUrl,true));//通知个人中心刷新个人信息
                        } else {
                            Toast.makeText(getActivity(),"上传个人信息失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getActivity(),"上传个人信息出错了",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getActivity(),"上传个人信息出错",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    upInfoResp = responseBody.string();
                    Log.i("LYQ", "上传医生信息响应：" + upInfoResp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 上传证件照
     */
    private void uploadImg(BeanDoctor beanDoctor) {
        Intent startUploadImage = new Intent(getActivity(), UploadImage.class);
        startUploadImage.putExtra("BeanDoctor", beanDoctor);
        getActivity().startService(startUploadImage);
        Log.i("LYQ", "uploadImg");
    }

    /**
     * 从服务器获取医生信息
     */
    private void getInformationFromNetwork(String reqKey) {
        BeanBaseKeyGetReq beanBaseKeyGetReq = new BeanBaseKeyGetReq();
        beanBaseKeyGetReq.setKey(reqKey);

        RetrofitManagerUtils.getInstance(getActivity(), null).getHealthyInfoByRetrofit(OkHttpUtils.getRequestBody(beanBaseKeyGetReq), new Subscriber<ResponseBody>() {

            String infoResp = "";

            @Override
            public void onCompleted() {
                if (!TextUtils.isEmpty(infoResp)) {
                    if (infoResp.substring(0, 1).equals("{")) {
                        BeanBaseKeyGetResp beanBaseKeyGetResp = JSON.parseObject(infoResp, BeanBaseKeyGetResp.class);
                        if (beanBaseKeyGetResp.getCode() == 0) {
                            if (!TextUtils.isEmpty(beanBaseKeyGetResp.getValue())) {
                                String str = beanBaseKeyGetResp.getValue().toString().substring(0, 1);
                                if (!str.equals("{")) {//防止之前网页版的个人信息格式不匹配导致出错
                                    MyToast.showToast(MyApplication.getContetxt(), "您的个人信息有误，请重新修改上传");
                                    EventBus.getDefault().post(new LoginEventBus(null,true));
                                    return;
                                }
                                Log.i("LYQ", "beanBaseKeyGetResp.getValue():" + beanBaseKeyGetResp.getValue());
                                if (isFirstReq) {//第一次请求时已审核，第二次请求时未审核
                                    isFirstReq = false;
                                    setTextColor("认证状态：已认证");
                                    BeanDoctor beanDoctor = JSON.parseObject(beanBaseKeyGetResp.getValue(), BeanDoctor.class);

                                    saveToDB("cert_" + uid, beanDoctor, true);

                                    initWidget(beanDoctor);

                                    DoctorInfo doctorInfo = new DoctorInfo();
                                    doctorInfo.setUpload(false);
                                    doctorInfo.setPast(true);
                                    doctorInfo.setImgList(beanDoctor.getImgList());
                                    EventBus.getDefault().post(doctorInfo);//通知关联挂号页面刷新显示
                                } else {
                                    setTextColor("认证状态：未认证");
                                    BeanDoctor beanDoctor = JSON.parseObject(beanBaseKeyGetResp.getValue(), BeanDoctor.class);

                                    saveToDB("certReq_" + uid, beanDoctor, false);

                                    initWidget(beanDoctor);

                                    DoctorInfo doctorInfo = new DoctorInfo();
                                    doctorInfo.setUpload(false);
                                    doctorInfo.setPast(false);
                                    doctorInfo.setImgList(beanDoctor.getImgList());
                                    EventBus.getDefault().post(doctorInfo);//通知关联挂号页面刷新显示
                                }
                            } else {//第一次请求时未审核
                                if (!isSecondReq) {
                                    isFirstReq = false;
                                    getInformationFromNetwork("certReq_" + uid);
                                    isSecondReq = true;
                                } else {
                                    Toast.makeText(getActivity(),"您还未上传个人信息",Toast.LENGTH_SHORT).show();
                                }
                            }

                        } else {
                            Toast.makeText(getActivity(),"加载个人信息失败",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(),"加载个人信息出错啦",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(),"加载个人信息出错了",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getActivity(),"加载个人信息出错",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    infoResp = responseBody.string();
                    Log.i("LYQ", "infoResp:" + infoResp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 将个人信息保存到数据库
     *
     * @param key
     * @param beanDoctor
     * @param isPast
     */
    private void saveToDB(String key, BeanDoctor beanDoctor, boolean isPast) {
        BeanDoctorDB beanDoctorDB = new BeanDoctorDB();
        beanDoctorDB.setDoctId(beanDoctor.getDoctId());
        beanDoctorDB.setKey(key);
        beanDoctorDB.setType(beanDoctor.getType());
        beanDoctorDB.setHosp(beanDoctor.getHosp());
        beanDoctorDB.setHospTxt(beanDoctor.getHospTxt());
        beanDoctorDB.setDept(beanDoctor.getDept());
        beanDoctorDB.setDeptTxt(beanDoctor.getDeptTxt());
        beanDoctorDB.setName(beanDoctor.getName());
        beanDoctorDB.setGender(beanDoctor.getGender());
        beanDoctorDB.setDoct(beanDoctor.getDoct());
        beanDoctorDB.setDoctReg(beanDoctor.getDoctReg());
        beanDoctorDB.setDesc(beanDoctor.getDesc());
        beanDoctorDB.setIcon(beanDoctor.getIcon());
        String jsonImages = JSONArray.toJSONString(beanDoctor.getImgList());
        beanDoctorDB.setImages(jsonImages);
        beanDoctorDB.setReister(beanDoctor.getReister());
        beanDoctorDB.setPhone(beanDoctor.getPhone());
        beanDoctorDB.setPast(isPast);
        Log.i("LYQ", "保存到数据库：" + JSON.toJSONString(beanDoctorDB));
        boolean isSave = beanDoctorDB.saveOrUpdate("doctId = ?", uid);
        if (!isSave) {
            beanDoctorDB.saveOrUpdate("doctId = ?", uid);
        }
    }

    /**
     * 显示个人信息
     *
     * @param beanDoctor
     */
    private void initWidget(BeanDoctor beanDoctor) {
        if (beanDoctor != null) {
            if (!TextUtils.isEmpty(beanDoctor.getIcon())) {
                Glide.with(getActivity()).load(HttpHealthyFishyUrl + beanDoctor.getIcon()).into(civHeadPortrait);
                headPortraitUrl = beanDoctor.getIcon();
            }
            etHospital.setText(beanDoctor.getHospTxt());
            etDepartment.setText(beanDoctor.getDeptTxt());
            etName.setText(beanDoctor.getName());
            tvGender.setText(beanDoctor.getGender());
            etPhone.setText(beanDoctor.getPhone());
            etDuties.setText(beanDoctor.getReister());
            etDesc.setText(beanDoctor.getDesc());
            //展示证件图片
            imagePaths.clear();
            List<String> imgList = new ArrayList<>();
            for (String url : beanDoctor.getImgList()) {
                imgList.add(HttpHealthyFishyUrl + url);
            }
            imagePaths.addAll(imgList);
            isLoadFromNetwork = true;
            gridAdapter.notifyDataSetChanged();
            imgUrlList.clear();
            imgUrlList.addAll(beanDoctor.getImgList());
        }
    }

    /**
     * 设置认证状态文本不同颜色
     *
     * @param text
     */
    private void setTextColor(String text) {
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append(text);
        //设置字体颜色
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.color_secondary));
        spannableString.setSpan(colorSpan, 5, text.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tvAuthentication.setText(spannableString);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }
}

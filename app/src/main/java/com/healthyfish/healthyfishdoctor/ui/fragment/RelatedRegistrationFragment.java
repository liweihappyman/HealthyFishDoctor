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
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.foamtrace.photopicker.ImageCaptureManager;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.PhotoPreviewActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.foamtrace.photopicker.intent.PhotoPreviewIntent;
import com.healthyfish.healthyfishdoctor.MainActivity;
import com.healthyfish.healthyfishdoctor.MyApplication;
import com.healthyfish.healthyfishdoctor.POJO.BeanBaseKeySetReq;
import com.healthyfish.healthyfishdoctor.POJO.BeanBaseResp;
import com.healthyfish.healthyfishdoctor.POJO.BeanDoctor;
import com.healthyfish.healthyfishdoctor.POJO.BeanHospDeptDoctListReq;
import com.healthyfish.healthyfishdoctor.POJO.BeanHospDeptDoctListRespItem;
import com.healthyfish.healthyfishdoctor.POJO.BeanHospDeptListReq;
import com.healthyfish.healthyfishdoctor.POJO.BeanHospDeptListRespItem;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.adapter.CourseGridAdapter;
import com.healthyfish.healthyfishdoctor.adapter.CreateCourseGridAdapter;
import com.healthyfish.healthyfishdoctor.eventbus.DoctorInfo;
import com.healthyfish.healthyfishdoctor.eventbus.SaveInformation;
import com.healthyfish.healthyfishdoctor.ui.widget.MyGridView;
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

import static com.healthyfish.healthyfishdoctor.constant.Constants.HttpHealthyFishyUrl;
import static com.healthyfish.healthyfishdoctor.constant.Constants.first;

/**
 * 描述：关联挂号页面
 * 作者：LYQ on 2017/8/5.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */


public class RelatedRegistrationFragment extends Fragment {

    @BindView(R.id.tv_prompt)
    TextView tvPrompt;
    @BindView(R.id.tv_authentication)
    TextView tvAuthentication;
    @BindView(R.id.civ_head_portrait)
    CircleImageView civHeadPortrait;
    @BindView(R.id.tv_hospital)
    TextView tvHospital;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_duties)
    TextView tvDuties;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    Unbinder unbinder;
    @BindView(R.id.gv_select_img)
    MyGridView gvSelectImg;

    private String uid;

    private int selectedDeptItme = 0;//选择的选项
    private int selectedDoctItme = 0;//选择的选项
    private CourseGridAdapter mGridAdapter;
    private List<BeanHospDeptListRespItem> mDeptList = new ArrayList<>();
    private List<BeanHospDeptDoctListRespItem> mDoctList = new ArrayList<>();
    private List<String> imgUrlList = new ArrayList<>();
    private CreateCourseGridAdapter gridAdapter;//适配器
    private List<String> imagePaths = new ArrayList<>();//（后面用的）图片的路径list
    private ImageCaptureManager captureManager; // 相机拍照处理类
    public static final int REQUEST_CAMERA_CODE = 11;//摄像头拍照的标志
    public static final int REQUEST_PREVIEW_CODE = 12;//预览的标志

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_related_registration, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        uid = MyApplication.uid;
        //initImgGv();
        return view;
    }

    @OnClick({R.id.tv_hospital, R.id.tv_department, R.id.tv_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_hospital:
                choiceHospital();
                break;
            case R.id.tv_department:
                if (!TextUtils.isEmpty(tvHospital.getText().toString())) {
                    choiceDepartment();
                } else {
                    MyToast.showToast(getActivity(), "请先选择医院");
                }
                break;
            case R.id.tv_name:
                if (!TextUtils.isEmpty(tvDepartment.getText().toString())) {
                    choiceName();
                } else {
                    MyToast.showToast(getActivity(), "请先选择部门");
                }
                break;
        }
    }

    /**
     * 展示审核状态和证件图片
     *
     * @param doctorInfo
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initInformation(DoctorInfo doctorInfo) {
        Log.i("LYQ", "initInformation");

        if (doctorInfo != null) {
            if (doctorInfo.isUpload()) {
                String hospTxt = tvHospital.getText().toString().trim();
                String deptTxt = tvDepartment.getText().toString().trim();
                String name = tvName.getText().toString().trim();

                if (!TextUtils.isEmpty(hospTxt) && !TextUtils.isEmpty(deptTxt) && !TextUtils.isEmpty(name) ) {
                    imgUrlList.clear();
                    imgUrlList.addAll(doctorInfo.getImgList());
                    String value = getInputInformation(doctorInfo.getGender());
                    uploadRegistrationInformation(value);
                } else {
                    toPersonalCenter();
                }

            } else {
                if (doctorInfo.isPast()) {
                    setTextColor("认证状态：已认证");
                } else {
                    setTextColor("认证状态：未认证");
                }

            }

        }
    }

    /**
     * 选择医院
     */
    private void choiceHospital() {
        String[] hsopitals = {"柳州市中医院"};
        showChoice("请选择医院", hsopitals, tvHospital);
    }

    /**
     * 选择部门
     */
    private void choiceDepartment() {
        BeanHospDeptListReq beanHospDeptListReq = new BeanHospDeptListReq();
        beanHospDeptListReq.setHosp("lzzyy");

        RetrofitManagerUtils.getInstance(getActivity(), null).getHealthyInfoByRetrofit(OkHttpUtils.getRequestBody(beanHospDeptListReq), new Subscriber<ResponseBody>() {

            String deptListResp = "";

            @Override
            public void onCompleted() {
                if (!TextUtils.isEmpty(deptListResp)) {
                    List<JSONObject> objList = JSONArray.parseObject(deptListResp, List.class);
                    String[] depts = new String[objList.size()];
                    if (!objList.isEmpty()) {
                        mDeptList.clear();
                        for (int i = 0; i < objList.size(); i++) {
                            String jsonString = objList.get(i).toJSONString();
                            BeanHospDeptListRespItem beanHospDeptListRespItem = JSON.parseObject(jsonString, BeanHospDeptListRespItem.class);
                            mDeptList.add(beanHospDeptListRespItem);
                            depts[i] = beanHospDeptListRespItem.getDEPT_NAME();
                        }
                    }
                    showChoice("请选择部门", depts, tvDepartment);
                } else {
                    MyToast.showToast(getActivity(), "加载部门信息出错");
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    deptListResp = responseBody.string();
                    Log.i("LYQ", "deptListResp:" + deptListResp);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 选择医生姓名
     */
    private void choiceName() {
        BeanHospDeptDoctListReq beanHospDeptDoctListReq = new BeanHospDeptDoctListReq();
        beanHospDeptDoctListReq.setHosp("lzzyy");
        beanHospDeptDoctListReq.setDept(mDeptList.get(selectedDeptItme).getDEPT_CODE());

        RetrofitManagerUtils.getInstance(getActivity(), null).getHealthyInfoByRetrofit(OkHttpUtils.getRequestBody(beanHospDeptDoctListReq), new Subscriber<ResponseBody>() {

            String doctListResp = "";

            @Override
            public void onCompleted() {
                if (!TextUtils.isEmpty(doctListResp)) {
                    List<JSONObject> objList = JSONArray.parseObject(doctListResp, List.class);
                    String[] names = new String[objList.size()];
                    if (!objList.isEmpty()) {
                        mDoctList.clear();
                        for (int i = 0; i < objList.size(); i++) {
                            String jsonString = objList.get(i).toJSONString();
                            BeanHospDeptDoctListRespItem beanHospDeptListRespItem = JSON.parseObject(jsonString, BeanHospDeptDoctListRespItem.class);
                            mDoctList.add(beanHospDeptListRespItem);
                            names[i] = beanHospDeptListRespItem.getDOCTOR_NAME();
                        }
                    }
                    showChoice("请选择您的姓名", names, tvName);
                } else {
                    MyToast.showToast(getActivity(), "加载部门医生信息出错");
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    doctListResp = responseBody.string();
                    Log.i("LYQ", "doctListResp:" + doctListResp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 选择选项对话框
     */
    private void showChoice(final String title, final String[] strings, final TextView textView) {
        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setSingleChoiceItems(strings, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                textView.setText(strings[which]);
                                if (title.equals("请选择部门")) {
                                    selectedDeptItme = which;
                                    clearAll();
                                } else if (title.equals("请选择您的姓名")) {
                                    selectedDoctItme = which;
                                    loadDoctInfo();
                                }
                                dialog.dismiss();
                            }
                        }
                ).show();
    }

    /**
     * 加载医生信息
     */
    private void loadDoctInfo() {
        Glide.with(getActivity()).load(HttpHealthyFishyUrl + mDoctList.get(selectedDoctItme).getZHAOPIAN()).into(civHeadPortrait);
        tvDuties.setText(mDoctList.get(selectedDoctItme).getREISTER_NAME());
        tvDesc.setText(mDoctList.get(selectedDoctItme).getWEB_INTRODUCE());
    }

    /**
     * 清除已输入的信息
     */
    private void clearAll() {
        tvName.setText("");
        Glide.with(getActivity()).load(R.mipmap.ic_logo).into(civHeadPortrait);
        tvDuties.setText("");
        tvDesc.setText("");
    }

    /**
     * 初始化显示证件图片
     */
    private void initImgGv() {
        mGridAdapter = new CourseGridAdapter(null, getActivity(), imgUrlList);
        gvSelectImg.setAdapter(gridAdapter);
    }


    /**
     * 获取输入的个人信息
     */
    private String getInputInformation(String gender) {

        String hospTxt = tvHospital.getText().toString().trim();
        String deptTxt = tvDepartment.getText().toString().trim();
        String name = tvName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String reister = tvDuties.getText().toString().trim();
        String desc = tvDesc.getText().toString().trim();

        if (!TextUtils.isEmpty(hospTxt) && !TextUtils.isEmpty(deptTxt) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone) &&
                !TextUtils.isEmpty(reister) && !TextUtils.isEmpty(desc)) {
            if (phone.length() == 11) {
                if (!imgUrlList.isEmpty()) {
                    BeanDoctor beanDoctor = new BeanDoctor();
                    beanDoctor.setType(1);
                    beanDoctor.setHosp("lzzyy");
                    beanDoctor.setDeptTxt("柳州市中医院");
                    beanDoctor.setDept(mDeptList.get(selectedDeptItme).getDEPT_CODE());
                    beanDoctor.setDeptTxt(mDeptList.get(selectedDeptItme).getDEPT_NAME());
                    beanDoctor.setDoct(mDoctList.get(selectedDoctItme).getSTAFF_NO());
                    beanDoctor.setName(mDoctList.get(selectedDoctItme).getDOCTOR_NAME());
                    beanDoctor.setGender(gender);
                    beanDoctor.setPhone(etPhone.getText().toString().trim());
                    beanDoctor.setReister(mDoctList.get(selectedDoctItme).getREISTER_NAME());
                    beanDoctor.setDesc(mDoctList.get(selectedDoctItme).getWEB_INTRODUCE());
                    beanDoctor.setDoctReg(mDoctList.get(selectedDoctItme).getDOCTOR());
                    beanDoctor.setTitle("柳州市中医院-" + mDeptList.get(selectedDeptItme).getDEPT_NAME() + "-" + mDoctList.get(selectedDoctItme).getDOCTOR_NAME());
                    beanDoctor.setIcon(mDoctList.get(selectedDoctItme).getZHAOPIAN());
                    beanDoctor.setImgList(imgUrlList);
                    String jsonStr = JSON.toJSONString(beanDoctor);
                    Log.i("LYQ", jsonStr);
                    return jsonStr;
                } else {
                    Toast.makeText(getActivity(),"图片未上传",Toast.LENGTH_SHORT).show();
                    return null;
                }
            } else {
                Toast.makeText(getActivity(),"请确认您的手机号准确无误",Toast.LENGTH_SHORT).show();
                return null;
            }
        } else {
            Toast.makeText(getActivity(),"请选择完所有信息",Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    /**
     * 上传挂号信息
     */
    private void uploadRegistrationInformation(String value) {

        if (TextUtils.isEmpty(value) && TextUtils.isEmpty(uid)) {
            return;
        }
        BeanBaseKeySetReq beanBaseKeySetReq = new BeanBaseKeySetReq();
        beanBaseKeySetReq.setKey("certReq_" + uid);
        beanBaseKeySetReq.setValue(value);

        Log.i("LYQ", "关联挂号请求：" + value);

        RetrofitManagerUtils.getInstance(MyApplication.getContetxt(), null).getHealthyInfoByRetrofit(OkHttpUtils.getRequestBody(beanBaseKeySetReq), new Subscriber<ResponseBody>() {

            String upInfoResp = "";

            @Override
            public void onCompleted() {
                if (!TextUtils.isEmpty(upInfoResp)) {
                    if (upInfoResp.substring(0, 1).equals("{")) {
                        BeanBaseResp beanBaseResp = JSON.parseObject(upInfoResp, BeanBaseResp.class);
                        if (beanBaseResp.getCode() == 0) {
                            Toast.makeText(getActivity(),"成功关联挂号，请等待审核",Toast.LENGTH_SHORT).show();
                            toPersonalCenter();
                        } else {
                            Toast.makeText(getActivity(),"关联挂号失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getActivity(),"关联挂号出错了",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getActivity(),"关联挂号出错",Toast.LENGTH_SHORT).show();
                Log.i("LYQ", "关联挂号错误提示：" + e.toString());
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    upInfoResp = responseBody.string();
                    Log.i("LYQ", "关联挂号响应：" + upInfoResp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

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

    /**
     * 推出本页面，回到个人中心
     */
    private void toPersonalCenter() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(intent);
        getActivity().finish();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }
}

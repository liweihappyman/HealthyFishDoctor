package com.healthyfish.healthyfishdoctor.ui.activity.medical_record;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.healthyfish.healthyfishdoctor.MyApplication;
import com.healthyfish.healthyfishdoctor.POJO.BeanBaseKeyAddReq;
import com.healthyfish.healthyfishdoctor.POJO.BeanBaseKeyAddResp;
import com.healthyfish.healthyfishdoctor.POJO.BeanBaseKeyRemReq;
import com.healthyfish.healthyfishdoctor.POJO.BeanBaseKeySetReq;
import com.healthyfish.healthyfishdoctor.POJO.BeanBaseResp;
import com.healthyfish.healthyfishdoctor.POJO.BeanCourseOfDisease;
import com.healthyfish.healthyfishdoctor.POJO.BeanMedRec;
import com.healthyfish.healthyfishdoctor.POJO.BeanMedRecUser;
import com.healthyfish.healthyfishdoctor.POJO.BeanUserLoginReq;
import com.healthyfish.healthyfishdoctor.POJO.ImMsgBean;
import com.healthyfish.healthyfishdoctor.POJO.MessageToServise;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.adapter.CourseOfDiseaseAdapter;
import com.healthyfish.healthyfishdoctor.constant.Constants;
import com.healthyfish.healthyfishdoctor.eventbus.NoticeMessage;
import com.healthyfish.healthyfishdoctor.service.UploadImages;
import com.healthyfish.healthyfishdoctor.ui.widget.DatePickerDialog;
import com.healthyfish.healthyfishdoctor.utils.DateTimeUtil;
import com.healthyfish.healthyfishdoctor.utils.MySharedPrefUtil;
import com.healthyfish.healthyfishdoctor.utils.OkHttpUtils;
import com.healthyfish.healthyfishdoctor.utils.RetrofitManagerUtils;
import com.healthyfish.healthyfishdoctor.utils.Utils1;
import com.healthyfish.healthyfishdoctor.utils.mqtt_utils.MqttUtil;

import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import rx.Subscriber;

import static com.healthyfish.healthyfishdoctor.ui.activity.medical_record.CreateCourse.CREATE_COURSE_RESULT_SAVE;
import static com.healthyfish.healthyfishdoctor.ui.activity.medical_record.CreateCourse.CREATE_COURSE_RESULT_UPDATE_OR_DEL;


/**
 * 描述：电子病历
 * 作者：WKJ on 2017/7/4.
 * 邮箱：
 * 编辑：WKJ
 */
public class NewMedRec extends AppCompatActivity implements View.OnClickListener {
    //标志病历夹是要更新还是直接保存（默认是更新状态）  save:表示新建的直接保存
    public String SAVE_OR_UPDATE = "update";
    public static int ID = 0;//记录本次所编辑的病历夹的id
    public static final int COURSE_OF_DISEASE = 33;//跳转进入病程页面的标志
    public static final int INFO = 34;
    public static final int LABLE = 35;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lable)
    TextView lable;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.patient_info)
    TextView patientInfo;
    @BindView(R.id.clinical_time)
    TextView clinicalTime;
    @BindView(R.id.create_course)
    TextView createCourse;
    @BindView(R.id.course_of_disease)
    RecyclerView courseOfDiseaseRecyclerView;
    @BindView(R.id.save)
    TextView save;
    @BindView(R.id.diagnosis)
    EditText diagnosis;
    @BindView(R.id.disease_info)
    EditText diseaseInfo;
    @BindView(R.id.clinical_department)
    EditText clinicalDepartment;
    String phoneNumber;

    private CourseOfDiseaseAdapter courseOfDiseaseAdapter;
    private BeanMedRec medRec = new BeanMedRec();
    private List<String> imagePaths = new ArrayList<>();
    private List<BeanCourseOfDisease> listCourseOfDiseases = new ArrayList<BeanCourseOfDisease>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_med_rec);
        ButterKnife.bind(this);
        toolbarTitle.setText("新建病历");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back_icon);
        }
        initListener();
        ////根据进入该活动的方式加载数据
        judgeTypeAndInitDate();
        initList(listCourseOfDiseases, courseOfDiseaseRecyclerView);//初始化病程列表
    }

    //判断是点击item进来的还是点击新建病历夹进来的，并执行相应的初始化操作
    // （从聊天界面进来的是Constants.POSITION_MED_REC == -2)
    private void judgeTypeAndInitDate() {
        if (Constants.POSITION_MED_REC == -1) {
            clinicalTime.setText(Utils1.getTime());
            SAVE_OR_UPDATE = "save";//标志位新建，用于判断保存的方式
            medRec = new BeanMedRec();
        } else if (Constants.POSITION_MED_REC == -2) {//从聊天界面点击病历进来的
            try {
                String key = getIntent().getStringExtra("MdrKey");
                Constants.MED_REC_USER_PHONE = getIntent().getStringExtra("PhoneNumber");
                Constants.MED_REC_USER_ID = DataSupport.where("name = ?", Constants.MED_REC_USER_PHONE).find(BeanMedRecUser.class).get(0).getId();
                List<BeanMedRec> list = DataSupport.where("key = ?", key).find(BeanMedRec.class);
                if (list.size() > 0) {
                    ID = list.get(0).getId();
                    medRec = DataSupport.find(BeanMedRec.class, ID, true);
                    initdata();
                }
            }catch (Exception e){

            }
        } else {//点击病历列表进来的
            ID = getIntent().getIntExtra("id", 0);
            medRec = DataSupport.find(BeanMedRec.class, ID, true);
            initdata();
        }
    }

    private void initdata() {

        listCourseOfDiseases = medRec.getListCourseOfDisease();
        setLableTv(lable, medRec);
        setInfo();
        if (medRec.getDiagnosis() != null) {
            diagnosis.setText(medRec.getDiagnosis());
        }
        if (medRec.getDiseaseInfo() != null) {
            diseaseInfo.setText(medRec.getDiseaseInfo());
        }
        if (medRec.getClinicalDepartement() != null) {
            clinicalDepartment.setText(medRec.getClinicalDepartement());
        }
        clinicalTime.setText(medRec.getClinicalTime());
    }

    //适配器
    private void initList(List<BeanCourseOfDisease> beanCourseOfDiseases, RecyclerView recyclerView) {
        CourseOfDiseaseAdapter courseOfDiseaseAdapter = new CourseOfDiseaseAdapter(this, beanCourseOfDiseases);
        LinearLayoutManager lmg = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lmg);
        recyclerView.setAdapter(courseOfDiseaseAdapter);
        courseOfDiseaseAdapter.setOnItemClickListener(new CourseOfDiseaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Constants.POSITION_COURSE = position;
                Intent intent = new Intent(NewMedRec.this, CreateCourse.class);
                intent.putExtra("Course", medRec);
                startActivityForResult(intent, COURSE_OF_DISEASE);
            }
        });
    }

    //初始化监听
    private void initListener() {
        lable.setOnClickListener(this);
        patientInfo.setOnClickListener(this);
        clinicalTime.setOnClickListener(this);
        createCourse.setOnClickListener(this);
        save.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.med_rec, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.del:
                deleteAndGoback();
                break;
        }
        return true;
    }


    //执行删除操作并跳转回到AllMedRed页面，新建状态则提示没有可删除的病历
    private void deleteAndGoback() {
        if (Constants.POSITION_MED_REC != -1) {
            showDelDialog();
        } else {
            Toast.makeText(this, "没有可删除的病历哦", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 删除提示对话框
     */
    private void showDelDialog() {
        new AlertDialog.Builder(NewMedRec.this).setMessage("是否要删除此病历")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        medRec.delete();
                        Toast.makeText(NewMedRec.this, "删除成功", Toast.LENGTH_SHORT);
                        EventBus.getDefault().post(new NoticeMessage(11));
                        finish();
                        dialog.dismiss();

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lable:
                Intent toLable = new Intent(this, Lable.class);
                toLable.putExtra("lable", medRec);
                startActivityForResult(toLable, LABLE);
                break;
            case R.id.patient_info:
                Intent toPatientInfo = new Intent(this, PatientInfo.class);
                toPatientInfo.putExtra("info", medRec);
                startActivityForResult(toPatientInfo, INFO);
                break;
            case R.id.clinical_time:
                selectTime();
                break;
            case R.id.create_course:
                Constants.POSITION_COURSE = -1;
                Intent toCreateCourse = new Intent(this, CreateCourse.class);
                startActivityForResult(toCreateCourse, COURSE_OF_DISEASE);
                break;
            case R.id.save:
                if (!TextUtils.isEmpty(medRec.getName()) && !diagnosis.getText().toString().trim().equals("") && !diseaseInfo.getText().toString().trim().equals("")) {
                    saveOrUpdate();
                } else {
                    Toast.makeText(NewMedRec.this, "请完善信息", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //保存操作和更操作并返回AllMedRed页面
    private void saveOrUpdate() {
        BeanMedRecUser beanMedRecUser = DataSupport.find(BeanMedRecUser.class, Constants.MED_REC_USER_ID, true);
        //Log.i("lllllll", beanMedRecUser.getDate());
        medRec.setDiagnosis(diagnosis.getText().toString());
        medRec.setDiseaseInfo(diseaseInfo.getText().toString());
        medRec.setClinicalDepartement(clinicalDepartment.getText().toString());
        medRec.setClinicalTime(clinicalTime.getText().toString());
        medRec.setState(true);
        medRec.setBeanMedRecUser(beanMedRecUser);
        medRec.save();
        if (listCourseOfDiseases.size() > 0) {
            medRec = DataSupport.find(BeanMedRec.class, ID, true);
            listCourseOfDiseases = medRec.getListCourseOfDisease();
            medRec.setListCourseOfDisease(listCourseOfDiseases);
        }
        EventBus.getDefault().post(new NoticeMessage(11));
        requestForAddOrUpdate();
        finish();
    }

    /**
     * 请求服务器，添加数据或者更新数据
     * 在执行更新之前先判断if (medRec.getKey()==null),则说明之前有执行过保存操作，
     * 因为没有网的时候也是可以执行添加的，所以会没有key的情况，这时候就要add
     * 只是没有同步到服务器，所以服务器是没有的，直接请求添加病历
     */
    private void requestForAddOrUpdate() {
        if (SAVE_OR_UPDATE.equals("save")) {
            addMedRec();//添加病历
        } else {
            if (medRec.getKey() == null) {
                addMedRec();//添加病历
            } else {
                updateMedRec();//更新病历
            }
        }
    }

    /**
     * ----------------------------------------------------------------------------------------------------------------------
     * 删除网络数据  医生端不能删除网络的，只能删除本地的
     */
    private void networkReqDelMedRec() {
        BeanBaseKeyRemReq baseKeyRemReq = new BeanBaseKeyRemReq();//删除单个
        baseKeyRemReq.setKey(medRec.getKey());
        RetrofitManagerUtils.getInstance(NewMedRec.this, null).getHealthyInfoByRetrofit(OkHttpUtils.getRequestBody(baseKeyRemReq), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                EventBus.getDefault().post(new NoticeMessage(11));
                finish();
            }

            @Override
            public void onError(Throwable e) {
                EventBus.getDefault().post(new NoticeMessage(11));
                finish();
                Toast.makeText(NewMedRec.this, "删除失败，请检查网络环境", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                String str = null;
                try {
                    str = responseBody.string();
                    Log.i("电子病历", "删除操作的响应数据:" + str);
                    if (str != null) {
                        BeanBaseResp beanBaseResp = JSON.parseObject(str, BeanBaseResp.class);
                        if (beanBaseResp.getCode() == 0) {
                            Toast.makeText(NewMedRec.this, "删除成功", Toast.LENGTH_SHORT).show();
                            medRec.delete();
                        } else {
                            Toast.makeText(NewMedRec.this, "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 更新病历
     */
    private void updateMedRec() {
        Log.i("更新", "updateMedRec: ");
        BeanBaseKeySetReq beanBaseKeySetReq = new BeanBaseKeySetReq();
        beanBaseKeySetReq.setKey(medRec.getKey());
        Log.i("更新", "updateMedRec: " + medRec.getKey());
        beanBaseKeySetReq.setValue(JSON.toJSONString(medRec));
        //Log.i("电子病历", "更新的数据" + JSON.toJSONString(medRec));
        RetrofitManagerUtils.getInstance(this, null).getHealthyInfoByRetrofit(OkHttpUtils.getRequestBody(beanBaseKeySetReq), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                sendMdrSaveOrUpdateSuccessInfo();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(NewMedRec.this, "出错啦,数据更新失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                String str = null;
                try {
                    str = responseBody.string();
                    //Log.i("电子病历", "update的响应数据:" + str);
                    if (str != null) {
                        BeanBaseResp beanBaseResp = JSON.parseObject(str, BeanBaseResp.class);
                        if (beanBaseResp.getCode() == 0) {
                            Toast.makeText(NewMedRec.this, "成功更新到服务器", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(NewMedRec.this, "更新失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 添加病历
     */
    private void addMedRec() {
        //String userStr = MySharedPrefUtil.getValue("_user");
        //BeanUserLoginReq beanUserLogin = JSON.parseObject(userStr, BeanUserLoginReq.class);
        final BeanBaseKeyAddReq beanBaseKeyAddReq = new BeanBaseKeyAddReq();
        StringBuilder prefix = new StringBuilder("medRec_");
        prefix.append(Constants.MED_REC_USER_PHONE);//获取当前用户的手机号
        //Log.i("电子病历", "prefix:" + prefix.toString());
        beanBaseKeyAddReq.setPrefix(prefix.toString());//前缀
        beanBaseKeyAddReq.setJsonString(JSON.toJSONString(medRec));//数据string
        RetrofitManagerUtils.getInstance(this, null).getHealthyInfoByRetrofit(OkHttpUtils.getRequestBody(beanBaseKeyAddReq), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                // 发送病历夹新建或更新成功的信息
                sendMdrSaveOrUpdateSuccessInfo();
                Toast.makeText(NewMedRec.this, "成功同步到服务器", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(NewMedRec.this, "出错啦,数据还没有同步到服务器哟", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                String str = null;
                try {
                    str = responseBody.string();
                    //Log.i("电子病历", "add的响应数据:" + str);
                    if (str != null) {
                        BeanBaseKeyAddResp beanBaseKeyAddResp = JSON.parseObject(str, BeanBaseKeyAddResp.class);
                        medRec.setTimestamp(beanBaseKeyAddResp.getTimestamp());
                        medRec.setKey(beanBaseKeyAddResp.getBeanKey());
                        medRec.save();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * ---------------------------------------------------------------------------------------------------------------------
     */

    //时间选择对话框
    private void selectTime() {
        DatePickerDialog datePicker_dialog = new DatePickerDialog(this, new
                DatePickerDialog.MyListener() {
                    @Override
                    public void refreshUI(String string) {
                        clinicalTime.setText(string);
                    }
                });
        datePicker_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        datePicker_dialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case CREATE_COURSE_RESULT_UPDATE_OR_DEL:
                display();
                break;
            case CREATE_COURSE_RESULT_SAVE:
                saveCourseOfDiseaseAndDisplay(data);
                break;
            case INFO:
                BeanMedRec medRecInfo = (BeanMedRec) data.getSerializableExtra("forInfo");
                updataData(medRecInfo);
                setInfo();
                break;
            case LABLE:
                BeanMedRec medRecLables = (BeanMedRec) data.getSerializableExtra("forLable");
                medRec.setLables(medRecLables.getLables());
                setLableTv(lable, medRec);
                break;
        }
    }

    //从病程页面执行更新操作后，返回来更新病程列表的显示
    private void display() {
        BeanMedRec medRecUpdateOrDel = DataSupport.find(BeanMedRec.class, ID, true);
        listCourseOfDiseases.clear();
        listCourseOfDiseases = medRecUpdateOrDel.getListCourseOfDisease();
        medRec.setListCourseOfDisease(listCourseOfDiseases);
        initList(listCourseOfDiseases, courseOfDiseaseRecyclerView);
    }

    //从病程页面编辑完成并点击保存操作后，返回来执行该方法，进行真正的保存并更新病程列表的显示
    private void saveCourseOfDiseaseAndDisplay(Intent data) {
        if (!medRec.isSaved()) {
            //将日期保存下来，防止在AllMedRec做日期排序的时候报错
            medRec.setClinicalTime(clinicalTime.getText().toString());
            medRec.setListCourseOfDisease(new ArrayList<BeanCourseOfDisease>());
            //如果还没有保存过，先保存一次，因为后面的
            // 新建的病程做关联病历夹的操作的时候要依赖已经保存的病历夹对象
            medRec.save();
            //病历夹与用户进行关联
            BeanMedRecUser beanMedRecUser = DataSupport.find(BeanMedRecUser.class, Constants.MED_REC_USER_ID, true);
            beanMedRecUser.getMedRecList().add(medRec);
        }
        ID = medRec.getId();//固定该次病历夹操作的id，方便之后的操作
        //Log.i("lllllli2",String.valueOf(ID));
        //拿到已经保存的medRec（病历夹对象），然后做病程的保存和关联操作，以免保存出错
        BeanMedRec medRecSave = DataSupport.find(BeanMedRec.class, ID, true);
        BeanCourseOfDisease save = (BeanCourseOfDisease) data.getSerializableExtra("saveCourse");
        save.setBeanMedRec(medRecSave);
        save.save();
        //如果有图片则开启服务上传
        if (save.getImgPaths().size() > 0) {
            Intent startUploadImages = new Intent(this, UploadImages.class);
            startUploadImages.putExtra("messageToService", new MessageToServise(medRec.getId(), save.getId(), save.getImgPaths()));
            startService(startUploadImages);
        }

        listCourseOfDiseases.clear();
        //从数据库同步获取最新的病程信息
        listCourseOfDiseases = medRecSave.getListCourseOfDisease();
        medRec.setListCourseOfDisease(listCourseOfDiseases);
        initList(listCourseOfDiseases, courseOfDiseaseRecyclerView);
    }

    //当在患者信息页面编辑并点击保存后，返回来更新medRec里面的值，以免数据丢失
    private void updataData(BeanMedRec medRecInfo) {
        medRec.setName(medRecInfo.getName());
        medRec.setGender(medRecInfo.getGender());
        medRec.setBirthday(medRecInfo.getBirthday());
        medRec.setIDno(medRecInfo.getIDno());
        medRec.setOccupation(medRecInfo.getOccupation());
        medRec.setMarital_status(medRecInfo.getMarital_status());
    }

    //设置lable（标签）控件的显示内容
    private void setLableTv(TextView lable, BeanMedRec beanMedRec) {
        List<String> lables = new ArrayList<>();
        lables = (List<String>) beanMedRec.getLables();
        String lableText = "";
        if (lables.size() != 0) {
            for (int i = 0; i < lables.size(); i++) {
                lableText = lableText + lables.get(i) + "  ";
            }
        }
        lable.setText(lableText);
    }

    //设置name（姓名） 、patientInfo（患者信息点击时间的控件，这里用来显示性别）控件的值
    private void setInfo() {
        if (medRec.getName() != null && !medRec.getName().trim().equals("null") && !medRec.getName().equals("")) {
            name.setText("姓名： " + medRec.getName());
        }
        if (medRec.getGender() != null && !medRec.getGender().trim().equals("null") && !medRec.getGender().equals("")) {
            patientInfo.setText(medRec.getGender());
        }
    }

    // 发送病历夹新建或更新成功的信息
    private void sendMdrSaveOrUpdateSuccessInfo() {
        ImMsgBean bean = new ImMsgBean();
        bean.setName("d" + MyApplication.uid);
        bean.setSender(true);// 是否是发送者
        bean.setTime(DateTimeUtil.getLongMs());// 发送时间
        bean.setContent("[sys]" + medRec.getKey());
        bean.setTopic("u" + Constants.MED_REC_USER_PHONE);

        bean.setType("$");// 类型：文字
        // MQTT发送数据
        MqttUtil.sendTxt(bean);
    }

}

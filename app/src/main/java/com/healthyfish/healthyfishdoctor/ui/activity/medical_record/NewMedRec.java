package com.healthyfish.healthyfishdoctor.ui.activity.medical_record;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.healthyfish.healthyfishdoctor.POJO.BeanCourseOfDisease;
import com.healthyfish.healthyfishdoctor.POJO.BeanMedRec;
import com.healthyfish.healthyfishdoctor.POJO.BeanMedRecUser;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.adapter.CourseOfDiseaseAdapter;
import com.healthyfish.healthyfishdoctor.constant.constants;
import com.healthyfish.healthyfishdoctor.ui.widget.DatePickerDialog;
import com.healthyfish.healthyfishdoctor.utils.Utils1;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.healthyfish.healthyfishdoctor.ui.activity.medical_record.CreateCourse.CREATE_COURSE_RESULT_SAVE;
import static com.healthyfish.healthyfishdoctor.ui.activity.medical_record.CreateCourse.CREATE_COURSE_RESULT_UPDATE_OR_DEL;


/**
 * 描述：电子病历
 * 作者：WKJ on 2017/7/4.
 * 邮箱：
 * 编辑：WKJ
 */
public class NewMedRec extends AppCompatActivity implements View.OnClickListener {
    public static int ID = 0;//记录本次所编辑的病历夹的id
    public static final int ALL_MED_REC_RESULT = 38;//给AllMedRec页面返回结果的标志
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
        //判断是点击item进来的还是点击新建病历夹进来的，并执行相应的初始化操作
        if (constants.POSITION_MED_REC == -1) {
            clinicalTime.setText(Utils1.getTime());
            medRec = new BeanMedRec();
        } else {
            initdata();
        }
        initList(listCourseOfDiseases, courseOfDiseaseRecyclerView);
    }

    private void initdata() {
        ID = getIntent().getIntExtra("id", 0);
        medRec = DataSupport.find(BeanMedRec.class, ID, true);
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
                constants.POSITION_COURSE = position;
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

    //执行删除操作并跳转回到AllMedRed页面
    private void deleteAndGoback() {
        if (constants.POSITION_MED_REC != -1) {
            medRec.delete();
            Intent intent = new Intent(this, AllMedRec.class);
            setResult(ALL_MED_REC_RESULT, intent);
            finish();
        } else {
            Toast.makeText(this, "没有可删除的病程哦", Toast.LENGTH_SHORT).show();
        }
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
                constants.POSITION_COURSE = -1;
                Intent toCreateCourse = new Intent(this, CreateCourse.class);
                startActivityForResult(toCreateCourse, COURSE_OF_DISEASE);
                break;
            case R.id.save:
                saveOrUpdate();
                break;
        }
    }

    //保存操作和更操作并返回AllMedRed页面
    private void saveOrUpdate() {
        BeanMedRecUser beanMedRecUser = DataSupport.find(BeanMedRecUser.class,constants.MED_REC_USER_ID,true);
        Log.i("lllllll",beanMedRecUser.getDate());
        medRec.setDiagnosis(diagnosis.getText().toString());
        medRec.setDiseaseInfo(diseaseInfo.getText().toString());
        medRec.setClinicalDepartement(clinicalDepartment.getText().toString());
        medRec.setClinicalTime(clinicalTime.getText().toString());
        medRec.setBeanMedRecUser(beanMedRecUser);
        medRec.save();

        BeanMedRecUser beanMedRecUser1 = DataSupport.find(BeanMedRecUser.class,constants.MED_REC_USER_ID,true);
        List<BeanMedRec> list = beanMedRecUser1.getMedRecList();
        Log.i("lllllll","读取"+list.get(0).getClinicalTime());
        Intent intent = new Intent(NewMedRec.this, AllMedRec.class);
        NewMedRec.this.setResult(ALL_MED_REC_RESULT, intent);
        NewMedRec.this.finish();
    }

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
            //如果还没有保存过，先保存一次，因为后面的
            // 新建的病程做关联病历夹的操作的时候要依赖已经保存的病历夹对象
            medRec.save();
            //病历夹与用户进行关联
            BeanMedRecUser beanMedRecUser = DataSupport.find(BeanMedRecUser.class,constants.MED_REC_USER_ID,true);
            beanMedRecUser.getMedRecList().add(medRec);
        }
        ID = medRec.getId();//固定该次病历夹操作的id，方便之后的操作
        //Log.i("lllllli2",String.valueOf(ID));
        //拿到已经保存的medRec（病历夹对象），然后做病程的保存和关联操作，以免保存出错
        BeanMedRec medRecSave = DataSupport.find(BeanMedRec.class, ID, true);
        BeanCourseOfDisease save = (BeanCourseOfDisease) data.getSerializableExtra("saveCourse");
        save.setBeanMedRec(medRecSave);
        save.save();
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
        if (medRec.getName() != null) {
            name.setText("姓名： " + medRec.getName());
        }
        if (medRec.getGender() != null) {
            patientInfo.setText("性别： " + medRec.getGender());
        }
    }


}

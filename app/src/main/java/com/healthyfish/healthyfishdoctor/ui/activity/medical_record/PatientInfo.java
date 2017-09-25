package com.healthyfish.healthyfishdoctor.ui.activity.medical_record;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.healthyfish.healthyfishdoctor.POJO.BeanMedRec;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.constant.Constants;
import com.healthyfish.healthyfishdoctor.ui.widget.DatePickerDialog;
import com.healthyfish.healthyfishdoctor.utils.MySharedPrefUtil;
import com.healthyfish.healthyfishdoctor.utils.Utils1;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：患者信息页面
 * 作者：WKJ on 2017/7/4.
 * 邮箱：
 * 编辑：WKJ
 */
public class PatientInfo extends AppCompatActivity implements View.OnClickListener {
    public static final int INFO_RESULT = 34;
    private BeanMedRec medRec;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.gender)
    TextView gender;
    @BindView(R.id.birthday)
    TextView birthday;
    @BindView(R.id.id_number)
    EditText idNumber;
    @BindView(R.id.occupation)
    EditText occupation;
    @BindView(R.id.unmarried)
    RadioButton unmarried;
    @BindView(R.id.married)
    RadioButton married;
    @BindView(R.id.marital_status)
    RadioGroup maritalStatus;
    @BindView(R.id.save)
    TextView save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);
        ButterKnife.bind(this);
        toolbarTitle.setText("患者信息");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back_icon);
        }
        save.setOnClickListener(PatientInfo.this);
        gender.setOnClickListener(this);
        birthday.setOnClickListener(this);
        initData();
    }

    //初始化页面的显示
    private void initData() {
        medRec = (BeanMedRec) getIntent().getSerializableExtra("info");
        if (medRec.getName() != null && !medRec.getName().equals("null") && !medRec.getName().equals("")) {
            name.setText(medRec.getName());
        } else {
            if (Constants.POSITION_MED_REC == -1) {
                String patientName = MySharedPrefUtil.getValue("patientName");
                if (!patientName.equals("")) {
                    name.setText(patientName);
                    medRec.setName(patientName);
                }
            }
        }

        if (medRec.getGender() != null && !medRec.getGender().equals("null") && !medRec.getGender().equals("")) {
            gender.setText(medRec.getGender());
        } else {
            if (Constants.POSITION_MED_REC == -1) {
                String patientGender = MySharedPrefUtil.getValue("patientGender");
                if (!patientGender.equals("")) {
                    gender.setText(patientGender);
                    medRec.setGender(patientGender);
                }
            }
        }

        if (medRec.getBirthday() != null && !medRec.getBirthday().equals("")) {
            birthday.setText(medRec.getBirthday());
        } else {
            if (Constants.POSITION_MED_REC == -1) {
                String patientBirthday = MySharedPrefUtil.getValue("patientBirthday");
                if (!patientBirthday.equals("")) {
                    birthday.setText(patientBirthday);
                    medRec.setBirthday(patientBirthday);
                } else {
                    birthday.setText(Utils1.getTime());
                }
            }
        }

        if (medRec.getIDno() != null && !medRec.getIDno().equals("")) {
            idNumber.setText(medRec.getIDno());
        } else {
            if (Constants.POSITION_MED_REC == -1) {
                String patientIdNumber = MySharedPrefUtil.getValue("patientIdNumber");
                if (!patientIdNumber.equals("")) {
                    idNumber.setText(patientIdNumber);
                    medRec.setIDno(patientIdNumber);
                }
            }
        }

        if (medRec.getOccupation() != null && !medRec.getOccupation().equals("")) {
            occupation.setText(medRec.getOccupation());
        } else {
            if (Constants.POSITION_MED_REC == -1) {
                String patientOccupation = MySharedPrefUtil.getValue("patientOccupation");
                if (!patientOccupation.equals("")) {
                    occupation.setText(patientOccupation);
                    medRec.setOccupation(patientOccupation);
                }
            }
        }

        if (medRec.getMarital_status() != null && !medRec.getMarital_status().equals("")) {
            if (medRec.getMarital_status().equals("未婚")) {
                unmarried.setChecked(true);
            } else {
                married.setChecked(true);
            }
        } else {
            if (Constants.POSITION_MED_REC == -1) {
                String patientMaritalStatus = MySharedPrefUtil.getValue("patientMaritalStatus");
                if (!patientMaritalStatus.equals("")) {
                    if (patientMaritalStatus.equals("未婚")) {
                        unmarried.setChecked(true);
                    } else {
                        married.setChecked(true);
                    }
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    //获取控件的数据
    public void getInfo() {
        medRec.setName(name.getText().toString());
        medRec.setGender(gender.getText().toString());
        medRec.setBirthday(birthday.getText().toString());
        medRec.setIDno(idNumber.getText().toString());
        medRec.setOccupation(occupation.getText().toString());
        medRec.setMarital_status(selectRadioBtn());
    }

    //获取婚姻状况控件的内容
    private String selectRadioBtn() {
        RadioButton radioButton = (RadioButton) findViewById(maritalStatus.getCheckedRadioButtonId());
        String maritalStatusString = radioButton.getText().toString();
        return maritalStatusString;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gender:
                chooseGengerDialog();
                break;
            case R.id.birthday:
                selectBirthday();
                break;
            case R.id.save:
                if (!TextUtils.isEmpty(name.getText().toString().trim()) && !TextUtils.isEmpty(gender.getText().toString())) {
                    getInfo();
                    saveAndGoback();
                } else {
                    Toast.makeText(PatientInfo.this, "请完善信息", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 保存并且返回到上一页面
     */
    private void saveAndGoback() {
        MySharedPrefUtil.saveKeyValue("patientName", medRec.getName());
        MySharedPrefUtil.saveKeyValue("patientGender", medRec.getGender());
        MySharedPrefUtil.saveKeyValue("patientBirthday", medRec.getBirthday());
        MySharedPrefUtil.saveKeyValue("patientIdNumber", medRec.getIDno());
        MySharedPrefUtil.saveKeyValue("patientOccupation", medRec.getOccupation());
        MySharedPrefUtil.saveKeyValue("patientMaritalStatus", medRec.getMarital_status());
        Intent intent = new Intent(PatientInfo.this, NewMedRec.class);
        intent.putExtra("forInfo", medRec);
        setResult(INFO_RESULT, intent);
        finish();
    }

    /**
     * 性别选择对话框
     */
    private void chooseGengerDialog() {
        final String[] strings = new String[]{"男", "女"};
        new AlertDialog.Builder(PatientInfo.this)
                .setTitle("请选择性别")
                .setSingleChoiceItems(strings, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                gender.setText(strings[which]);
                                dialog.dismiss();
                            }
                        }
                )
                .setNegativeButton("取消", null)
                .show();
    }

    /**
     * 出生日期选择对话框
     */
    private void selectBirthday() {
        DatePickerDialog datePicker_dialog = new DatePickerDialog(this, new
                DatePickerDialog.MyListener() {
                    @Override
                    public void refreshUI(String string) {
                        birthday.setText(string);
                    }
                });
        datePicker_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        datePicker_dialog.show();
    }


}

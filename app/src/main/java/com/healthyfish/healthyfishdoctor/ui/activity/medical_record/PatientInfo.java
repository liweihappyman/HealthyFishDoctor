package com.healthyfish.healthyfishdoctor.ui.activity.medical_record;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.healthyfish.healthyfishdoctor.POJO.BeanMedRec;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.ui.widget.DatePickerDialog;
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
        if (medRec.getName() != null&& !medRec.getName().equals("null")) {
            name.setText(medRec.getName());
        }
        if (medRec.getGender() != null && !medRec.getGender().equals("null")) {
            gender.setText(medRec.getGender());
        }
        if (medRec.getBirthday() != null) {
            birthday.setText(medRec.getBirthday());
        }else {
            birthday.setText(Utils1.getTime());
        }
        if (medRec.getIDno() != null) {
            idNumber.setText(medRec.getIDno());
        }
        if (medRec.getOccupation() != null) {
            occupation.setText(medRec.getOccupation());
        }
        if (medRec.getMarital_status() != null) {
            if (medRec.getMarital_status().equals("未婚")) {
                unmarried.setChecked(true);
            } else {
                married.setChecked(true);
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
                getInfo();
                Intent intent = new Intent(PatientInfo.this, NewMedRec.class);
                intent.putExtra("forInfo", medRec);
                setResult(INFO_RESULT, intent);
                finish();
                break;
        }
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
     *     出生日期选择对话框
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

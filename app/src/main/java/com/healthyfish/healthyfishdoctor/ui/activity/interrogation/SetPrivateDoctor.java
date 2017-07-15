package com.healthyfish.healthyfishdoctor.ui.activity.interrogation;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.ui.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：问诊设置私人医生页面
 * 作者：LYQ on 2017/7/13.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class SetPrivateDoctor extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.switch_private_doctor)
    ToggleButton switchPrivateDoctor;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_admission_time)
    TextView tvAdmissionTime;
    @BindView(R.id.tv_upper_limit)
    TextView tvUpperLimit;
    @BindView(R.id.bt_save)
    Button btSave;

    private final int REQUEST_CODE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_private_doctor);
        ButterKnife.bind(this);
        initToolBar(toolbar,toolbarTitle,"设置私人医生");
        initControl();
    }


    @OnClick({R.id.tv_price, R.id.tv_admission_time, R.id.tv_upper_limit, R.id.bt_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_price:
                MyDialogs setEngageTimeDialog = new MyDialogs(this,tvPrice,"PRICE");
                setEngageTimeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                setEngageTimeDialog.setDisplay("设置价格");
                break;
            case R.id.tv_admission_time:
                Intent intent = new Intent(this, SetAdmissionTime.class);
                startActivityForResult(intent,REQUEST_CODE);
                break;
            case R.id.tv_upper_limit:
                MyDialogs setMaxServiceNumDialog = new MyDialogs(this,tvUpperLimit,"UPPER_LIMIT");
                setMaxServiceNumDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                setMaxServiceNumDialog.setDisplay("设置服务人数上限");
                break;
            case R.id.bt_save:
                break;
        }
    }

    /**
     * 初始化控件显示
     */
    private void initControl() {
        //判断是否开启私人医生功能，以及显示设置
//        switchPrivateDoctor.setChecked();
//        tvPrice.setText();
//        tvAdmissionTime.setText();
//        tvUpperLimit.setText();
    }

    /**
     * 设置对话框
     */
    class MyDialogs extends Dialog implements View.OnClickListener {
        private TextView title_tv;//标题
        private EditText set_et;//编辑设置内容
        private Button cancel_tv;//取消
        private Button yes_tv;//确定
        private TextView tv;
        private String flag;

        public MyDialogs(Context context, TextView tv,String flag) {
            super(context);
            this.tv = tv;
            this.flag = flag;
        }

        //要显示这个对话框，只要创建该类对象．然后调用该函数即可．
        public void setDisplay(String str) {
            setContentView(R.layout.layout_set_private_doctor);//设置对话框的布局
            title_tv = (TextView) findViewById(R.id.tv_title);
            title_tv.setText(str);
            set_et = (EditText) findViewById(R.id.et_input);
            cancel_tv = (Button) findViewById(R.id.bt_cancel);
            yes_tv = (Button) findViewById(R.id.bt_sure);
            initListeners();
            show();//显示对话框
        }

        private void initListeners() {
            cancel_tv.setOnClickListener(this);//取消
            yes_tv.setOnClickListener(this);//确定
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_cancel:
                    dismiss();
                    break;
                case R.id.bt_sure:
                    if (!set_et.getText().toString().isEmpty()){
                        if (flag.equals("UPPER_LIMIT")){
                            tv.setText(set_et.getText().toString()+"人");
                        }else if (flag.equals("PRICE")){
                            tv.setText(set_et.getText().toString()+"元/月");
                        }
                    }
                    dismiss();
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE){
            switch (resultCode){
                case SetAdmissionTime.RESULT_OK:
                    tvAdmissionTime.setText("已设置");
                    break;
                default:
                    break;
            }
        }
    }
}

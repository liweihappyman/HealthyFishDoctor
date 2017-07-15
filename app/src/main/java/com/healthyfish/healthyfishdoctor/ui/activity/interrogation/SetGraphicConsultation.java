package com.healthyfish.healthyfishdoctor.ui.activity.interrogation;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.ui.activity.BaseActivity;
import com.healthyfish.healthyfishdoctor.utils.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：问诊设置图文咨询页面
 * 作者：LYQ on 2017/7/13.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class SetGraphicConsultation extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.switch_graphic_consultation)
    ToggleButton switchGraphicConsultation;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.unit)
    TextView unit;
    @BindView(R.id.bt_save)
    Button btSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_graphic_consultation);
        ButterKnife.bind(this);
        initToolBar(toolbar,toolbarTitle,"设置图文咨询");
    }

    @OnClick(R.id.bt_save)
    public void onViewClicked() {
        //点击保存按钮
        if (switchGraphicConsultation.isChecked()){
            if (etPrice.getText().toString().isEmpty()){
                MyToast.showToast(this,"您还没有设置价格噢！");
            }else {
                //保存操作
                etPrice.getText().toString().trim();
            }
        }else {
            //没有选择开通服务的操作

        }
    }
}

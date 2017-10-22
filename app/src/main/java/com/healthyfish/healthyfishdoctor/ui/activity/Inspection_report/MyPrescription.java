package com.healthyfish.healthyfishdoctor.ui.activity.Inspection_report;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.healthyfish.healthyfishdoctor.POJO.BeanInspectionReport;
import com.healthyfish.healthyfishdoctor.POJO.BeanPrescriptiom;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.adapter.PrescriptionRvAdapter;
import com.healthyfish.healthyfishdoctor.constant.Constants;
import com.healthyfish.healthyfishdoctor.eventbus.NoticeMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyPrescription extends AppCompatActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_prescription)
    RecyclerView rvPrescription;
    private PrescriptionRvAdapter adapter;
    private List<BeanPrescriptiom> list = new ArrayList<>();
    private String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_prescription);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initToolBar(toolbar, toolbarTitle, "处方");
        initDataFromDB();
    }
    /**
     * 初始化ToolBar
     */
    public void initToolBar(Toolbar toolbar, TextView tvTitle, String title) {
        toolbar.setTitle("");//设置不显示应用名
        tvTitle.setText(title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back_icon);
        }
    }


    /**
     * 返回按钮的监听
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }


    /**
     * 从数据库加载
     */
    private void initDataFromDB() {
        list.clear();
        key = getIntent().getStringExtra("key");
        if (!key.equals(Constants.FOR_LIST)){
            if ( !DataSupport.where("key = ? ", key).find(BeanPrescriptiom.class).isEmpty()){
                list = DataSupport.where("key = ? ", key).find(BeanPrescriptiom.class);
                LinearLayoutManager lmg = new LinearLayoutManager(this);
                rvPrescription.setLayoutManager(lmg);
                adapter = new PrescriptionRvAdapter(this, list, toolbar);
                rvPrescription.setAdapter(adapter);
            }
        }else if (key.equals(Constants.FOR_LIST)){
            list = DataSupport.findAll(BeanPrescriptiom.class);
            Collections.reverse(list);
            LinearLayoutManager lmg = new LinearLayoutManager(this);
            rvPrescription.setLayoutManager(lmg);
            adapter = new PrescriptionRvAdapter(this, list, toolbar);
            rvPrescription.setAdapter(adapter);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUI(NoticeMessage noticeMessage){
        if (noticeMessage.getMsg() == 51){
            if (key.equals(Constants.FOR_LIST)) {
                initDataFromDB();
            }else {
                list.clear();
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}

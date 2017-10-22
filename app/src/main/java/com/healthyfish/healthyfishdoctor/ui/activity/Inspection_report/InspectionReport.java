package com.healthyfish.healthyfishdoctor.ui.activity.Inspection_report;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.healthyfish.healthyfishdoctor.POJO.BeanInspectionReport;
import com.healthyfish.healthyfishdoctor.POJO.BeanPrescriptiom;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.adapter.InspectionReportAdapter;
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

public class InspectionReport extends AppCompatActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    List<BeanInspectionReport> mList = new ArrayList<>();
    InspectionReportAdapter adapter;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_report);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initToolBar(toolbar, toolbarTitle, "化验单");
        initFromDB();
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
     * 从数据库加载数据
     */
    private void initFromDB() {
        mList.clear();
        key = getIntent().getStringExtra("key");
        if (!key.equals(Constants.FOR_LIST)) {
            if (!DataSupport.where("key = ? ", key).find(BeanInspectionReport.class).isEmpty()) {
                mList = DataSupport.where("key = ? ", key).find(BeanInspectionReport.class);
                LinearLayoutManager lmg = new LinearLayoutManager(this);
                recyclerview.setLayoutManager(lmg);
                adapter = new InspectionReportAdapter(this, mList);
                recyclerview.setAdapter(adapter);
            }
        } else if (key.equals(Constants.FOR_LIST)){
            mList = DataSupport.findAll(BeanInspectionReport.class);
            if (mList.size() > 0) {
                Collections.reverse(mList);//倒序
                LinearLayoutManager lmg = new LinearLayoutManager(this);
                recyclerview.setLayoutManager(lmg);
                adapter = new InspectionReportAdapter(this, mList);
                recyclerview.setAdapter(adapter);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.prescription, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.prescrption:
                Intent intent = new Intent(this, MyPrescription.class);
                intent.putExtra("key", Constants.FOR_LIST);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUI(NoticeMessage noticeMessage){
        if (noticeMessage.getMsg() == 50){
            if (key.equals(Constants.FOR_LIST)) {
                initFromDB();
            }else {
                mList.clear();
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

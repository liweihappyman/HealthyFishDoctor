package com.healthyfish.healthyfishdoctor.ui.activity.pharmacopeia;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.healthyfish.healthyfishdoctor.POJO.BeanDrug;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.adapter.ListOfDrugAdapter;
import com.healthyfish.healthyfishdoctor.ui.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 描述：某类药典列表
 * 作者：WKJ on 2017/7/15.
 * 邮箱：
 * 编辑：WKJ
 */
public class ListOfCertainPharmacopoeia extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_list_of_drug)
    RecyclerView rvListOfDrug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_certain_pharmacopoeia);
        ButterKnife.bind(this);
        initToolBar(toolbar,toolbarTitle,"某类药的列表");
        init();
    }

    private void init() {
        List<BeanDrug> list = new ArrayList<>();
        BeanDrug b1 = new BeanDrug("注射用青蒿琥酯","桂林南药股份有限公司");
        list.add(b1);
        BeanDrug b2 = new BeanDrug("乙胺嘧啶片","昆明制药集团股份有限公司");
        list.add(b2);
        BeanDrug b3 = new BeanDrug("注射用青蒿琥酯","桂林南药股份有限公司");
        list.add(b3);
        BeanDrug b4 = new BeanDrug("注射用青蒿琥酯","桂林南药股份有限公司");
        list.add(b4);
        ListOfDrugAdapter adapter = new ListOfDrugAdapter(list,this);
        LinearLayoutManager lmg = new LinearLayoutManager(this);
        rvListOfDrug.setLayoutManager(lmg);
        rvListOfDrug.setAdapter(adapter);

    }
}

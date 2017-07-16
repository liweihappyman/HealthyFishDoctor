package com.healthyfish.healthyfishdoctor.ui.activity.pharmacopeia;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.ui.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 描述：药物详情
 * 作者：WKJ on 2017/7/15.
 * 邮箱：
 * 编辑：WKJ
 */
public class DrugDetail extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.name_of_drug)
    TextView nameOfDrug;
    @BindView(R.id.indications)
    TextView indications;
    @BindView(R.id.specifications)
    TextView specifications;
    @BindView(R.id.usage_and_dosage)
    TextView usageAndDosage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_detail);
        ButterKnife.bind(this);
        initToolBar(toolbar,toolbarTitle,"具体药名");

    }
}

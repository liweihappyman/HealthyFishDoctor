package com.healthyfish.healthyfishdoctor.ui.activity.medical_record;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.healthyfish.healthyfishdoctor.POJO.BeanMedRecUser;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.adapter.MedRecHomeLvAdapter;
import com.healthyfish.healthyfishdoctor.constant.Constants;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MedRecHome extends AppCompatActivity {
    List<BeanMedRecUser> list = new ArrayList<>();
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.med_rec_home_lv)
    ListView medRecHomeLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_rec_home);
        ButterKnife.bind(this);
        toolbarTitle.setText("病历夹");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back_icon);
        }
        initAll();//初始化列表
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

    private void initAll() {
        list = DataSupport.findAll(BeanMedRecUser.class);
        MedRecHomeLvAdapter adapter = new MedRecHomeLvAdapter(this, list);
        medRecHomeLv.setAdapter(adapter);
        medRecHomeLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Constants.MED_REC_USER_ID = list.get(position).getId();
                Constants.MED_REC_USER_PHONE = list.get(position).getName();
                Intent intent = new Intent(MedRecHome.this, AllMedRec.class);
                startActivity(intent);
            }
        });
    }

}

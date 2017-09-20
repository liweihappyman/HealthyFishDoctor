package com.healthyfish.healthyfishdoctor.ui.activity.medical_record;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.healthyfish.healthyfishdoctor.POJO.BeanMedRecUser;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.adapter.MedRecHomeLvAdapter;
import com.healthyfish.healthyfishdoctor.constant.constants;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MedRecHome extends AppCompatActivity {

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

        initAll();
    }

    private void initAll() {
        final List<BeanMedRecUser> list = new ArrayList<>();
        list.add(new BeanMedRecUser("呵呵哒", "2017年8月11日"));
        list.add(new BeanMedRecUser("呵呵哒", "2017年8月20日"));
        list.add(new BeanMedRecUser("厉害了", "2017年8月12日"));
        list.add(new BeanMedRecUser("呵呵哒", "2017年8月14日"));
        list.add(new BeanMedRecUser("隔壁老王", "2017年8月20日"));
        list.add(new BeanMedRecUser("呵呵哒", "2017年8月20日"));
        list.add(new BeanMedRecUser("厉害了", "2017年8月16日"));
        list.add(new BeanMedRecUser("呵呵哒", "2017年8月20日"));
        list.add(new BeanMedRecUser("隔壁老王", "2017年8月20日"));
        list.add(new BeanMedRecUser("呵呵哒", "2017年8月20日"));
        list.add(new BeanMedRecUser("厉害了", "2017年8月20日"));
        list.add(new BeanMedRecUser("呵呵哒", "2017年8月20日"));
        list.add(new BeanMedRecUser("隔壁老王", "2017年8月20日"));
        list.add(new BeanMedRecUser("呵呵哒", "2017年8月20日"));
        list.add(new BeanMedRecUser("厉害了", "2017年8月20日"));
        list.add(new BeanMedRecUser("呵呵哒", "2017年8月20日"));
        list.add(new BeanMedRecUser("隔壁老王", "2017年8月20日"));
        list.add(new BeanMedRecUser("呵呵哒", "2017年8月20日"));
        if (constants.first) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).save();
                constants.first = false;
            }
        }
        MedRecHomeLvAdapter adapter = new MedRecHomeLvAdapter(this, list);
        medRecHomeLv.setAdapter(adapter);
        medRecHomeLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                constants.MED_REC_USER_ID = list.get(position).getId();
                Intent intent = new Intent(MedRecHome.this, AllMedRec.class);
                startActivity(intent);
            }
        });
    }
//-------------------------------------------这里是测试版的，退出该界面就删除所有病历夹相关的信息
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DataSupport.deleteAll(BeanMedRecUser.class);
    }
//-------------------------------------------这里是测试版的，退出该界面就删除所有病历夹相关的信息
}

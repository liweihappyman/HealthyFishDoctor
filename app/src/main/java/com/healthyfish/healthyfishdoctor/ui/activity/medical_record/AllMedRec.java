package com.healthyfish.healthyfishdoctor.ui.activity.medical_record;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.healthyfish.healthyfishdoctor.POJO.BeanMedRec;
import com.healthyfish.healthyfishdoctor.POJO.BeanMedRecUser;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.adapter.MedRecLvAdapter;
import com.healthyfish.healthyfishdoctor.constant.constants;
import com.healthyfish.healthyfishdoctor.utils.ComparatorDate;
import com.zhy.autolayout.AutoLinearLayout;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.healthyfish.healthyfishdoctor.ui.activity.medical_record.NewMedRec.ALL_MED_REC_RESULT;


/**
 * 描述：电子病历
 * 作者：WKJ on 2017/6/30.
 * 邮箱：
 * 编辑：WKJ
 */


public class AllMedRec extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    public static final int TO_NEW_MED_REC = 38;//进入NewMedRec页面的请求标志
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.med_rec_all)
    ListView medRecAll;
    @BindView(R.id.new_med_rec)
    AutoLinearLayout newMedRec;

    private List<BeanMedRec> listMecRec = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_rec_all);
        ButterKnife.bind(this);
        toolbar.setTitle("");
        //toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.three_points));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back_icon);
        }
        newMedRec.setOnClickListener(this);
        medRecAll.setOnItemClickListener(this);
        init();//初始化布局
    }

    //初始化列表
    private void init() {
        BeanMedRecUser beanMedRecUser = DataSupport.find(BeanMedRecUser.class,constants.MED_REC_USER_ID,true);
        listMecRec = beanMedRecUser.getMedRecList();
        if (listMecRec.size() == 0) {
            initNullLV();
        }
        if (listMecRec.size() > 0) {
            //将日期按时间先后排序
            ComparatorDate c = new ComparatorDate();
            Collections.sort(listMecRec, c);
            //遍历出日期，格式为：       2017年10月
            List<String> listDate = new ArrayList<>();
            for (int i = 0; i < listMecRec.size(); i++) {
                String date = listMecRec.get(i).getClinicalTime();
                date = date.substring(0, date.indexOf("月") + 1);
                listDate.add(date);
            }
            MedRecLvAdapter medRecLvAdapter = new MedRecLvAdapter(this, listMecRec, listDate);
            medRecAll.setAdapter(medRecLvAdapter);
        }
    }

    //初始化空的ListView,提示列表为空（没用到）
    private void initNullLV() {
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setVisibility(View.GONE);
        ((ViewGroup) medRecAll.getParent()).addView(imageView);
        medRecAll.setEmptyView(imageView);
        imageView.setImageResource(R.mipmap.personal_center);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.share:
//                Intent share = new Intent(this, SelectMedRec.class);
//                AllMedRec.this.startActivity(share);
                break;
            case R.id.del:
//                Intent selectDoctor = new Intent(this, SelectDoctor.class);
//                AllMedRec.this.startActivity(selectDoctor);
                break;
            case R.id.test:
                showOptions();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.med_rec, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_med_rec://新建病历
                constants.POSITION_MED_REC = -1;
                Intent intent = new Intent(this, NewMedRec.class);
                startActivityForResult(intent, TO_NEW_MED_REC);
                startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        constants.POSITION_MED_REC = position;
        Intent intent = new Intent(AllMedRec.this, NewMedRec.class);
        //将选中的病历的id穿到NewMedRec活动
        intent.putExtra("id", listMecRec.get(position).getId());
        startActivityForResult(intent, TO_NEW_MED_REC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case ALL_MED_REC_RESULT:
                listMecRec.clear();
                init();
                break;

        }
    }

    private void showOptions() {
        TextView close;
        View rootView;
        rootView = LayoutInflater.from(AllMedRec.this).inflate(R.layout.popupwindow_drug_instructions,
                null);
        final PopupWindow mPopWindow = new PopupWindow(rootView);
        mPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopWindow.setTouchable(true);
        mPopWindow.setFocusable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setAnimationStyle(R.style.PopupRightAnimation);
        mPopWindow.showAsDropDown(toolbar, 380, 0);
        close = (TextView) rootView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });
    }


}

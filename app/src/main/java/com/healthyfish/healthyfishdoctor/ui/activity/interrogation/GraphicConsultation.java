package com.healthyfish.healthyfishdoctor.ui.activity.interrogation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.healthyfish.healthyfishdoctor.POJO.BeanGraphicConsultation;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.adapter.GraphicConsultationAdapter;
import com.healthyfish.healthyfishdoctor.ui.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：图文咨询消息列表
 * 作者：LYQ on 2017/7/13.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class GraphicConsultation extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_go_set)
    TextView tvGoSet;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv_graphic_consultation)
    ListView lvGraphicConsultation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic_consultation);
        ButterKnife.bind(this);
        initToolBar(toolbar,toolbarTitle,"图文咨询");
        initListView();
    }

    /**
     * 初始化消息列表
     */
    private void initListView() {
        List<BeanGraphicConsultation> list = new ArrayList<>();

        for (int i=0; i<10; i++){
            BeanGraphicConsultation bean1 = new BeanGraphicConsultation();
            bean1.setImgUrl("http://wmtp.net/wp-content/uploads/2017/02/0227_weimei01_1.jpeg");
            bean1.setName("赵思思"+i);
            bean1.setTime("2017年7月13日");
            bean1.setContent("这是一条很长但很有趣的消息呦！");
            list.add(bean1);
        }

        GraphicConsultationAdapter adapter = new GraphicConsultationAdapter(this, list);
        lvGraphicConsultation.setAdapter(adapter);
        lvGraphicConsultation.setVerticalScrollBarEnabled(false);
        lvGraphicConsultation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GraphicConsultation.this, SeeUserInformation.class);
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.tv_go_set)
    public void onViewClicked() {
        Intent intent = new Intent(this, SetGraphicConsultation.class);
        startActivity(intent);
    }
}

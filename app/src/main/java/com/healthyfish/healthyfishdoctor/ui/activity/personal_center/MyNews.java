package com.healthyfish.healthyfishdoctor.ui.activity.personal_center;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.healthyfish.healthyfishdoctor.POJO.BeanMyNewsItem;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.adapter.MyNewsAdapter;
import com.healthyfish.healthyfishdoctor.ui.activity.BaseActivity;
import com.healthyfish.healthyfishdoctor.utils.MyToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：个人中心我的消息页面
 * 作者：LYQ on 2017/7/7.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class MyNews extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv_my_news)
    ListView lvMyNews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_news);
        ButterKnife.bind(this);
        initToolBar(toolbar,toolbarTitle,"我的消息");
        initListView();
    }

    /**
     * 初始化用户评价ListView
     */
    public void initListView() {
        //评价列表适配器
        MyNewsAdapter adapter = new MyNewsAdapter(this,getLisViewData());
        lvMyNews.setAdapter(adapter);
        lvMyNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyToast.showToast(MyNews.this,getLisViewData().get(position).getNewsContent());
            }
        });
    }

    /**
     * 模拟消息数据
     */
    private List<BeanMyNewsItem> getLisViewData() {
        List<BeanMyNewsItem> list = new ArrayList<>();
        BeanMyNewsItem myNewsItem = new BeanMyNewsItem("问诊消息","这是一条问诊消息","2017年7月8日");
        list.add(myNewsItem);
        return list;
    }

}

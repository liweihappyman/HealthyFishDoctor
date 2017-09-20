package com.healthyfish.healthyfishdoctor.ui.activity.interrogation;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.healthyfish.healthyfishdoctor.POJO.BeanSelectTemplate;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.adapter.SelectTemplateLvAdapter;
import com.healthyfish.healthyfishdoctor.ui.activity.BaseActivity;
import com.healthyfish.healthyfishdoctor.utils.MyToast;
import com.healthyfish.healthyfishdoctor.utils.ScrollViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：问诊选择模板页面
 * 作者：LYQ on 2017/7/15.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class SelectTemplate extends BaseActivity {

    @BindView(R.id.lv_select_template)
    ListView lvSelectTemplate;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_update)
    TextView toolbarUpdate;
    @BindView(R.id.bt_add_new_template)
    Button btAddNewTemplate;


    private SelectTemplateLvAdapter adapter;
    private final List<BeanSelectTemplate> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_template);
        ButterKnife.bind(this);
        initToolBar(toolbar, toolbarTitle, "选择模板");
        toolbarUpdate.setText("更新");
        initListView();
    }

    private void initListView() {

        for (int i = 0; i < 20; i++) {
            BeanSelectTemplate bean = new BeanSelectTemplate();
            bean.setTitle("模板" + i);
            bean.setContent("这是模板" + i);
            list.add(bean);
        }
        adapter = new SelectTemplateLvAdapter(this, list);
        lvSelectTemplate.setChoiceMode(ListView.CHOICE_MODE_SINGLE);//设置单选功能
        lvSelectTemplate.setAdapter(adapter);
        ScrollViewUtils.setListViewHeightBasedOnChildren(lvSelectTemplate);
    }

    @OnClick({R.id.toolbar_update, R.id.bt_add_new_template})
    public void onViewClicked(View view) {
         switch (view.getId()) {
            case R.id.toolbar_update:
                //更新按钮
                MyToast.showToast(SelectTemplate.this, "您选择了第" + adapter.getSelectPosition() + "个模板，" + "模板名称是：" + list.get(adapter.getSelectPosition()).getTitle());
                break;
            case R.id.bt_add_new_template:
              //添加新的模板
                break;


        }
    }

}

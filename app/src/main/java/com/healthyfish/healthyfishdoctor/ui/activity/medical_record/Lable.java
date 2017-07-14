package com.healthyfish.healthyfishdoctor.ui.activity.medical_record;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.healthyfish.healthyfishdoctor.POJO.BeanMedRec;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.adapter.TagAdapter;
import com.healthyfish.healthyfishdoctor.ui.widget.FlowLayout;
import com.healthyfish.healthyfishdoctor.ui.widget.TagFlowLayout;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：标签页
 * 作者：WKJ on 2017/7/4.
 * 邮箱：
 * 编辑：WKJ
 */
public class Lable extends AppCompatActivity implements View.OnClickListener {
    public static final int FOR_LABLE = 35;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.id_flowlayout)
    FlowLayout idFlowlayout;//上面的flowLayout
    @BindView(R.id.id_flowlayout_two)
    TagFlowLayout idFlowlayoutTwo;//所有标签的TagFlowLayout
    @BindView(R.id.save)
    TextView save;
    private List<String> label_list = new ArrayList<>();//上面的标签列表
    private List<String> all_label_List = new ArrayList<>();//所有标签列表
    final List<TextView> labels = new ArrayList<>();//存放标签
    final List<Boolean> labelStates = new ArrayList<>();//存放标签状态
    final Set<Integer> set = new HashSet<>();//存放选中的
    private TagAdapter<String> tagAdapter;//标签适配器
    private LinearLayout.LayoutParams params;
    private EditText editText;//新添加用的
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private List<String> listLable;//将要保存的textview标签转换为Listview<String>
    private BeanMedRec medRec = new BeanMedRec();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lable);
        ButterKnife.bind(this);
        toolbar.setTitle("");
        toolbarTitle.setText("添加标签");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back_icon);
        }
        //偏好设置，保存用户的一些轻量数据，这里保存的是所有的标签
        // 前面的标记符任意，模式为私有模式，即数据只有自己能用
        sharedPreferences = getSharedPreferences("lablews", MODE_PRIVATE);
        editor = sharedPreferences.edit();//用来向sharePrefrence写入数据
        //顺序不能乱
        initView();//初始化View,上下两个大布局
        initData();//用来加载本地的标签和数据bean里面的标签
        initEdittext();//初始化默认textview
        initAllLeblLayout();//初始化所有标签布局
        save.setOnClickListener(this);
    }

    //初始化数据，初始化上面的标签
    private void initData() {
        initAllLable();//下面标签的初始化
        initlable();//上面标签的初始化
    }

    //初始化所有标签，从sharedPreferences读取出之前保存的标签
    private void initAllLable() {
        String allLableJson = sharedPreferences.getString("lable", null);
        if (allLableJson != null) {
            all_label_List = (List<String>) JSON.parse(allLableJson);
        }
    }

    //上面标签的初始化
    private void initlable() {
        medRec = (BeanMedRec) getIntent().getSerializableExtra("lable");
        if (medRec.getLables() != null) {
            label_list = medRec.getLables();
            //拿到当前的Bean的标签，如果不为空，则执行
            for (int i = 0; i < label_list.size(); i++) {
                editText = new EditText(getApplicationContext());//new 一个EditText
                editText.setText(label_list.get(i));
                editText.setMinEms(5);
                editText.setTextSize(16);
                addLabel(editText);//添加标签
            }
        }
    }

    //对比上下的标签,如果在上面的标签与所有标签有不同,则将该标签添加到所有标签列表里
    private void getAllLable() {
        List<TextView> lab = labels;
        //保存到数据库的标签
        listLable = new ArrayList<String>();
        for (int i = 0; i < lab.size(); i++) {
            String str = labels.get(i).getText().toString();
            listLable.add(str);
        }
        //逐个对比，如果上面的标签和所有标签列表的有相同的，则移除
        for (int j = 0; j < all_label_List.size(); j++) {
            for (int i = 0; i < lab.size(); i++) {
                if (lab.get(i).getText().toString().equals(all_label_List.get(j))) {
                    lab.remove(i);
                    break;
                }
            }
        }
        //将移除后剩下的不同的添加到所有标签标签列表里
        for (int k = 0; k < lab.size(); k++) {
            all_label_List.add(lab.get(k).getText().toString());
        }
        //用sharePrefrence保存所有标签()
        String all_label_ListJson = JSON.toJSONString(all_label_List);
        editor.clear();
        editor.putString("lable", all_label_ListJson);
        editor.commit();//提交编辑结果
    }

    //初始化View，两个流水标签布局
    private void initView() {
        idFlowlayout = (FlowLayout) findViewById(R.id.id_flowlayout);
        idFlowlayoutTwo = (TagFlowLayout) findViewById(R.id.id_flowlayout_two);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
                .LayoutParams.WRAP_CONTENT);
        params.setMargins(20, 20, 20, 20);
        idFlowlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editTextContent = editText.getText().toString();
                if (TextUtils.isEmpty(editTextContent)) {
                    tagNormal();
                } else {
                    addLabel(editText);
                }
            }
        });
    }

    //初始化默认的添加标签
    private void initEdittext() {
        editText = new EditText(getApplicationContext());
        editText.setHint("添加标签");
        //设置固定宽度
        editText.setMinEms(5);
        editText.setTextSize(16);
        //设置shape
        editText.setBackgroundResource(R.drawable.label_add);
        editText.setHintTextColor(Color.parseColor("#b4b4b4"));
        editText.setTextColor(Color.parseColor("#000000"));
        editText.setLayoutParams(params);
        //添加到layout中
        idFlowlayout.addView(editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tagNormal();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //初始化所有标签列表
    private void initAllLeblLayout() {
        //初始化适配器
        tagAdapter = new TagAdapter<String>(all_label_List) {


            @Override
            public View getView(FlowLayout parent, int position, Object s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.flag_adapter,
                        idFlowlayoutTwo, false);
                tv.setText((String) s);
                return tv;
            }
        };
        idFlowlayoutTwo.setAdapter(tagAdapter);
        //根据上面的标签来判断是否有与下面的标签有相同的，将相同的设置为选中状态
        for (int i = 0; i < label_list.size(); i++) {
            for (int j = 0; j < all_label_List.size(); j++) {
                if (label_list.get(i).equals(
                        all_label_List.get(j))) {
                    tagAdapter.setSelectedList(j);//设为选中
                }
            }
        }
        tagAdapter.notifyDataChanged();
        //给下面的标签添加监听
        idFlowlayoutTwo.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (labels.size() == 0) {
                    editText.setText(all_label_List.get(position));
                    addLabel(editText);
                    label_list.add(editText.getText().toString());
                    return false;
                }
                List<String> list = new ArrayList<>();
                for (int i = 0; i < labels.size(); i++) {
                    list.add(labels.get(i).getText().toString());
                }
                //如果上面包含点击的标签就删除
                if (list.contains(all_label_List.get(position))) {
                    for (int i = 0; i < list.size(); i++) {
                        if (all_label_List.get(position).equals(list.get(i))) {
                            idFlowlayout.removeView(labels.get(i));
                            labels.remove(i);
                        }
                    }
                } else {
                    editText.setText(all_label_List.get(position));
                    addLabel(editText);
                }
                return false;
            }
        });
        //已经选中的监听
        idFlowlayoutTwo.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                set.clear();
                set.addAll(selectPosSet);
            }
        });
    }

    //添加标签
    private boolean addLabel(EditText editText) {
        String editTextContent = editText.getText().toString();
        //判断输入是否为空
        if (editTextContent.equals(""))
            return true;
        //判断是否重复
        for (TextView tag : labels) {
            String tempStr = tag.getText().toString();
            if (tempStr.equals(editTextContent)) {
                editText.setText("");
                editText.requestFocus();
                return true;
            }
        }
        //添加标签
        final TextView temp = getTag(editText.getText().toString());
        labels.add(temp);
        labelStates.add(false);
        //添加点击事件，点击变成选中状态，选中状态下被点击则删除
        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int curIndex = labels.indexOf(temp);
                if (!labelStates.get(curIndex)) {
                    //显示 ×号删除
                    temp.setText(temp.getText() + " ×");
                    temp.setBackgroundResource(R.drawable.label_del);
                    temp.setTextColor(Color.parseColor("#ffffff"));
                    //修改选中状态
                    labelStates.set(curIndex, true);
                } else {
                    delByTest(temp.getText().toString());
                    idFlowlayout.removeView(temp);
                    labels.remove(curIndex);
                    labelStates.remove(curIndex);
                    for (int i = 0; i < label_list.size(); i++) {
                        for (int j = 0; j < labels.size(); j++) {
                            if (label_list.get(i).equals(
                                    labels.get(j).getText())) {
                                tagAdapter.setSelectedList(i);
                            }
                        }
                    }
                    tagAdapter.notifyDataChanged();
                }
            }
        });
        idFlowlayout.addView(temp);
        //让输入框在最后一个位置上
        editText.bringToFront();
        //清空编辑框
        editText.setText("");
        editText.requestFocus();
        return true;
    }

    //根据字符删除标签
    private void delByTest(String text) {

        for (int i = 0; i < all_label_List.size(); i++) {
            String a = all_label_List.get(i) + " ×";
            if (a.equals(text)) {
                set.remove(i);
            }
        }
        tagAdapter.setSelectedList(set);//重置选中的标签
    }

    //标签恢复到正常状态
    private void tagNormal() {
        //输入文字时取消已经选中的标签
        for (int i = 0; i < labelStates.size(); i++) {
            if (labelStates.get(i)) {
                TextView tmp = labels.get(i);
                tmp.setText(tmp.getText().toString().replace(" ×", ""));
                labelStates.set(i, false);
                tmp.setBackgroundResource(R.drawable.label_normal);
                tmp.setTextColor(Color.parseColor("#54bcf1"));
            }
        }
    }

    //创建一个正常状态的标签
    private TextView getTag(String label) {
        TextView textView = new TextView(getApplicationContext());
        textView.setTextSize(16);
        textView.setBackgroundResource(R.drawable.label_normal);
        textView.setTextColor(Color.parseColor("#54bcf1"));
        textView.setText(label);
        textView.setLayoutParams(params);
        return textView;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                getAllLable();
                medRec.setLables(listLable);
                Intent intent = new Intent(Lable.this, NewMedRec.class);
                intent.putExtra("forLable", medRec);
                setResult(FOR_LABLE, intent);
                finish();
                break;
        }
    }
}

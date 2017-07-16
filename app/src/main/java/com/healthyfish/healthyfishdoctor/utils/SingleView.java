package com.healthyfish.healthyfishdoctor.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;

/**
 * 描述：具有单选功能的单选框视图布局
 * 作者：LYQ on 2017/7/6.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class SingleView extends LinearLayout implements Checkable {

    private Context mContext;
    private CheckBox mCkbSelect;

    public SingleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    public SingleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public SingleView(Context context) {
        super(context);
        mContext = context;
    }

    public View initView(int layoutID) {
        // 填充布局
        View view =  LayoutInflater.from(mContext).inflate(layoutID, this, true);
        return view;
    }

    public void initCheckBox(CheckBox checkBox){
        mCkbSelect = checkBox;
    }

    @Override
    public void setChecked(boolean checked) {
        mCkbSelect.setChecked(checked);
    }

    @Override
    public boolean isChecked() {
        return mCkbSelect.isChecked();
    }

    @Override
    public void toggle() {
        mCkbSelect.toggle();
    }

}

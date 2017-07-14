package com.healthyfish.healthyfishdoctor.adapter;

import android.view.View;


import com.healthyfish.healthyfishdoctor.ui.widget.FlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 描述：新建病历夹的标签适配器
 * 作者：WKJ on 2017/7/4.
 * 邮箱：
 * 编辑：WKJ
 */
public abstract class TagAdapter<T>
{
    private List<T> mTagDatas;
    private OnDataChangedListener mOnDataChangedListener;
    private HashSet<Integer> mCheckedPosList = new HashSet<Integer>();

    public TagAdapter(List<T> datas)
    {
        mTagDatas = datas;
    }

    public TagAdapter(T[] datas)
    {
        mTagDatas = new ArrayList<T>(Arrays.asList(datas));
    }

    public static interface OnDataChangedListener
    {
        void onChanged();
    }

    public void setOnDataChangedListener(OnDataChangedListener listener)
    {
        mOnDataChangedListener = listener;
    }

    public void setSelectedList(int... pos)
    {
        for (int i = 0; i < pos.length; i++)
            mCheckedPosList.add(pos[i]);
        notifyDataChanged();
    }

    public void setSelectedList(Set<Integer> set)
    {

        mCheckedPosList.clear();
        if (set != null)
            mCheckedPosList.addAll(set);
        notifyDataChanged();
    }

    public HashSet<Integer> getPreCheckedList()
    {
        return mCheckedPosList;
    }


    public int getCount()
    {
        return mTagDatas == null ? 0 : mTagDatas.size();
    }

    public void notifyDataChanged()
    {
        mOnDataChangedListener.onChanged();
    }

    public T getItem(int position)
    {
        return mTagDatas.get(position);
    }

    public abstract View getView(FlowLayout parent, int position, Object t);

    public boolean setSelected(int position, T t)
    {
        return false;
    }


}

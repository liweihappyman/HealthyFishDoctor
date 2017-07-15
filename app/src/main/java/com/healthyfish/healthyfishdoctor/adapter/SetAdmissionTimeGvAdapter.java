package com.healthyfish.healthyfishdoctor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.healthyfish.healthyfishdoctor.POJO.BeanAdmissionTimeItem;
import com.healthyfish.healthyfishdoctor.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：设置接诊时间页面GridView适配器
 * 作者：LYQ on 2017/7/13.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class SetAdmissionTimeGvAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<BeanAdmissionTimeItem> mList = new ArrayList<>();

    public SetAdmissionTimeGvAdapter(Context mContext, List<BeanAdmissionTimeItem> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public List<BeanAdmissionTimeItem> getList(){
        return mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_set_admission_time, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        //后期需要判断是否为空
        holder.cbTime.setText(mList.get(position).getTime());
        holder.cbTime.setChecked(mList.get(position).isCheck());
        holder.cbTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mList.get(position).setCheck(isChecked);//保存选中状态
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.cb_time)
        CheckBox cbTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

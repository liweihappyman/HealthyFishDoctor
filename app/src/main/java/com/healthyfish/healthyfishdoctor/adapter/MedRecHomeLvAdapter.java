package com.healthyfish.healthyfishdoctor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.healthyfish.healthyfishdoctor.POJO.BeanMedRecUser;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.utils.DateTimeUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 描述：
 * 作者：LYQ on 2017/7/2.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class MedRecHomeLvAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<BeanMedRecUser> listUser;

    public MedRecHomeLvAdapter(Context mContext, List<BeanMedRecUser> listUser) {
        this.mContext = mContext;
        this.listUser = listUser;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return listUser.size();
    }

    @Override
    public Object getItem(int position) {
        return listUser.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_med_rec_home, parent,false);
            holder = new ViewHolder();
            holder.img = (CircleImageView) convertView.findViewById(R.id.img);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            convertView.setTag(holder);
            //对于listView，注意添加这一行，即可在item上使用高度
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext).load(listUser.get(position).getImgUrl()).error(R.mipmap.error).centerCrop().into(holder.img);
        holder.name.setText(listUser.get(position).getName()+"的病历夹");
        holder.date.setText(DateTimeUtil.getTime(Long.valueOf(listUser.get(position).getDate())));
        return convertView;
    }

    static class ViewHolder {
        private CircleImageView img;
        private TextView name;
        private TextView date;
    }
}

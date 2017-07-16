package com.healthyfish.healthyfishdoctor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthyfish.healthyfishdoctor.POJO.BeanPayService;
import com.healthyfish.healthyfishdoctor.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：问诊首页GridView适配器
 * 作者：LYQ on 2017/7/13.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class PayServiceGvAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<BeanPayService> mList = new ArrayList<>();

    public PayServiceGvAdapter(Context mContext, List<BeanPayService> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mLayoutInflater = LayoutInflater.from(mContext);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_pay_service_gv, parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);//item自适应适配
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.ivServiceImg.setImageResource(mList.get(position).getImg());
        holder.serviceTitle.setText(mList.get(position).getTitle());
        if (mList.get(position).isOpen()){
            holder.serviceTitle.setTextColor(mContext.getResources().getColor(R.color.color_primary));
            holder.ivOpenOrClose.setVisibility(View.GONE);
        }else {
            holder.serviceTitle.setTextColor(mContext.getResources().getColor(R.color.color_hint));
            holder.ivOpenOrClose.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_service_img)
        ImageView ivServiceImg;
        @BindView(R.id.service_title)
        TextView serviceTitle;
        @BindView(R.id.iv_open_or_close)
        ImageView ivOpenOrClose;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

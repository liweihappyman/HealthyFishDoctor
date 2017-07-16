package com.healthyfish.healthyfishdoctor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.healthyfish.healthyfishdoctor.POJO.BeanMyNewsItem;
import com.healthyfish.healthyfishdoctor.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：个人中心我的消息页面ListView的适配器
 * 作者：LYQ on 2017/7/8.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class MyNewsAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<BeanMyNewsItem> mList = new ArrayList<>();

    public MyNewsAdapter(Context mContext, List<BeanMyNewsItem> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
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
            convertView = mLayoutInflater.inflate(R.layout.item_my_news_listview,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);//item自适应适配
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvNewsType.setText(mList.get(position).getNewsType());
        holder.tvNewsContent.setText(mList.get(position).getNewsContent());
        holder.tvNewsTime.setText(mList.get(position).getNewsTime());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_news_type)
        TextView tvNewsType;
        @BindView(R.id.tv_news_content)
        TextView tvNewsContent;
        @BindView(R.id.tv_news_time)
        TextView tvNewsTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

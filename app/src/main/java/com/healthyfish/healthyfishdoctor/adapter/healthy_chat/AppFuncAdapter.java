package com.healthyfish.healthyfishdoctor.adapter.healthy_chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.healthyfish.healthyfishdoctor.POJO.AppFuncBean;
import com.healthyfish.healthyfishdoctor.R;

import java.util.ArrayList;


/**
 * 更多功能按钮--适配器.
 */
public class AppFuncAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context mContext;
    private ArrayList<AppFuncBean> mDdata = new ArrayList<AppFuncBean>();

    public AppFuncAdapter(Context context, ArrayList<AppFuncBean> data) {
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        if (data != null) {
            this.mDdata = data;
        }
    }

    @Override
    public int getCount() {
        return mDdata.size();
    }

    @Override
    public Object getItem(int position) {
        return mDdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_app, null); // 单个功能布局
            viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon); // 功能图标
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name); // 功能名字
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 实现更多功能的事件监听
        final AppFuncBean appFuncBean = mDdata.get(position);
        if (appFuncBean != null) {
            viewHolder.iv_icon.setBackgroundResource(appFuncBean.getIcon());
            viewHolder.tv_name.setText(appFuncBean.getFuncName());
/*            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, appFuncBean.getFuncName(), Toast.LENGTH_SHORT).show();
                }
            });*/
        }
        return convertView;
    }

    class ViewHolder {
        public ImageView iv_icon;
        public TextView tv_name;
    }
}
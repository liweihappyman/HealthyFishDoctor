package com.healthyfish.healthyfishdoctor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.healthyfish.healthyfishdoctor.POJO.BeanGraphicConsultation;
import com.healthyfish.healthyfishdoctor.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 描述：图文咨询消息列表适配器
 * 作者：LYQ on 2017/7/13.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class GraphicConsultationAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<BeanGraphicConsultation> mList = new ArrayList<>();

    public GraphicConsultationAdapter(Context mContext, List<BeanGraphicConsultation> mList) {
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
            convertView = mLayoutInflater.inflate(R.layout.item_graphic_consultation_lv, parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);//item自适应适配
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext).load(mList.get(position).getImgUrl()).into(holder.civHeadPortrait);
        holder.tvName.setText(mList.get(position).getName());
        holder.tvTime.setText(mList.get(position).getTime());
        holder.tvMessageContent.setText(mList.get(position).getContent());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.civ_head_portrait)
        CircleImageView civHeadPortrait;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_message_content)
        TextView tvMessageContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

package com.healthyfish.healthyfishdoctor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthyfish.healthyfishdoctor.POJO.BeanAllMessage;
import com.healthyfish.healthyfishdoctor.POJO.BeanMedRecUser;
import com.healthyfish.healthyfishdoctor.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 描述：
 * 作者：LYQ on 2017/7/2.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class HomeLvAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<BeanAllMessage> listMessages;

    public HomeLvAdapter(Context mContext, List<BeanAllMessage> listMessages) {
        this.mContext = mContext;
        this.listMessages = listMessages;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return listMessages.size();
    }

    @Override
    public Object getItem(int position) {
        return listMessages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_home_lv, parent,false);
            holder = new ViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.type = (TextView) convertView.findViewById(R.id.type);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.description = (TextView) convertView.findViewById(R.id.description);
            convertView.setTag(holder);
            //对于listView，注意添加这一行，即可在item上使用高度
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String type = listMessages.get(position).getType();
        setImg(holder, type);//根据type设置img
        holder.type.setText(type);
        holder.date.setText(listMessages.get(position).getDate());
        holder.description.setText(listMessages.get(position).getDescription());
        return convertView;
    }
    //根据type设置img
    private void setImg(ViewHolder holder, String type) {
        switch (type){
            case "系统消息":
                holder.img.setImageResource(R.mipmap.system_message);
                break;
            case "图文消息":
                holder.img.setImageResource(R.mipmap.picture_consulting);
                break;
            case "私人医生":
                holder.img.setImageResource(R.mipmap.prvivate_doctor);
                break;
            case "病历夹":
                holder.img.setImageResource(R.mipmap.med_rec);
                break;
            case "会诊消息":
                holder.img.setImageResource(R.mipmap.consultation_message);
                break;
            default:
                break;
        }
    }

    static class ViewHolder {
        private ImageView img;
        private TextView type;
        private TextView date;
        private TextView description;
    }
}

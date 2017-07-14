package com.healthyfish.healthyfishdoctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.healthyfish.healthyfishdoctor.POJO.BeanTrainingVideo;
import com.healthyfish.healthyfishdoctor.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * 描述：
 * 作者：Wayne on 2017/7/13 10:36
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.ViewHolder> {

    Context mContext;
    private List<BeanTrainingVideo> listTrainingVideo;

    public TrainingAdapter(Context mContext, List<BeanTrainingVideo> listTrainingThumbNail) {
        this.mContext = mContext;
        this.listTrainingVideo = listTrainingThumbNail;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivThumbNailTraining;
        TextView tvNameTraining;
        TextView tvNumViewer;
        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.auto(itemView);
            ivThumbNailTraining = (ImageView) itemView.findViewById(R.id.iv_training_thumb_nail);
            tvNameTraining = (TextView) itemView.findViewById(R.id.tv_name_traininng);
            tvNumViewer = (TextView) itemView.findViewById(R.id.tv_num_viewer);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_training_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BeanTrainingVideo bean = listTrainingVideo.get(position);
        holder.tvNameTraining.setText(bean.getNameTraining());
        holder.tvNumViewer.setText(""+bean.getNumViewer());
        Glide.with(mContext)
                .load(R.mipmap.training_thumb_nail_video)
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.error)
                .centerCrop()
                .dontAnimate()
                .into(holder.ivThumbNailTraining);


    }

    @Override
    public int getItemCount() {
        return listTrainingVideo.size();
    }

}

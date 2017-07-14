package com.healthyfish.healthyfishdoctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;


import com.healthyfish.healthyfishdoctor.POJO.BeanCourseOfDisease;
import com.healthyfish.healthyfishdoctor.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * 描述：病程列表的适配器
 * 作者：WKJ on 2017/7/5.
 * 邮箱：
 * 编辑：WKJ
 */

public class CourseOfDiseaseAdapter extends RecyclerView.Adapter<CourseOfDiseaseAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<BeanCourseOfDisease> listCourseOfDisease;//病程列表

    private OnItemClickListener mOnItemClickListener = null;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public CourseOfDiseaseAdapter(Context mContext, List<BeanCourseOfDisease> listCourseOfDisease) {
        this.mContext = mContext;
        this.listCourseOfDisease = listCourseOfDisease;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_disease_course,parent,false);

        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BeanCourseOfDisease courseOfDisease  = listCourseOfDisease.get(position);
        holder.type.setText(courseOfDisease.getType());
        holder.date.setText(courseOfDisease.getDate());
        if (courseOfDisease.getImgPaths()==null){
            holder.imgGridView.setVisibility(View.GONE);
        }else {
            holder.imgGridView.setVisibility(View.VISIBLE);
            CourseGridAdapter courseGridAdapter = new CourseGridAdapter(courseOfDisease.getImgPaths(),mContext);
           // Log.i("url",courseOfDisease.getImgPaths().get(0));
            holder.imgGridView.setAdapter(courseGridAdapter);
        }

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return listCourseOfDisease.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView type;
        private TextView date;
        private GridView imgGridView;
        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            type = (TextView) itemView.findViewById(R.id.type);
            date = (TextView) itemView.findViewById(R.id.date);
            imgGridView = (GridView) itemView.findViewById(R.id.image_gridview);
        }
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}

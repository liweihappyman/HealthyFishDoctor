package com.healthyfish.healthyfishdoctor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.healthyfish.healthyfishdoctor.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.io.File;
import java.util.List;

/**
 * 描述：创建病程页面的图片显示适配器
 * 作者：WKJ on 2017/7/5.
 * 邮箱：
 * 编辑：WKJ
 */

public class CreateCourseGridAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> listPaths;

    public CreateCourseGridAdapter(List<String> listPaths, Context mContext) {
        this.listPaths = listPaths;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return listPaths.size() + 1;
    }//修改

    @Override
    public String getItem(int position) {
        return listPaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;
//        if (convertView == null) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_image, null);
        imageView = (ImageView) convertView.findViewById(R.id.image);
//            convertView.setTag(imageView);
        AutoUtils.auto(convertView);
//        } else {
//            imageView = (ImageView) convertView.getTag();
//        }
        if (listPaths.size() <= 8) {
            if (position < listPaths.size()) {

                Glide.with(mContext)
                        .load(new File(getItem(position)))
                        .placeholder(R.mipmap.default_error)
                        .error(R.mipmap.default_error)
                        .centerCrop()
                        .crossFade()
                        .into(imageView);
            }
            if (position == listPaths.size()) {
                imageView.setImageResource(R.mipmap.addpic);
            }
            if (position == 8) {
                imageView.setVisibility(View.GONE);
            }
        }
        return convertView;
    }
}

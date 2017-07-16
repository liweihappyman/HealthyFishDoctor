package com.healthyfish.healthyfishdoctor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthyfish.healthyfishdoctor.POJO.BeanPharmacopeia;
import com.healthyfish.healthyfishdoctor.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 描述：
 * 作者：WKJ on 2017/7/15.
 * 邮箱：
 * 编辑：WKJ
 */

public class PharmacopeiaExplvAdapter extends BaseExpandableListAdapter{
    private Context mContext;
    private List<BeanPharmacopeia> mList;
    private LayoutInflater mLayoutInflater;
    public PharmacopeiaExplvAdapter(Context mContext, List<BeanPharmacopeia> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getGroupCount() {
        return mList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mList.get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mList.get(groupPosition).getList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_pharmacopia, parent,false);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.groupIndicator = (ImageView) convertView.findViewById(R.id.group_indicator);
            convertView.setTag(holder);
            //对于listView，注意添加这一行，即可在item上使用高度
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(mList.get(groupPosition).getTitle());
        if (isExpanded){
            holder.groupIndicator.setBackgroundResource(R.mipmap.down);
        }else {
            holder.groupIndicator.setBackgroundResource(R.mipmap.right_dark);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderChild holderChild = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_pharmacopia_child, parent,false);
            holderChild = new ViewHolderChild();
            holderChild.child = (TextView) convertView.findViewById(R.id.tv_child);
            convertView.setTag(holderChild);
            //对于listView，注意添加这一行，即可在item上使用高度
            AutoUtils.autoSize(convertView);
        } else {
            holderChild = (ViewHolderChild) convertView.getTag();
        }
        holderChild.child.setText(mList.get(groupPosition).getList().get(childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ViewHolder{
        private TextView title;
        private ImageView groupIndicator;
    }

    class ViewHolderChild{
        private TextView child;
    }

}

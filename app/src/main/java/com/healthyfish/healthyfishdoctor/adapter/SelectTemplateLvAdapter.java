package com.healthyfish.healthyfishdoctor.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.healthyfish.healthyfishdoctor.POJO.BeanSelectTemplate;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.utils.SingleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：问诊选择模板列表适配器
 * 作者：LYQ on 2017/7/15.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class SelectTemplateLvAdapter extends BaseAdapter {

    private Context mContext;
    private List<BeanSelectTemplate> mList = new ArrayList<>();
    private final int layoutID = R.layout.item_select_template_lv;
    private int selectID;

    public SelectTemplateLvAdapter(Context mContext, List<BeanSelectTemplate> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }
    public int getSelectPosition(){
        return selectID;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        SingleView singleView = null;
        if (singleView == null){
            singleView = new SingleView(mContext);
            convertView = singleView.initView(layoutID);
            holder = new ViewHolder(convertView);
            singleView.setTag(holder);
        }else {
            holder = (ViewHolder) singleView.getTag();
        }
        singleView.initCheckBox(holder.cbSelect);
        holder.tvTemplateTitle.setText(mList.get(position).getTitle());
        holder.tvTemplateContent.setText(mList.get(position).getContent());
        holder.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    selectID = position;
                }
            }
        });
        return singleView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_template_title)
        TextView tvTemplateTitle;
        @BindView(R.id.tv_template_content)
        TextView tvTemplateContent;
        @BindView(R.id.cb_select)
        CheckBox cbSelect;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

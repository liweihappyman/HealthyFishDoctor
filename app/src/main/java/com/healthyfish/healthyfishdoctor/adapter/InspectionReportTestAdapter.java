package com.healthyfish.healthyfishdoctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.healthyfish.healthyfishdoctor.POJO.BeanInspectionReportTest;
import com.healthyfish.healthyfishdoctor.R;
import com.zhy.autolayout.utils.AutoUtils;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：
 * 作者：WKJ on 2017/8/1.
 * 邮箱：
 * 编辑：WKJ
 */

public class InspectionReportTestAdapter extends RecyclerView.Adapter<InspectionReportTestAdapter.ViewHolder> {


    private Context mContext;
    private List<BeanInspectionReportTest> mList;

    public InspectionReportTestAdapter(Context mContext, List<BeanInspectionReportTest> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_inspection_report_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BeanInspectionReportTest beanInspectionReportTest = mList.get(position);
        holder.ireptSubItemName.setText(beanInspectionReportTest.getITEM_NAME());
        holder.ireptResult.setText(beanInspectionReportTest.getRESULT() + "");
        holder.ireptResultUnits.setText(beanInspectionReportTest.getUNITS());
        holder.ireptRefrenceRange.setText(beanInspectionReportTest.getREFERENCE_RANGE());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.irept_result)
        TextView ireptResult;
        @BindView(R.id.irept_result_units)
        TextView ireptResultUnits;
        @BindView(R.id.irept_refrence_range)
        TextView ireptRefrenceRange;
        @BindView(R.id.irept_sub_item_name)
        TextView ireptSubItemName;
        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.auto(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

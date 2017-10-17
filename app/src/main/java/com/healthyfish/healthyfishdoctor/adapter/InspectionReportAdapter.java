package com.healthyfish.healthyfishdoctor.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.healthyfish.healthyfishdoctor.POJO.BeanInspectionReport;
import com.healthyfish.healthyfishdoctor.POJO.BeanInspectionReportTest;
import com.healthyfish.healthyfishdoctor.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：
 * 作者：WKJ on 2017/8/1.
 * 邮箱：
 * 编辑：WKJ
 */

public class InspectionReportAdapter extends RecyclerView.Adapter<InspectionReportAdapter.ViewHolder> {

    private Context mContext;
    private List<BeanInspectionReport> mList;

    public InspectionReportAdapter(Context mContext, List<BeanInspectionReport> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viwe = LayoutInflater.from(mContext).inflate(R.layout.item_inspection_report, parent, false);
        return new ViewHolder(viwe);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BeanInspectionReport beanInspectionReport = mList.get(position);
        holder.ireptItemName.setText(beanInspectionReport.getITEM_NAME());
        holder.ireptPatientName.setText(beanInspectionReport.getPATIENT_NAME());
        holder.ireptRequestedDate.setText(beanInspectionReport.getREQUESTED_DATE());
        holder.ireptPatientId.setText(beanInspectionReport.getPATIENT_ID());


        List<BeanInspectionReportTest> list = new ArrayList<>();
        List<JSONObject> listString = JSONArray.parseObject(beanInspectionReport.getSPECIMEN(), List.class);
        //Log.i("检查报告数据", "" + listString.toString());
        for (JSONObject jsonObject : listString) {
            String string = jsonObject.toJSONString(jsonObject);
            BeanInspectionReportTest beanInspectionReportTest = JSON.parseObject(string, BeanInspectionReportTest.class);
            list.add(beanInspectionReportTest);

        }


        LinearLayoutManager lmg = new LinearLayoutManager(mContext);
        holder.recyclerview.setLayoutManager(lmg);
        holder.recyclerview.setAdapter(new InspectionReportTestAdapter(mContext, list));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.irept_item_name)
        TextView ireptItemName;
        @BindView(R.id.irept_patient_name)
        TextView ireptPatientName;
        @BindView(R.id.irept_hosp_name)
        TextView ireptHospName;
        @BindView(R.id.irept_requested_date)
        TextView ireptRequestedDate;
        @BindView(R.id.irept_patient_id)
        TextView ireptPatientId;
        @BindView(R.id.irept_rusult)
        TextView ireptRusult;
        @BindView(R.id.iirept_refrence_range)
        TextView iireptRefrenceRange;
        @BindView(R.id.recyclerview)
        RecyclerView recyclerview;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.auto(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

package com.healthyfish.healthyfishdoctor.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.healthyfish.healthyfishdoctor.POJO.BeanDrug;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.ui.activity.pharmacopeia.DrugDetail;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * 描述：某类药典列表
 * 作者：WKJ on 2017/7/15.
 * 邮箱：
 * 编辑：WKJ
 */

public class ListOfDrugAdapter extends RecyclerView.Adapter<ListOfDrugAdapter.ViewHolder>{
    private Context mContext;
    private List<BeanDrug> list;
    private Activity activity;

    public ListOfDrugAdapter(List<BeanDrug> list,Activity activity) {
        this.mContext = activity;
        this.list = list;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_drug,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BeanDrug beanDrug = list.get(position);
        holder.nameOfDrug.setText(beanDrug.getNameOfdrug());
        holder.company.setText(beanDrug.getCompany());
        holder.drugLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DrugDetail.class);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nameOfDrug;
        private TextView company;
        private AutoLinearLayout drugLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.auto(itemView);
            drugLayout = (AutoLinearLayout) itemView.findViewById(R.id.drug_layout);
            nameOfDrug = (TextView) itemView.findViewById(R.id.name_of_drug);
            company = (TextView) itemView.findViewById(R.id.name_of_company);
        }
    }
}

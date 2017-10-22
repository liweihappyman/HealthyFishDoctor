package com.healthyfish.healthyfishdoctor.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.healthyfish.healthyfishdoctor.POJO.BeanInspectionReport;
import com.healthyfish.healthyfishdoctor.POJO.BeanPresList;
import com.healthyfish.healthyfishdoctor.POJO.BeanPrescriptiom;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.eventbus.NoticeMessage;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoUtils;

import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 描述：处方的适配器
 * 作者：WKJ on 2017/7/20.
 * 邮箱：
 * 编辑：WKJ
 */

public class PrescriptionRvAdapter extends RecyclerView.Adapter<PrescriptionRvAdapter.ViewHolder> {
    private Context mContext;
    private List<BeanPrescriptiom> list;
    private Toolbar toolbar;
    BeanPresList bean = new BeanPresList();


    public PrescriptionRvAdapter(Context mContext, List<BeanPrescriptiom> list, Toolbar toolbar) {
        this.mContext = mContext;
        this.list = list;
        this.toolbar = toolbar;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_prescription, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        BeanPrescriptiom item = list.get(position);
        holder.ipresDiagnosisName.setText(item.getDIAGNOSIS_NAME());
        String originalWriteTime = item.getWRITE_TIME();
        holder.ipresWriteTime.setText(originalWriteTime.substring(0,10));
        holder.ipresSickName.setText(item.getSICK_NAME());
        holder.ipresSex.setText(item.getSEX());
        holder.ipresAge.setText(item.getAGE());
        holder.ipresDeptOperator.setText(item.getDEPT_NAME() + "  "+item.getPRESCRIBE_OPERATOR());
        holder.ipresPerscribeStatus.setText(item.getRESCRIBE_STATUS());
        /**
         * ITEM_CLASS   存放的是  List<BeanPrescriptiom.PresListBean> preslist的JsonString对象
         */
        if (item.getITEM_CLASS() != null) {
            try {
                //BeanPresList bean = new BeanPresList();
                bean = JSON.parseObject(item.getITEM_CLASS(),BeanPresList.class);
                holder.ipresPhysicName.setText(bean.getPHYSIC_NAME());
                holder.right.setVisibility(View.VISIBLE);
                final BeanPresList finalBean = bean;
                holder.drugNameLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDetail(finalBean);
                    }
                });
            }catch (JSONException e){

            }


                //break;
            //}
        } else {
            holder.ipresPhysicName.setText(" ");
            holder.right.setVisibility(View.GONE);
        }
        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDelDialog(list.get(position).getKey());
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout)
        AutoLinearLayout layout;
        @BindView(R.id.ipres_diagnosis_name)
        TextView ipresDiagnosisName;
        @BindView(R.id.ipres_write_time)
        TextView ipresWriteTime;
        @BindView(R.id.ipres_sick_name)
        TextView ipresSickName;
        @BindView(R.id.ipres_sex)
        TextView ipresSex;
        @BindView(R.id.ipres_age)
        TextView ipresAge;
        @BindView(R.id.ipres_dept_operator)
        TextView ipresDeptOperator;
        @BindView(R.id.ipres_perscribe_status)
        TextView ipresPerscribeStatus;
        @BindView(R.id.ipres_physic_name)
        TextView ipresPhysicName;
        @BindView(R.id.ipres_right)
        ImageView right;
        @BindView(R.id.drug_name_layout)
        AutoLinearLayout drugNameLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.auto(itemView);
            ButterKnife.bind(this, itemView);

        }
    }




    TextView ipresPhysicName;
    @BindView(R.id.usage)
    TextView usage;
    @BindView(R.id.ipres_freq_describe)
    TextView ipresFreqDescribe;
    @BindView(R.id.dose)
    TextView dose;
    @BindView(R.id.ipres_physic_days)
    TextView ipresPhysicDays;
    @BindView(R.id.ipres_physic_quantity)
    TextView ipresPhysicQuantity;
    @BindView(R.id.ipres_pack_spec)
    TextView ipresPackSpec;
    private void showDetail(BeanPresList bean) {

        TextView close;
        View rootView;
        rootView = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_drug_instructions, null);
        final PopupWindow mPopWindow = new PopupWindow(rootView);
        mPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopWindow.setTouchable(true);
        mPopWindow.setFocusable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setAnimationStyle(R.style.PopupRightAnimation);
        ipresPhysicName = (TextView) rootView.findViewById(R.id.ipres_physic_name);
        usage = (TextView) rootView.findViewById(R.id.usage);
        ipresFreqDescribe = (TextView) rootView.findViewById(R.id.ipres_freq_describe);
        dose = (TextView) rootView.findViewById(R.id.dose);
        ipresPhysicDays = (TextView) rootView.findViewById(R.id.ipres_physic_days);
        ipresPhysicQuantity = (TextView) rootView.findViewById(R.id.ipres_physic_quantity);
        ipresPackSpec = (TextView) rootView.findViewById(R.id.ipres_pack_spec);




        initdata(bean);
        close = (TextView) rootView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });
        mPopWindow.showAsDropDown(toolbar, 380, 0);
    }

    private void initdata(BeanPresList bean) {
        ipresPhysicName.setText(bean.getPHYSIC_NAME());
        usage.setText(bean.getUSAGE());
        ipresFreqDescribe.setText(bean.getFREQ_DESCRIBE());
        dose.setText("每次"+bean.getPHYSIC_DOSEAGE()+bean.getDOSE_UNIT());
        ipresPhysicDays.setText(bean.getLAY_PHYSIC_DAYS()+"天");
        ipresPackSpec.setText(bean.getPACK_SPEC());
        ipresPhysicQuantity.setText(bean.getLAY_PHYSIC_QUANTITY()+bean.getPHYSIC_UNIT());
    }




    /**
     * 删除提示对话框
     */
    private void showDelDialog(final String key) {
        new AlertDialog.Builder(mContext).setMessage("是否要删除此处方")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataSupport.deleteAll(BeanPrescriptiom.class,"key = ?",key);
                        EventBus.getDefault().post(new NoticeMessage(51));
                        dialog.dismiss();

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

}

package com.healthyfish.healthyfishdoctor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.healthyfish.healthyfishdoctor.POJO.BeanMedRec;
import com.healthyfish.healthyfishdoctor.R;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.Collections;
import java.util.List;


/**
 * 描述：病历夹列表适配器
 * 作者：TMXK
 * 编辑：wkj
 */

public class MedRecLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<BeanMedRec> listMedRec;
    //date之前的日期   dateCompare当前的日期，两个比较
    private String date;
    private String dateCompare;
    private List<String> listDate;//日期集合，用来遍历，取相同日期的个数;
    private static final int TYPE_0 = 0;
    private static final int TYPE_1 = 1;

    public MedRecLvAdapter(Context mContext, List<BeanMedRec> listMedRec, List<String> listDate) {
        this.mContext = mContext;
        this.listMedRec = listMedRec;
        this.listDate = listDate;
    }
    public MedRecLvAdapter(Context mContext, List<BeanMedRec> listMedRec) {
        this.mContext = mContext;
        this.listMedRec = listMedRec;
    }

    @Override
    public int getCount() {
        return listMedRec.size();
    }

    @Override
    public Object getItem(int position) {
        return listMedRec.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        BeanMedRec beanMedRec = listMedRec.get(position);
        ViewHolderHasHead viewHolderHasHead = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_med_rec, parent, false);
            viewHolderHasHead = new ViewHolderHasHead();
            viewHolderHasHead.headLayout = (AutoLinearLayout) convertView.findViewById(R.id.item_med_rec_head);
            viewHolderHasHead.headDate = (TextView) convertView.findViewById(R.id.item_med_rec_headdate);
            viewHolderHasHead.headCount = (TextView) convertView.findViewById(R.id.item_med_rec_headcount);
            viewHolderHasHead.nameGender = (TextView) convertView.findViewById(R.id.item_med_rec_name_gender);
            viewHolderHasHead.info = (TextView) convertView.findViewById(R.id.item_med_rec_info);
            viewHolderHasHead.dateDiagnose = (TextView) convertView.findViewById(R.id.item_med_rec_date_diagnose);
            //viewHolderHasHead.state = (TextView) convertView.findViewById(R.id.item_med_rec_state);
            convertView.setTag(viewHolderHasHead);
            AutoUtils.autoSize(convertView);
        } else {
            viewHolderHasHead = (ViewHolderHasHead) convertView.getTag();
        }
        viewHolderHasHead.headLayout.setVisibility(View.GONE);
        //如果size = 1 的话，说明只有一个元素，直接与现有的date比较，如果size大于1，
        // 判断当前位置是0不是，如果不是0的话，说明有前一项，这样可以跟前项比较
        try {
            String date = listMedRec.get(position).getClinicalTime();
            dateCompare = date.substring(0, date.indexOf("月") + 1);
            int count = Collections.frequency(listDate, dateCompare);
            if (listDate.size() == 1) {
                date = "date";
            } else {
                date = "date";
                if (position > 0) {
                    date = listDate.get(position - 1);
                }
            }

            if (!date.equals(listDate.get(position))) {
                viewHolderHasHead.headLayout.setVisibility(View.VISIBLE);
                viewHolderHasHead.headDate.setText(dateCompare);
                viewHolderHasHead.headCount.setText(String.valueOf(count));
            }
        } catch (Exception e) {

        }

        String birth = beanMedRec.getBirthday();
        if (birth != null && !birth.equals("")) {
            int age = 2017 - Integer.valueOf(birth.substring(0, 4));
            viewHolderHasHead.nameGender.setText(beanMedRec.getName() + "  " + beanMedRec.getGender() + "  " + age + "岁");
        } else {
            viewHolderHasHead.nameGender.setText(beanMedRec.getName() + "  " + beanMedRec.getGender());
        }
        viewHolderHasHead.info.setText(beanMedRec.getDiseaseInfo());
        viewHolderHasHead.dateDiagnose.setText(beanMedRec.getClinicalTime() + "  " + beanMedRec.getDiagnosis());
        //设置右边标志的颜色和内容
//        if (beanMedRec.isState()) {
//            viewHolderHasHead.state.setText("医");
//            viewHolderHasHead.state.setBackgroundResource(R.mipmap.med_rec_orange_tab);
//        } else {
//            viewHolderHasHead.state.setText("自");
//            viewHolderHasHead.state.setBackgroundResource(R.mipmap.med_rec_green_tab);
//        }
        return convertView;
    }

    class ViewHolderHasHead {
        private AutoLinearLayout headLayout;
        private TextView headDate; //头部日期
        private TextView headCount; //头部总数
        private TextView nameGender;//姓名、性别、年龄
        private TextView info; //病情信息时间
        private TextView dateDiagnose;//日期和诊断
        //private TextView state;//自或医的状态
    }
}

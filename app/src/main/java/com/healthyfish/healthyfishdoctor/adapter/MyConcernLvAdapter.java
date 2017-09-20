package com.healthyfish.healthyfishdoctor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import com.healthyfish.healthyfishdoctor.POJO.BeanMyConcernItem;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.utils.MyToast;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 描述：个人中心我的关注页面的适配器
 * 作者：LYQ on 2017/7/8.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class MyConcernLvAdapter extends BaseSwipeAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<BeanMyConcernItem> mList = new ArrayList<>();

    public MyConcernLvAdapter(Context mContext, List<BeanMyConcernItem> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
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
    public int getSwipeLayoutResourceId(int position) {
        return R.id.sly_message_view;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        View viewPrivateDoctor = mLayoutInflater.inflate(R.layout.item_my_concern_doctor, null);
        return viewPrivateDoctor;
    }

    @Override
    public void fillValues(final int position, View convertView) {
        //绑定控件，holder应在此定义而不能定义为全局，否则会有视图复用问题，即出现点击删除按钮后下一个视图的删除按钮会弹出的情况
        final HolderDoctor holderDoctor = new HolderDoctor(convertView);
        //填充数据
        Glide.with(mContext).load(mList.get(position).getBeanDoctorInfo().getImgUrl()).into(holderDoctor.civHeadPortrait);
        holderDoctor.tvDoctorName.setText(mList.get(position).getBeanDoctorInfo().getName());
        holderDoctor.tvDoctorDepartment.setText(mList.get(position).getBeanDoctorInfo().getDepartment());
        holderDoctor.tvDoctorTitle.setText(mList.get(position).getBeanDoctorInfo().getDuties());
        holderDoctor.tvHospital.setText(mList.get(position).getBeanDoctorInfo().getHospital());
        holderDoctor.tvDoctorInfo.setText(mList.get(position).getBeanDoctorInfo().getIntroduce());

        //整个swipeLayout的简单监听
        holderDoctor.slyMessageView.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                //在删除按钮弹出后设置点击可以关闭按钮，此时它会将子视图的点击监听屏蔽
                holderDoctor.slyMessageView.setClickToClose(true);
            }
        });
        //设置item的点击监听，可以点击进入相对应的item的内容，点击跳转逻辑需要在此处处理执行
        holderDoctor.rlyDoctorInfoVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyToast.showToast(mContext, mList.get(position).getBeanDoctorInfo().getName());
            }
        });
        //设置item的长按监听，弹出删除按钮
        holderDoctor.rlyDoctorInfoVisible.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holderDoctor.slyMessageView.open();
                return true;
            }
        });
        //设置删除按钮的监听，删除逻辑需要在此处处理执行
        holderDoctor.tvCancelAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyToast.showToast(mContext, "删除了第" + position + "条：" + mList.get(position).getBeanDoctorInfo().getName());
                holderDoctor.slyMessageView.close();//在最前面执行此操作就不会出现删除按钮下移到下一个视图的现象，不然会出现在下一个视图删除按钮有探头露脸的现象
                mList.remove(position);
                MyConcernLvAdapter.this.notifyDataSetChanged();
            }
        });
    }


    public class HolderDoctor {

        @BindView(R.id.tv_cancel_attention)
        TextView tvCancelAttention;
        @BindView(R.id.civ_head_portrait)
        CircleImageView civHeadPortrait;
        @BindView(R.id.tv_doctorName)
        TextView tvDoctorName;
        @BindView(R.id.tv_doctorDepartment)
        TextView tvDoctorDepartment;
        @BindView(R.id.tv_doctorTitle)
        TextView tvDoctorTitle;
        @BindView(R.id.tv_hospital)
        TextView tvHospital;
        @BindView(R.id.tv_doctorInfo)
        TextView tvDoctorInfo;
        @BindView(R.id.sly_message_view)
        SwipeLayout slyMessageView;
        @BindView(R.id.rly_doctor_info_visible)
        AutoRelativeLayout rlyDoctorInfoVisible;

        public HolderDoctor(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }

}

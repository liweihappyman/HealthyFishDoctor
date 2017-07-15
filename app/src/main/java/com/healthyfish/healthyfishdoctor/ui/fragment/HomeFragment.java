package com.healthyfish.healthyfishdoctor.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.healthyfish.healthyfishdoctor.POJO.BeanAllMessage;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.adapter.HomeLvAdapter;
import com.healthyfish.healthyfishdoctor.ui.activity.medical_record.MedRecHome;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 描述：首页fragment
 * 作者：Wayne on 2017/7/11 20:50
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.topbar_info)
    AutoRelativeLayout topbarInfo;
    @BindView(R.id.fm_med_rec)
    TextView fmMedRec;
    @BindView(R.id.message_lv)
    ListView messageLv;
    private Context mContext;
    private View rootView;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContext = getActivity();
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initAll();

        return rootView;
    }

    private void initAll() {
        fmMedRec.setOnClickListener(this);
        List<BeanAllMessage> list = new ArrayList<>();
        list.add(new BeanAllMessage("系统消息", "2016年10月20日", "我是一条很长很长的病历，长到的我自己都怕，哦呵呵呵"));
        list.add(new BeanAllMessage("图文消息", "2016年10月20日", "我是一条很长很长的病历，长到的我自己都怕，哦呵呵呵"));
        list.add(new BeanAllMessage("私人医生", "2016年10月20日", "我是一条很长很长的病历，长到的我自己都怕，哦呵呵呵"));
        list.add(new BeanAllMessage("病历夹", "2016年10月20日", "我是一条很长很长的病历，长到的我自己都怕，哦呵呵呵"));
        list.add(new BeanAllMessage("会诊消息", "2016年10月20日", "我是一条很长很长的病历，长到的我自己都怕，哦呵呵呵"));
        list.add(new BeanAllMessage("系统消息", "2016年10月20日", "我是一条很长很长的病历，长到的我自己都怕，哦呵呵呵"));
        list.add(new BeanAllMessage("病历夹", "2016年10月20日", "我是一条很长很长的病历，长到的我自己都怕，哦呵呵呵"));
        list.add(new BeanAllMessage("病历夹", "2016年10月20日", "我是一条很长很长的病历，长到的我自己都怕，哦呵呵呵"));
        list.add(new BeanAllMessage("会诊消息", "2016年10月20日", "我是一条很长很长的病历，长到的我自己都怕，哦呵呵呵"));
        list.add(new BeanAllMessage("系统消息", "2016年10月20日", "我是一条很长很长的病历，长到的我自己都怕，哦呵呵呵"));
        list.add(new BeanAllMessage("病历夹", "2016年10月20日", "我是一条很长很长的病历，长到的我自己都怕，哦呵呵呵"));
        HomeLvAdapter homeLvAdapter = new HomeLvAdapter(getActivity(), list);
        messageLv.setAdapter(homeLvAdapter);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fm_med_rec:
                Intent intent = new Intent(getActivity(), MedRecHome.class);
                startActivity(intent);
                break;
        }
    }
}

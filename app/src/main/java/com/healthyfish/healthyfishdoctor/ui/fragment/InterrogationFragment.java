package com.healthyfish.healthyfishdoctor.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.healthyfish.healthyfishdoctor.POJO.BeanPayService;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.adapter.PayServiceGvAdapter;
import com.healthyfish.healthyfishdoctor.ui.activity.interrogation.GraphicConsultation;
import com.healthyfish.healthyfishdoctor.ui.activity.interrogation.SetGraphicConsultation;
import com.healthyfish.healthyfishdoctor.ui.activity.interrogation.SetPrivateDoctor;
import com.healthyfish.healthyfishdoctor.utils.MyToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 描述：问诊fragment
 * 作者：Wayne on 2017/7/11 20:50
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */
public class InterrogationFragment extends Fragment {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.gv_pay_service)
    GridView gvPayService;
    private Context mContext;
    private View rootView;
    Unbinder unbinder;

    private boolean isOpen1 = true;
    private boolean isOpen2 = false;
    private boolean isOpen3 = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContext = getActivity();
        rootView = inflater.inflate(R.layout.fragment_interrogation, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initAll();

        return rootView;
    }

    private void initAll() {
        initToolBar();
        initPayServiceGv();
    }

    private void initPayServiceGv() {
        List<BeanPayService> list = new ArrayList<>();

        BeanPayService payService1 = new BeanPayService();
        if (isOpen1){
            payService1.setImg(R.mipmap.ic_picture_consulting_open);
        }else {
            payService1.setImg(R.mipmap.ic_picture_consulting_not_open);
        }
        payService1.setTitle("图文咨询");
        payService1.setOpen(isOpen1);

        BeanPayService payService2 = new BeanPayService();
        if (isOpen2){
            payService2.setImg(R.mipmap.ic_video_not_open);
        }else {
            payService2.setImg(R.mipmap.ic_video_not_open);
        }
        payService2.setTitle("视频咨询");
        payService2.setOpen(isOpen2);

        BeanPayService payService3 = new BeanPayService();
        if (isOpen3){
            payService3.setImg(R.mipmap.ic_private_doctor_open);
        }else {
            payService3.setImg(R.mipmap.ic_private_doctor_not_open);
        }
        payService3.setTitle("私人医生");
        payService3.setOpen(isOpen3);

        list.add(payService1);
        list.add(payService2);
        list.add(payService3);

        PayServiceGvAdapter adapter = new PayServiceGvAdapter(getActivity(), list);
        gvPayService.setAdapter(adapter);

        gvPayService.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent1 = new Intent(getActivity(), GraphicConsultation.class);
                        startActivity(intent1);
                        break;
                    case 1:
                        MyToast.showToast(getActivity(),"您还没开通该服务噢，赶紧开通吧！");
                        break;
                    case 2:
                        Intent intent2 = new Intent(getActivity(), SetPrivateDoctor.class);
                        startActivity(intent2);
                        break;
                }

            }
        });

    }

    private void initToolBar() {
        toolbarTitle.setText("问诊服务");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}

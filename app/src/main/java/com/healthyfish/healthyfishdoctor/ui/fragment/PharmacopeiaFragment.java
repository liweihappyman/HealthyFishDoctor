package com.healthyfish.healthyfishdoctor.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.ui.activity.pharmacopeia.PharmacopeiaList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 描述：药典fragment
 * 作者：Wayne on 2017/7/11 20:59
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */
public class PharmacopeiaFragment extends Fragment {
    @BindView(R.id.western_medicine)
    ImageView westernMedicine;
    @BindView(R.id.proprietary_chinese_medicine)
    ImageView proprietaryChineseMedicine;
    @BindView(R.id.chinese_medicine)
    ImageView chineseMedicine;
    @BindView(R.id.prescription)
    ImageView prescription;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Context mContext;
    private View rootView;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        rootView = inflater.inflate(R.layout.fragment_pharmacopeia, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        toolbarTitle.setText("药典");
        initAll();

        return rootView;
    }

    private void initAll() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.western_medicine, R.id.proprietary_chinese_medicine,
            R.id.chinese_medicine, R.id.prescription})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.western_medicine:
                Intent intent = new Intent(getActivity(), PharmacopeiaList.class);
                intent.putExtra("table", 0);
                startActivity(intent);
                break;
            case R.id.proprietary_chinese_medicine:
                Intent intent2 = new Intent(getActivity(), PharmacopeiaList.class);
                intent2.putExtra("table", 1);
                startActivity(intent2);
                break;
            case R.id.chinese_medicine:
                Intent intent3 = new Intent(getActivity(), PharmacopeiaList.class);
                intent3.putExtra("table", 2);
                startActivity(intent3);
                break;
            case R.id.prescription:
                Intent intent4 = new Intent(getActivity(), PharmacopeiaList.class);
                intent4.putExtra("table", 3);
                startActivity(intent4);
                break;
        }
    }
}

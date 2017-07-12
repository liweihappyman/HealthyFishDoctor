package com.healthyfish.healthyfishdoctor.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.healthyfish.healthyfishdoctor.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 描述：个人中心fragment
 * 作者：Wayne on 2017/7/11 20:55
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */
public class PersonalCenterFragment extends Fragment {
    private Context mContext;
    private View rootView;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContext = getActivity();
        rootView = inflater.inflate(R.layout.fragment_personal_center, container, false);
        unbinder = ButterKnife.bind(this, rootView);
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
}

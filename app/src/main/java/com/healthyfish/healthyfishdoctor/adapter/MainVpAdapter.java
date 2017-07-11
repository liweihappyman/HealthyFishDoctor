package com.healthyfish.healthyfishdoctor.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 描述：MainActivity的ViewPage适配器
 * 作者：WKJ on 2017/6/29.
 * 邮箱：
 * 编辑：WKJ
 */
public class MainVpAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;

    public MainVpAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }




}

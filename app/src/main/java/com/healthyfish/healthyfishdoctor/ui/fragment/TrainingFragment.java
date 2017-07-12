package com.healthyfish.healthyfishdoctor.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.utils.ViewFindUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 描述：培训fragment
 * 作者：Wayne on 2017/7/11 20:59
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */
public class TrainingFragment extends Fragment {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Context mContext;
    private View rootView;
    Unbinder unbinder;

    private final String[] mTitles = {
            "热门", "iOS", "Android", "前端"
            , "后端", "设计", "工具资源"
    };

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContext = getActivity();
        rootView = inflater.inflate(R.layout.fragment_training, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initAll();

        return rootView;
    }

    private void initAll() {
        initToolBar();
        initTabLayout();
    }

    private void initToolBar() {
        toolbarTitle.setText("医疗培训");
    }

    private void initTabLayout() {
        for (String title : mTitles) {
            mFragments.add(SimpleCardFragment.getInstance(title));
        }

        ViewPager viewPage = (ViewPager) rootView.findViewById(R.id.vp_training);
        viewPage.setAdapter(new MyPagerAdapter(getActivity().getSupportFragmentManager()));
        SlidingTabLayout tabLayout = (SlidingTabLayout) rootView.findViewById(R.id.tl_training);

        tabLayout.setViewPager(viewPage);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}

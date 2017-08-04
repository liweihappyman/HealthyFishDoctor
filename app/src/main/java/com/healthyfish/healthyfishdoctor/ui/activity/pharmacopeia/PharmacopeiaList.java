package com.healthyfish.healthyfishdoctor.ui.activity.pharmacopeia;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.ui.activity.BaseActivity;
<<<<<<< HEAD
=======
import com.healthyfish.healthyfishdoctor.ui.fragment.HomeFragment;
>>>>>>> pr/4
import com.healthyfish.healthyfishdoctor.ui.fragment.PharmacopeiaListFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 描述：药典
 * 作者：WKJ on 2017/7/15.
 * 邮箱：
 * 编辑：WKJ
 */

public class PharmacopeiaList extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private int mPosition = 0;
    private String[] mTitles = {
            "西药", "中成药", "中药", "方剂"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacopeia_list);
        ButterKnife.bind(this);
        initToolBar(toolbar,toolbarTitle,"药典");
        mPosition = getIntent().getIntExtra("table", 0);
        initTabLayout();


    }

    private void initTabLayout() {
<<<<<<< HEAD

        for (int i = 0; i < mTitles.length; i++) {
            mFragments.add(new PharmacopeiaListFragment());
=======
        mFragments.add(new PharmacopeiaListFragment());
        for (int i = 0; i < mTitles.length-1; i++) {
            mFragments.add(new HomeFragment());
>>>>>>> pr/4
        }

        ViewPager viewPage = (ViewPager) findViewById(R.id.vp_pharmacopeia);
        viewPage.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPage);
        viewPage.setCurrentItem(mPosition);
    }


    private class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(FragmentManager fm) {
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
}

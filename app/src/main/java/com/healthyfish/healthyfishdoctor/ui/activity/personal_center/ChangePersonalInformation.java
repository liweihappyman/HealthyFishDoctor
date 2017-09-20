package com.healthyfish.healthyfishdoctor.ui.activity.personal_center;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.eventbus.SaveInformation;
import com.healthyfish.healthyfishdoctor.ui.activity.BaseActivity;
import com.healthyfish.healthyfishdoctor.ui.fragment.OnlineInformationFragment;
import com.healthyfish.healthyfishdoctor.ui.fragment.RelatedRegistrationFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：修改完善个人信息页面
 * 作者：LYQ on 2017/8/5.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class ChangePersonalInformation extends BaseActivity {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.vp_change_personal_information)
    ViewPager vpChangePersonalInformation;

    private String[] mTitles = {"线上信息", "关联挂号"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_personal_information);
        ButterKnife.bind(this);
        initToolBar(toolbar,toolbarTitle,"个人信息");
        initTabLayout();
    }

    @OnClick(R.id.tv_save)
    public void onViewClicked() {
        //保存操作
        Log.i("LYQ", "onViewClicked");
        EventBus.getDefault().post(new SaveInformation());
    }

    /**
     * 初始化头部标题
     */
    private void initTabLayout() {
        ArrayList<Fragment> mFragments = new ArrayList<>();
        Fragment currentService = new OnlineInformationFragment();
        Fragment myAppointment = new RelatedRegistrationFragment();
        mFragments.add(currentService);
        mFragments.add(myAppointment);

        vpChangePersonalInformation.setAdapter(new PagerAdapter(getSupportFragmentManager(), mFragments));
        tabLayout.setupWithViewPager(vpChangePersonalInformation);
        vpChangePersonalInformation.setCurrentItem(0);
    }


    /**
     * ViewPager适配器
     */
    private class PagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mListFragment;

        public PagerAdapter(FragmentManager fm, List<Fragment> mListFragment) {
            super(fm);
            this.mListFragment = mListFragment;
        }

        @Override
        public int getCount() {
            return mListFragment.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mListFragment.get(position);
        }
    }
}

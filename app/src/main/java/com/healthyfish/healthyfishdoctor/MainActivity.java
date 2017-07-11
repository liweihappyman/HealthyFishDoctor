package com.healthyfish.healthyfishdoctor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthyfish.healthyfishdoctor.adapter.MainVpAdapter;
import com.healthyfish.healthyfishdoctor.ui.fragment.HomeFragment;
import com.healthyfish.healthyfishdoctor.ui.fragment.InterrogationFragment;
import com.healthyfish.healthyfishdoctor.ui.fragment.PersonalCenterFragment;
import com.healthyfish.healthyfishdoctor.ui.fragment.PharmacopeiaFragment;
import com.healthyfish.healthyfishdoctor.ui.fragment.TrainingFragment;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fg_viewpage)
    ViewPager fgViewpage;
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.ly_home)
    AutoLinearLayout lyHome;
    @BindView(R.id.iv_interrogation)
    ImageView ivInterrogation;
    @BindView(R.id.tv_interrogation)
    TextView tvInterrogation;
    @BindView(R.id.ly_interrogation)
    AutoLinearLayout lyInterrogation;
    @BindView(R.id.iv_pharmacopeia)
    ImageView ivPharmacopeia;
    @BindView(R.id.tv_pharmacopeia)
    TextView tvPharmacopeia;
    @BindView(R.id.ly_pharmacopeia)
    AutoLinearLayout lyPharmacopeia;
    @BindView(R.id.iv_training)
    ImageView ivTraining;
    @BindView(R.id.tv_training)
    TextView tvTraining;
    @BindView(R.id.ly_training)
    AutoLinearLayout lyTraining;
    @BindView(R.id.iv_personal_center)
    ImageView ivPersonalCenter;
    @BindView(R.id.tv_personal_center)
    TextView tvPersonalCenter;
    @BindView(R.id.ly_personal_center)
    AutoLinearLayout lyPersonalCenter;

    private MainVpAdapter ViewpageAdapter;
    private List<Fragment> fragments;
    private HomeFragment homeFragment;
    private InterrogationFragment interrogationFragment;
    private PharmacopeiaFragment pharmacopeiaFragment;
    private TrainingFragment trainingFragment;
    private PersonalCenterFragment personalCenterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
    }

    //初始化接界面
    private void init() {
        initpgAdapter();//初始化viewpage
        setTab(0);//初始化界面设置，即指定刚进入是可见的界面
        //菜单监听
        fgViewpage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                int pos = fgViewpage.getCurrentItem();
                setTab(pos);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    //菜单监听


    //初始化ViewPage
    private void initpgAdapter() {
        fragments = new ArrayList<>();
        homeFragment = new HomeFragment();
        interrogationFragment = new InterrogationFragment();
        pharmacopeiaFragment = new PharmacopeiaFragment();
        trainingFragment = new TrainingFragment();
        personalCenterFragment = new PersonalCenterFragment();
        fragments.add(homeFragment);
        fragments.add(interrogationFragment);
        fragments.add(pharmacopeiaFragment);
        fragments.add(trainingFragment);
        fragments.add(personalCenterFragment);
        ViewpageAdapter = new MainVpAdapter(getSupportFragmentManager()
                , fragments);
        //设置ViewPage的缓存页，数字表示预先加载的页面的偏移量，
        // 现在0的意思是不预先加载，另一个作用是也不销毁生的页面
        fgViewpage.setOffscreenPageLimit(0);
        fgViewpage.setAdapter(ViewpageAdapter);
    }

    //重置菜单状态
    private void reSet() {
        ivHome.setImageResource(R.mipmap.home_unselected);
        ivInterrogation.setImageResource(R.mipmap.interrogation_unselect);
        ivPharmacopeia.setImageResource(R.mipmap.pharmacopeia_unselected);
        ivTraining.setImageResource(R.mipmap.training_unselected);
        ivPersonalCenter.setImageResource(R.mipmap.personal_center_unselect);
        tvHome.setTextColor(getResources().getColor(R.color.color_general_and_title));
        tvInterrogation.setTextColor(getResources().getColor(R.color.color_general_and_title));
        tvPharmacopeia.setTextColor(getResources().getColor(R.color.color_general_and_title));
        tvTraining.setTextColor(getResources().getColor(R.color.color_general_and_title));
        tvPersonalCenter.setTextColor(getResources().getColor(R.color.color_general_and_title));
    }

    //设置菜单的选中状态
    private void setTab(int i) {
        switch (i) {
            case 0:
                reSet();
                ivHome.setImageResource(R.mipmap.home);
                tvHome.setTextColor(getResources().getColor(R.color.color_primary_dark));
                fgViewpage.setCurrentItem(0);
                break;
            case 1:
                reSet();
                ivInterrogation.setImageResource(R.mipmap.interrogation);
                tvInterrogation.setTextColor(getResources().getColor(R.color.color_primary_dark));
                fgViewpage.setCurrentItem(1);
                break;
            case 2:
                reSet();
                ivPharmacopeia.setImageResource(R.mipmap.pharmacopeia);
                tvPharmacopeia.setTextColor(getResources().getColor(R.color.color_primary_dark));
                fgViewpage.setCurrentItem(2);
                break;
            case 3:
                reSet();
                ivTraining.setImageResource(R.mipmap.training);
                tvTraining.setTextColor(getResources().getColor(R.color.color_primary_dark));
                fgViewpage.setCurrentItem(3);
                break;
            case 4:
                reSet();
                ivPersonalCenter.setImageResource(R.mipmap.personal_center);
                tvPersonalCenter.setTextColor(getResources().getColor(R.color.color_primary_dark));
                fgViewpage.setCurrentItem(4);
                break;
            default:
        }
    }

    @OnClick({R.id.ly_home, R.id.ly_interrogation, R.id.ly_pharmacopeia, R.id.ly_training, R.id.ly_personal_center})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ly_home:
                setTab(0);
                break;
            case R.id.ly_interrogation:
                setTab(1);
                break;
            case R.id.ly_pharmacopeia:
                setTab(2);
                break;
            case R.id.ly_training:
                setTab(3);
                break;
            case R.id.ly_personal_center:
                setTab(4);
                break;
        }
    }
}

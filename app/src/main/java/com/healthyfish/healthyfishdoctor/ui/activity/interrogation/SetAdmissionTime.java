package com.healthyfish.healthyfishdoctor.ui.activity.interrogation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.healthyfish.healthyfishdoctor.POJO.BeanAdmissionTime;
import com.healthyfish.healthyfishdoctor.POJO.BeanAdmissionTimeItem;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.adapter.SetAdmissionTimeGvAdapter;
import com.healthyfish.healthyfishdoctor.ui.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：问诊设置接诊时间页面
 * 作者：LYQ on 2017/7/13.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class SetAdmissionTime extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.gv_morning)
    GridView gvMorning;
    @BindView(R.id.gv_afternoon)
    GridView gvAfternoon;
    @BindView(R.id.gv_night)
    GridView gvNight;
    @BindView(R.id.rbt_monday)
    RadioButton rbtMonday;
    @BindView(R.id.rbt_tuesday)
    RadioButton rbtTuesday;
    @BindView(R.id.rbt_wednesday)
    RadioButton rbtWednesday;
    @BindView(R.id.rbt_thursday)
    RadioButton rbtThursday;
    @BindView(R.id.rbt_friday)
    RadioButton rbtFriday;
    @BindView(R.id.rbt_saturday)
    RadioButton rbtSaturday;
    @BindView(R.id.rbt_sunday)
    RadioButton rbtSunday;
    @BindView(R.id.rgp_week)
    RadioGroup rgpWeek;
    @BindView(R.id.tv_save)
    TextView tvSave;

    public final static int RESULT_OK = 223;

    private SetAdmissionTimeGvAdapter adapterMorning;
    private SetAdmissionTimeGvAdapter adapterAfternoon;
    private SetAdmissionTimeGvAdapter adapterNight;

    private BeanAdmissionTime admissionTimeList = new BeanAdmissionTime();

    private List<BeanAdmissionTimeItem> MondayMorning = new ArrayList<>();
    private List<BeanAdmissionTimeItem> MondayAfternoon = new ArrayList<>();
    private List<BeanAdmissionTimeItem> MondayNight = new ArrayList<>();

    private List<BeanAdmissionTimeItem> TuesdayMorning = new ArrayList<>();
    private List<BeanAdmissionTimeItem> TuesdayAfternoon = new ArrayList<>();
    private List<BeanAdmissionTimeItem> TuesdayNight = new ArrayList<>();

    private List<BeanAdmissionTimeItem> WednesdayMorning = new ArrayList<>();
    private List<BeanAdmissionTimeItem> WednesdayAfternoon = new ArrayList<>();
    private List<BeanAdmissionTimeItem> WednesdayNight = new ArrayList<>();

    private List<BeanAdmissionTimeItem> ThursdayMorning = new ArrayList<>();
    private List<BeanAdmissionTimeItem> ThursdayAfternoon = new ArrayList<>();
    private List<BeanAdmissionTimeItem> ThursdayNight = new ArrayList<>();

    private List<BeanAdmissionTimeItem> FridayMorning = new ArrayList<>();
    private List<BeanAdmissionTimeItem> FridayAfternoon = new ArrayList<>();
    private List<BeanAdmissionTimeItem> FridayNight = new ArrayList<>();

    private List<BeanAdmissionTimeItem> SaturdayMorning = new ArrayList<>();
    private List<BeanAdmissionTimeItem> SaturdayAfternoon = new ArrayList<>();
    private List<BeanAdmissionTimeItem> SaturdayNight = new ArrayList<>();

    private List<BeanAdmissionTimeItem> SundayMorning = new ArrayList<>();
    private List<BeanAdmissionTimeItem> SundayAfternoon = new ArrayList<>();
    private List<BeanAdmissionTimeItem> SundayNight = new ArrayList<>();

//模拟数据（后期删除）：
    //上午
    String[] timeMorning = new String[]{"8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00"};
    boolean[] checkMorning = new boolean[]{true, false, false, false, false, false, true, true, false};
    //下午
    String[] timeAfternoon = new String[]{"14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00"};
    boolean[] checkAfternoon = new boolean[]{true, true, false, false, false, false, true, true, false};
    //晚上
    String[] timeNight = new String[]{"19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00"};
    boolean[] checkNight = new boolean[]{true, false, false, true, false, false, true, true, false};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_admission_time);
        ButterKnife.bind(this);
        initToolBar(toolbar, toolbarTitle, "设置接诊时间");
        getData();
        initData();
        setAdmissionTimeGv(MondayMorning,MondayAfternoon,MondayNight);//后期需要判断数据是否为空
        rgpListener();
    }

    /**
     * 模拟数据，后期删除
     */
    private void getData() {
        List<BeanAdmissionTimeItem> listMorning1 = new ArrayList<>();
        List<BeanAdmissionTimeItem> listMorning2 = new ArrayList<>();
        List<BeanAdmissionTimeItem> listMorning3 = new ArrayList<>();
        List<BeanAdmissionTimeItem> listMorning4 = new ArrayList<>();
        List<BeanAdmissionTimeItem> listMorning5 = new ArrayList<>();
        List<BeanAdmissionTimeItem> listMorning6 = new ArrayList<>();
        List<BeanAdmissionTimeItem> listMorning7 = new ArrayList<>();
        for (int i = 0; i < timeMorning.length; i++) {
            BeanAdmissionTimeItem admissionTime = new BeanAdmissionTimeItem();
            admissionTime.setTime(timeMorning[i]);
            admissionTime.setCheck(checkMorning[i]);
            listMorning1.add(admissionTime);
        }
        for (int i = 0; i < timeMorning.length; i++) {
            BeanAdmissionTimeItem admissionTime = new BeanAdmissionTimeItem();
            admissionTime.setTime(timeMorning[i]);
            admissionTime.setCheck(checkMorning[i]);
            listMorning2.add(admissionTime);
        }
        for (int i = 0; i < timeMorning.length; i++) {
            BeanAdmissionTimeItem admissionTime = new BeanAdmissionTimeItem();
            admissionTime.setTime(timeMorning[i]);
            admissionTime.setCheck(checkMorning[i]);
            listMorning3.add(admissionTime);
        }
        for (int i = 0; i < timeMorning.length; i++) {
            BeanAdmissionTimeItem admissionTime = new BeanAdmissionTimeItem();
            admissionTime.setTime(timeMorning[i]);
            admissionTime.setCheck(checkMorning[i]);
            listMorning4.add(admissionTime);
        }
        for (int i = 0; i < timeMorning.length; i++) {
            BeanAdmissionTimeItem admissionTime = new BeanAdmissionTimeItem();
            admissionTime.setTime(timeMorning[i]);
            admissionTime.setCheck(checkMorning[i]);
            listMorning5.add(admissionTime);
        }
        for (int i = 0; i < timeMorning.length; i++) {
            BeanAdmissionTimeItem admissionTime = new BeanAdmissionTimeItem();
            admissionTime.setTime(timeMorning[i]);
            admissionTime.setCheck(checkMorning[i]);
            listMorning6.add(admissionTime);
        }
        for (int i = 0; i < timeMorning.length; i++) {
            BeanAdmissionTimeItem admissionTime = new BeanAdmissionTimeItem();
            admissionTime.setTime(timeMorning[i]);
            admissionTime.setCheck(checkMorning[i]);
            listMorning7.add(admissionTime);
        }

        List<BeanAdmissionTimeItem> listAfternoon1 = new ArrayList<>();
        List<BeanAdmissionTimeItem> listAfternoon2 = new ArrayList<>();
        List<BeanAdmissionTimeItem> listAfternoon3 = new ArrayList<>();
        List<BeanAdmissionTimeItem> listAfternoon4 = new ArrayList<>();
        List<BeanAdmissionTimeItem> listAfternoon5 = new ArrayList<>();
        List<BeanAdmissionTimeItem> listAfternoon6 = new ArrayList<>();
        List<BeanAdmissionTimeItem> listAfternoon7 = new ArrayList<>();
        for (int i = 0; i < timeAfternoon.length; i++) {
            BeanAdmissionTimeItem admissionTime = new BeanAdmissionTimeItem();
            admissionTime.setTime(timeAfternoon[i]);
            admissionTime.setCheck(checkAfternoon[i]);
            listAfternoon1.add(admissionTime);
            listAfternoon2.add(admissionTime);
            listAfternoon3.add(admissionTime);
            listAfternoon4.add(admissionTime);
            listAfternoon5.add(admissionTime);
            listAfternoon6.add(admissionTime);
            listAfternoon7.add(admissionTime);
        }

        List<BeanAdmissionTimeItem> listNight1 = new ArrayList<>();
        List<BeanAdmissionTimeItem> listNight2 = new ArrayList<>();
        List<BeanAdmissionTimeItem> listNight3 = new ArrayList<>();
        List<BeanAdmissionTimeItem> listNight4 = new ArrayList<>();
        List<BeanAdmissionTimeItem> listNight5 = new ArrayList<>();
        List<BeanAdmissionTimeItem> listNight6 = new ArrayList<>();
        List<BeanAdmissionTimeItem> listNight7 = new ArrayList<>();
        for (int i = 0; i < timeNight.length; i++) {
            BeanAdmissionTimeItem admissionTime = new BeanAdmissionTimeItem();
            admissionTime.setTime(timeNight[i]);
            admissionTime.setCheck(checkNight[i]);
            listNight1.add(admissionTime);
            listNight2.add(admissionTime);
            listNight3.add(admissionTime);
            listNight4.add(admissionTime);
            listNight5.add(admissionTime);
            listNight6.add(admissionTime);
            listNight7.add(admissionTime);
        }

        admissionTimeList.setMondayMorning(listMorning1);
        admissionTimeList.setMondayAfternoon(listAfternoon1);
        admissionTimeList.setMondayNight(listNight1);

        admissionTimeList.setTuesdayMorning(listMorning2);
        admissionTimeList.setTuesdayAfternoon(listAfternoon2);
        admissionTimeList.setTuesdayNight(listNight2);

        admissionTimeList.setWednesdayMorning(listMorning3);
        admissionTimeList.setWednesdayAfternoon(listAfternoon3);
        admissionTimeList.setWednesdayNight(listNight3);

        admissionTimeList.setThursdayMorning(listMorning4);
        admissionTimeList.setThursdayAfternoon(listAfternoon4);
        admissionTimeList.setThursdayNight(listNight4);

        admissionTimeList.setFridayMorning(listMorning5);
        admissionTimeList.setFridayAfternoon(listAfternoon5);
        admissionTimeList.setFridayNight(listNight5);

        admissionTimeList.setSaturdayMorning(listMorning6);
        admissionTimeList.setSaturdayAfternoon(listAfternoon6);
        admissionTimeList.setSaturdayNight(listNight6);

        admissionTimeList.setSundayMorning(listMorning7);
        admissionTimeList.setSundayAfternoon(listAfternoon7);
        admissionTimeList.setSundayNight(listNight7);

    }

    /**
     * 初始化数据
     */
    private void initData() {
        MondayMorning = admissionTimeList.getMondayMorning();
        MondayAfternoon = admissionTimeList.getMondayAfternoon();
        MondayNight = admissionTimeList.getMondayNight();

        TuesdayMorning = admissionTimeList.getTuesdayMorning();
        TuesdayAfternoon = admissionTimeList.getTuesdayAfternoon();
        TuesdayNight = admissionTimeList.getTuesdayNight();

        WednesdayMorning = admissionTimeList.getWednesdayMorning();
        WednesdayAfternoon = admissionTimeList.getWednesdayAfternoon();
        WednesdayNight = admissionTimeList.getWednesdayNight();

        ThursdayMorning = admissionTimeList.getThursdayMorning();
        ThursdayAfternoon = admissionTimeList.getThursdayAfternoon();
        ThursdayNight = admissionTimeList.getThursdayNight();

        FridayMorning = admissionTimeList.getFridayMorning();
        FridayAfternoon = admissionTimeList.getFridayAfternoon();
        FridayNight = admissionTimeList.getFridayNight();

        SaturdayMorning = admissionTimeList.getSaturdayMorning();
        SaturdayAfternoon = admissionTimeList.getSaturdayAfternoon();
        SaturdayNight = admissionTimeList.getSaturdayNight();

        SundayMorning = admissionTimeList.getSundayMorning();
        SundayAfternoon = admissionTimeList.getSundayAfternoon();
        SundayNight = admissionTimeList.getSundayNight();
    }

    /**
     * 设置RadioGroup的监听，监听选择星期几
     */
    private void rgpListener() {
        rgpWeek.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rbt_monday:
                        setAdmissionTimeGv(MondayMorning,MondayAfternoon,MondayNight);
                        break;
                    case R.id.rbt_tuesday:
                        setAdmissionTimeGv(TuesdayMorning,TuesdayAfternoon,TuesdayNight);
                        break;
                    case R.id.rbt_wednesday:
                        setAdmissionTimeGv(WednesdayMorning,WednesdayAfternoon,WednesdayNight);
                        break;
                    case R.id.rbt_thursday:
                        setAdmissionTimeGv(ThursdayMorning,ThursdayAfternoon,ThursdayNight);
                        break;
                    case R.id.rbt_friday:
                        setAdmissionTimeGv(FridayMorning,FridayAfternoon,FridayNight);
                        break;
                    case R.id.rbt_saturday:
                        setAdmissionTimeGv(SaturdayMorning,SaturdayAfternoon,SaturdayNight);
                        break;
                    case R.id.rbt_sunday:
                        setAdmissionTimeGv(SundayMorning,SundayAfternoon,SundayNight);
                        break;
                }
            }
        });
    }

    /**
     * 初始化和设置接诊时间的选择
     */
    private void setAdmissionTimeGv(List<BeanAdmissionTimeItem> listMorning, List<BeanAdmissionTimeItem> listAfternoon, List<BeanAdmissionTimeItem> listNight) {
        //上午
        adapterMorning = new SetAdmissionTimeGvAdapter(this, listMorning);
        gvMorning.setAdapter(adapterMorning);
        //下午
        adapterAfternoon = new SetAdmissionTimeGvAdapter(this, listAfternoon);
        gvAfternoon.setAdapter(adapterAfternoon);
        //晚上
        adapterNight = new SetAdmissionTimeGvAdapter(this, listNight);
        gvNight.setAdapter(adapterNight);
    }

    /**
     * 保存操作
     */
    @OnClick(R.id.tv_save)
    public void onViewClicked() {
        admissionTimeList.setMondayMorning(MondayMorning);
        admissionTimeList.setMondayAfternoon(MondayAfternoon);
        admissionTimeList.setMondayNight(MondayNight);

        admissionTimeList.setTuesdayMorning(TuesdayMorning);
        admissionTimeList.setTuesdayAfternoon(TuesdayAfternoon);
        admissionTimeList.setTuesdayNight(TuesdayNight);

        admissionTimeList.setWednesdayMorning(WednesdayMorning);
        admissionTimeList.setWednesdayAfternoon(WednesdayAfternoon);
        admissionTimeList.setWednesdayNight(WednesdayNight);

        admissionTimeList.setThursdayMorning(ThursdayMorning);
        admissionTimeList.setThursdayAfternoon(ThursdayAfternoon);
        admissionTimeList.setThursdayNight(ThursdayNight);

        admissionTimeList.setFridayMorning(FridayMorning);
        admissionTimeList.setFridayAfternoon(FridayAfternoon);
        admissionTimeList.setFridayNight(FridayNight);

        admissionTimeList.setSaturdayMorning(SaturdayMorning);
        admissionTimeList.setSaturdayAfternoon(SaturdayAfternoon);
        admissionTimeList.setSaturdayNight(SaturdayNight);

        admissionTimeList.setSundayMorning(SundayMorning);
        admissionTimeList.setSundayAfternoon(SundayAfternoon);
        admissionTimeList.setSundayNight(SundayNight);

        Intent intent = new Intent(this, SetPrivateDoctor.class);
        this.setResult(RESULT_OK,intent);
        finish();
    }
}
